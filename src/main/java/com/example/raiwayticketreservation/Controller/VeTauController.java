package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.*;
import com.example.raiwayticketreservation.Service.*;
import com.example.raiwayticketreservation.constants.SystemConstant;
import com.example.raiwayticketreservation.dtos.requests.KiemTraVeRequest;
import com.example.raiwayticketreservation.dtos.requests.MuaVeRequest;
import com.example.raiwayticketreservation.dtos.requests.TimVeTraRequest;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import com.example.raiwayticketreservation.dtos.responses.MuaVeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private TrangThaiGheService trangThaiGheService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Mua vé",
            description = "Sau khi hoàn thành các buớc mua vé thì API này thực thi" +
                    " để lưu vé và thông tin khách đặt vào DB",
            tags = "API Mua vé")
    @PostMapping("/muave")
    public ResponseEntity muaVe(@RequestBody MuaVeRequest muaVeRequest) {
        KhachDatVe khachDatVeID = KhachDatVe.builder().
                id(khachDatVeService.getIDKhachDat(muaVeRequest.getKhachDatVe()))
                .build();
        KhachDatVe khachDatVe = new KhachDatVe();
        if(khachDatVeID.getId() == null) {
            KhachDatVe khachDatVeSaved = KhachDatVe.builder().hoTen(muaVeRequest.getKhachDatVe().getHoTen())
                    .sdt(muaVeRequest.getKhachDatVe().getSdt())
                    .email(muaVeRequest.getKhachDatVe().getEmail())
                    .soGiayTo(muaVeRequest.getKhachDatVe().getSoGiayTo()).build();
            khachDatVe = khachDatVeService.themKhachDat(khachDatVeSaved);
        } else {
            khachDatVe = khachDatVeService.getKhachDatVeTheoID(khachDatVeID.getId());
        }
        Set<VeTau> veKhachDat = veTauService.getVeTauTheoMaKhachDat(khachDatVe.getId());
        if(veKhachDat.size() < 5){
            String maDatVe = "";
            String maDatCho = "";
            String tinhTrang = "";

            if(muaVeRequest.getHinhThucThanhToan().equals(SystemConstant.TRA_SAU)) {
                Random random = new Random();
                int numRand = random.nextInt(999999999);
                maDatCho = String.format("%09d", numRand);
                tinhTrang = "CHUA_THANH_TOAN";
                maDatVe = null;

            } else if (muaVeRequest.getHinhThucThanhToan().equals(SystemConstant.THANH_TOAN_ONLINE)){
                Random random = new Random();
                int numRand = random.nextInt(999999999);
                maDatVe = String.format("%09d", numRand);
                maDatCho = null;
                tinhTrang = "DA_THANH_TOAN";
            }
            Date ngayLap = Date.valueOf(muaVeRequest.getNgayLap());
            HoaDon hoaDon = HoaDon.builder()
                    .hinhThucThanhToan(muaVeRequest.getHinhThucThanhToan())
                    .ngayLap(ngayLap)
                    .khachDatVe(khachDatVe)
                    .maDatVe(maDatVe)
                    .maDatCho(maDatCho)
                    .tinhTrang(tinhTrang)
                    .trangThai(1)
                    .build();
            HoaDon hoaDonSaved = hoaDonService.themHoaDon(hoaDon);

            Set<VeTau> veTauDis = new HashSet<>();
            HoaDon hoaDonID = HoaDon.builder()
                    .id(hoaDonService.getIDHoaDonTheoMaDatChoHoacMaDatVe(maDatCho, maDatVe))
                    .build();

            String finalTinhTrang = tinhTrang;
            KhachDatVe finalKhachDatVe = khachDatVe;
            muaVeRequest.getVeTaus().forEach(veTau -> {
                Random random = new Random();
                int maVe = random.nextInt(99999999);
                Date ngayDi = Date.valueOf(veTau.getNgayDi());
                Date ngayDen = Date.valueOf(veTau.getNgayDen());
                HanhTrinh hanhTrinh = HanhTrinh.builder()
                        .gaDi(veTau.getGaDi())
                        .gaDen(veTau.getGaDen())
                        .ngayDi(ngayDi.toLocalDate())
                        .ngayDen(ngayDen.toLocalDate())
                        .gioDi(veTau.getGioDi())
                        .gioDen(veTau.getGioDen())
                        .build();
                HanhTrinh hanhTrinhID = HanhTrinh.builder()
                        .id(hanhTrinhService.getIDHanhTrinh(hanhTrinh))
                        .gaDi(veTau.getGaDi())
                        .gaDen(veTau.getGaDen())
                        .ngayDi(ngayDi.toLocalDate())
                        .ngayDen(ngayDen.toLocalDate())
                        .gioDi(veTau.getGioDi())
                        .gioDen(veTau.getGioDen())
                        .giaVe(veTau.getDonGia())
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
                        .khachDatVe(finalKhachDatVe)
                        .build();
                veTauDis.add(veTauDi);

                Long trangThaiGheID = trangThaiGheService
                        .getIdTrangThaiGhe(veTau.getGaDi(), veTau.getGaDen()
                                , veTau.getNgayDi().toString(), veTau.getMaGhe(), veTau.getSoToa(), SystemConstant.DAT_CHO);
                trangThaiGheService.capNhatTrangThaiGhe(veTauDi.getMaVe(), SystemConstant.DA_MUA, trangThaiGheID);
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
                    .khachDatVe(khachDatVe)
                    .hinhThucThanhToan(muaVeRequest.getHinhThucThanhToan())
                    .ngayLap(ngayLap)
                    .build();

            return new ResponseEntity(muaVeResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi mua vé")
                .moTaLoi("Khách hàng đã đặt quá số lượng vé được mua theo quy định của nhà ga. Mỗi khách hàng chỉ được mua tối đa 5 vé")
                .build(), HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Kiểm tra vé",
            description = "Khách hàng nhập thông tin trên vé, nếu thông tin trùng với vé thì sẽ trả về thông tin vé",
            tags = "API Kiểm tra vé")
    @PostMapping("/kiemtrave")
    public ResponseEntity kiemTraVe(@RequestBody KiemTraVeRequest kiemTraVeRequest) throws JsonProcessingException {
        VeTau veTau = veTauService.getVeTheoMaVe(kiemTraVeRequest);
        if(veTau == null) {
            return new ResponseEntity(ErrorResponse.builder().tenLoi("Lỗi kiểm tra vé").moTaLoi("Không tìm thấy vé").build(), HttpStatus.BAD_REQUEST);
        } else {
            HanhTrinh hanhTrinh = hanhTrinhService.getHanhTrinhTheoMaHanhTrinh(veTau.getHanhTrinh().getId());
            KhachDatVe khachDatVe = khachDatVeService.getKhachDatVeTheoID(veTau.getKhachDatVe().getId());
            veTau.builder().hanhTrinh(hanhTrinh).khachDatVe(khachDatVe);
            Date ngayDiRequest = Date.valueOf(kiemTraVeRequest.getNgayDi());
            if(kiemTraVeRequest.getTenTau().equals(veTau.getTenTau())
                    && kiemTraVeRequest.getGaDi().equals(veTau.getHanhTrinh().getGaDi())
                    && kiemTraVeRequest.getGaDen().equals(veTau.getHanhTrinh().getGaDen())
                    && ngayDiRequest.toLocalDate().equals(veTau.getHanhTrinh().getNgayDi())
                    && kiemTraVeRequest.getSoGiayTo().equals(veTau.getSoGiayTo()))
            {
                return new ResponseEntity(veTau, HttpStatus.OK);
            } else {
                return new ResponseEntity(ErrorResponse.builder().tenLoi("Lỗi thông tin vé").moTaLoi("Thông tin cung cấp không trùng khớp với thông tin vé").build(), HttpStatus.BAD_REQUEST);
            }

        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Lấy danh sách vé theo khách đặt",
            description = "Lấy danh sách vé theo thông tin khách đặt",
            tags = "API Trả vé")
    @PostMapping("/ves")
    public ResponseEntity getDanhSachVeTheoKhachDat(@RequestBody TimVeTraRequest timVeTraRequest) {
        HoaDon hoaDon = hoaDonService.getHoaDonByMaDatVe(timVeTraRequest.getMaDatVe());
        KhachDatVe khachDatVe = khachDatVeService.getKhachDatVeTheoID(hoaDon.getKhachDatVe().getId());
        if(timVeTraRequest.getTenKhachDat().equals(khachDatVe.getHoTen())
                && timVeTraRequest.getEmail().equals(khachDatVe.getEmail())
                && timVeTraRequest.getSoGiayTo().equals(khachDatVe.getSoGiayTo())
                && timVeTraRequest.getSdt().equals(khachDatVe.getSdt())) {
            Set<CTHD> cthds = cthdService.getCTHDTheoHoaDonId(hoaDon.getId());
            ArrayList<VeTau> veTaus = new ArrayList<>();
            cthds.forEach(cthd -> {
                VeTau veTau = veTauService.getVeTauTheoID(cthd.getVeTau().getId());
                HanhTrinh hanhTrinh = hanhTrinhService.getHanhTrinhTheoMaHanhTrinh(veTau.getHanhTrinh().getId());
                veTau.builder().khachDatVe(khachDatVe).hanhTrinh(hanhTrinh);
                if(!veTau.getTinhTrang().equals(SystemConstant.TRA_VE)) {
                    veTaus.add(veTau);
                }
            });
            return new ResponseEntity<>(veTaus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ErrorResponse.builder().tenLoi("Lỗi thông tin không trùng khớp").moTaLoi("Thông tin khách đặt không chính xác").build(), HttpStatus.BAD_REQUEST);
        }
    }
}
