package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.*;
import com.example.raiwayticketreservation.Service.*;
import com.example.raiwayticketreservation.dtos.requests.KiemTraVeRequest;
import com.example.raiwayticketreservation.dtos.requests.MuaVeRequest;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import com.example.raiwayticketreservation.dtos.responses.MuaVeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/v1")
public class VeTauController {
    @Autowired
    private HanhTrinhService hanhTrinhService;
    @Autowired
    private VeTauService veTauService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private CTHDService cthdService;

    @Autowired
    private KhachDatVeService khachDatVeService;

    @Operation(summary = "Mua vé",
            description = "Sau khi hoàn thành các buớc mua vé thì API này thực thi" +
                    " để lưu vé và thông tin khách đặt vào DB",
            tags = "API Mua vé")
    @PostMapping("/muave")
    public ResponseEntity muaVe(@RequestBody MuaVeRequest muaVeRequest) {
        KhachDatVe khachDatVe = KhachDatVe.builder().hoTen(muaVeRequest.getKhachDatVe().getHoTen())
                .sdt(muaVeRequest.getKhachDatVe().getSdt())
                .email(muaVeRequest.getKhachDatVe().getEmail())
                .soGiayTo(muaVeRequest.getKhachDatVe().getSoGiayTo()).build();
        KhachDatVe khachDatVeSaved = khachDatVeService.themKhachDat(khachDatVe);

        KhachDatVe khachDatVeID = KhachDatVe.builder().
                id(khachDatVeService.getIDKhachDat(muaVeRequest.getKhachDatVe()))
                .build();

        String maDatVe = "";
        String maDatCho = "";
        String tinhTrang = "";

        if(muaVeRequest.getHinhThucThanhToan().equals("TRA_SAU")) {
            Random random = new Random();
            int numRand = random.nextInt(999999999);
            maDatCho = String.format("%09d", numRand);
            tinhTrang = "CHUA_THANH_TOAN";
            maDatVe = null;

        } else if (muaVeRequest.getHinhThucThanhToan().equals("THANH_TOAN_ONLINE")){
            Random random = new Random();
            int numRand = random.nextInt(999999999);
            maDatVe = String.format("%09d", numRand);
            maDatCho = null;
            tinhTrang = "DA_THANH_TOAN";
        }

        HoaDon hoaDon = HoaDon.builder()
                .hinhThucThanhToan(muaVeRequest.getHinhThucThanhToan())
                .ngayLap(muaVeRequest.getNgayLap())
                .khachDatVe(khachDatVeID)
                .maDatVe(maDatVe)
                .maDatCho(maDatCho)
                .tinhTrang(tinhTrang)
                .trangThai(1)
                .build();
        HoaDon hoaDonSaved = hoaDonService.themHoaDon(hoaDon);

        Set<VeTau> veTauDis = new HashSet<>();
        HoaDon hoaDonID = HoaDon.builder()
                .id(hoaDonService.getIDHoaDon(muaVeRequest.getNgayLap(), khachDatVeID.getId()))
                .build();

        String finalTinhTrang = tinhTrang;
        muaVeRequest.getVeTaus().forEach(veTau -> {
                Random random = new Random();
                int maVe = random.nextInt(99999999);
                HanhTrinh hanhTrinh = HanhTrinh.builder()
                        .gaDi(veTau.getGaDi())
                        .gaDen(veTau.getGaDen())
                        .ngayDi(veTau.getNgayDi())
                        .build();
                HanhTrinh hanhTrinhID = HanhTrinh.builder()
                        .id(hanhTrinhService.getIDHanhTrinh(hanhTrinh))
                        .build();
                VeTau veTauDi = VeTau.builder()
                        .maVe(String.format("%08d", maVe))
                        .doiTuong(veTau.getDoiTuong())
                        .donGia(veTau.getDonGia())
                        .loaiVe(veTau.getLoaiVe())
                        .soGiayTo(veTau.getSoGiayTo())
                        .tenHanhKhach(veTau.getTenHanhKhach())
                        .tenTau(veTau.getTenTau())
                        .soGhe(veTau.getSoGhe())
                        .soToa(veTau.getSoToa())
                        .tinhTrang(finalTinhTrang)
                        .trangThai(1)
                        .hanhTrinh(hanhTrinhID)
                        .khachDatVe(khachDatVeID)
                        .build();
                veTauDis.add(veTauDi);
        });
        List<VeTau> veTauSaved = veTauService.themVe(veTauDis);

        Set<CTHD> cthds = new HashSet<>();
        veTauSaved.forEach(veTau -> {
            VeTau veTauID = VeTau.builder().id(veTau.getId()).build();
            CTHD cthd = CTHD.builder().donGia(veTau.getDonGia())
                    .hoaDon(hoaDonID)
                    .veTau(veTauID)
                    .build();
            cthds.add(cthd);
        });
        cthdService.themCTHD(cthds);

        MuaVeResponse muaVeResponse = MuaVeResponse.builder()
                .veTaus(veTauSaved)
                .khachDatVe(khachDatVeSaved)
                .hinhThucThanhToan(muaVeRequest.getHinhThucThanhToan())
                .ngayLap(muaVeRequest.getNgayLap())
                .build();

        return new ResponseEntity(muaVeResponse, HttpStatus.OK);
    }
    @Operation(summary = "Kiểm tra vé",
            description = "Khách hàng nhập thông tin trên vé, nếu thông tin trùng với vé thì sẽ trả về thông tin vé",
            tags = "API Kiểm tra vé")
    @GetMapping("/kiemtrave")
    public ResponseEntity kiemTraVe(@RequestBody KiemTraVeRequest kiemTraVeRequest) throws JsonProcessingException {
        VeTau veTau = veTauService.getVeTheoMaVe(kiemTraVeRequest);
        if(veTau == null) {
            return new ResponseEntity(ErrorResponse.builder().tenLoi("Lỗi kiểm tra vé").moTaLoi("Không tìm thấy vé").build(), HttpStatus.BAD_REQUEST);
        } else {
            HanhTrinh hanhTrinh = hanhTrinhService.getHanhTrinhTheoMaHanhTrinh(veTau);
            KhachDatVe khachDatVe = khachDatVeService.getKhachDatVeTheoID(veTau);
            veTau.builder().hanhTrinh(hanhTrinh).khachDatVe(khachDatVe);
            if(kiemTraVeRequest.getTenTau().equals(veTau.getTenTau())
                    && kiemTraVeRequest.getGaDi().equals(veTau.getHanhTrinh().getGaDi())
                    && kiemTraVeRequest.getGaDen().equals(veTau.getHanhTrinh().getGaDen())
                    && kiemTraVeRequest.getNgayDi().equals(veTau.getHanhTrinh().getNgayDi())
                    && kiemTraVeRequest.getSoGiayTo().equals(veTau.getSoGiayTo()))
            {
                return new ResponseEntity(veTau, HttpStatus.OK);
            } else {
                return new ResponseEntity(ErrorResponse.builder().tenLoi("Lỗi thông tin vé").moTaLoi("Thông tin cung cấp không trùng khớp với thông tin vé").build(), HttpStatus.BAD_REQUEST);
            }

        }
    }
}
