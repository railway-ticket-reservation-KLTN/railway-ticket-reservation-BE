package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.*;
import com.example.raiwayticketreservation.Service.*;
import com.example.raiwayticketreservation.constants.SystemConstant;
import com.example.raiwayticketreservation.dtos.requests.KiemTraVeRequest;
import com.example.raiwayticketreservation.dtos.requests.MuaVeRequest;
import com.example.raiwayticketreservation.dtos.requests.TimVeTraRequest;
import com.example.raiwayticketreservation.dtos.requests.VeTauRequest;
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

    @Autowired
    private ThanhToanMoMoService thanhToanMoMoService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Mua vé",
            description = "Sau khi hoàn thành các buớc mua vé thì API này thực thi" +
                    " để lưu vé và thông tin khách đặt vào DB",
            tags = "API Mua vé")
    @PostMapping("/muave")
    public ResponseEntity muaVe(@RequestBody MuaVeRequest muaVeRequest) {
        if(muaVeRequest.getVeTaus().size() <= 5) {
            MuaVeResponse muaVeResponse = xuLiMuaVe(muaVeRequest);
            if (muaVeResponse != null) {
                if(muaVeRequest.getHinhThucThanhToan().equals(SystemConstant.TRA_SAU)) {
                    if(muaVeResponse.getVeTaus() == null)
                        return new ResponseEntity<>(ErrorResponse.builder()
                                .tenLoi("Lỗi mua vé")
                                .moTaLoi("Xử lí mua vé gặp lỗi")
                                .build(), HttpStatus.BAD_REQUEST);
                    return new ResponseEntity(muaVeResponse, HttpStatus.OK);
                } else if(muaVeRequest.getHinhThucThanhToan().equals(SystemConstant.THANH_TOAN_MOMO)) {
                    Map<String, Long> param = new HashMap<String, Long>();
                    List<VeTauRequest> giaVes = muaVeRequest.getVeTaus().stream().toList();
                    double donGia = 0;
                    for(int i = 0; i < giaVes.size(); i++) {
                        donGia += giaVes.get(i).getDonGia();
                    }
                    param.put("amount", (long) donGia);
                    param.put("orderId", muaVeResponse.getMaDatCho());
                    Object response = thanhToanMoMoService.getDataThanhToanMoMo(param);
                    if(response != null && muaVeResponse != null) {
                        return  new ResponseEntity(response, HttpStatus.OK);
                    } else return new ResponseEntity<>(ErrorResponse.builder()
                            .tenLoi("Lỗi mua vé")
                            .moTaLoi("Xử lí mua vé online gặp lỗi")
                            .build(), HttpStatus.BAD_REQUEST);
                }
            }
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi mua vé")
                .moTaLoi("Khách hàng đã đặt quá số lượng vé được mua theo quy định của nhà ga. Mỗi khách hàng chỉ được mua tối đa 5 vé")
                .build(), HttpStatus.BAD_REQUEST);
    }

    public MuaVeResponse xuLiMuaVe(MuaVeRequest muaVeRequest) {
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
        if(veKhachDat.size() + muaVeRequest.getVeTaus().size() <= 5){
            String maDatCho = "";
            String tinhTrang = "";

            Random random = new Random();
            int numRand = random.nextInt(999999999);
            maDatCho = 1 + String.format("%09d", numRand);
            tinhTrang = "CHUA_THANH_TOAN";

            Date ngayMuaVe = Date.valueOf(muaVeRequest.getNgayLap());

            Set<VeTau> veTauDis = new HashSet<>();

            String finalTinhTrang = tinhTrang;
            KhachDatVe finalKhachDatVe = khachDatVe;
            String finalMaDatCho = maDatCho;

            muaVeRequest.getVeTaus().forEach(veTau -> {
                Random randomNum = new Random();
                int maVe = randomNum.nextInt(99999999);
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
                        .maVe(1 + String.format("%08d", maVe))
                        .maDatCho(finalMaDatCho)
                        .doiTuong(veTau.getDoiTuong())
                        .ngayMua(ngayMuaVe)
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

            MuaVeResponse muaVeResponse = MuaVeResponse.builder()
                    .veTaus(veTauSaved)
                    .hinhThucThanhToan(muaVeRequest.getHinhThucThanhToan())
                    .ngayMuave(ngayMuaVe)
                    .maDatCho(Long.valueOf(maDatCho))
                    .build();
            return muaVeResponse;
        }
        return null;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Kiểm tra vé",
            description = "Khách hàng nhập thông tin trên vé, nếu thông tin trùng với vé thì sẽ trả về thông tin vé",
            tags = "API Kiểm tra vé")
    @PostMapping("/kiemtrave")
    public ResponseEntity kiemTraVe(@RequestBody KiemTraVeRequest kiemTraVeRequest) throws JsonProcessingException {
        VeTau veTau = veTauService.getVeTheoMaVe(kiemTraVeRequest.getMaVe());
        if(veTau == null) {
            return new ResponseEntity(ErrorResponse.builder().tenLoi("Lỗi kiểm tra vé").moTaLoi("Không tìm thấy vé").build(), HttpStatus.BAD_REQUEST);
        } else {
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
        if(hoaDon != null) {
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
                return new ResponseEntity<>(ErrorResponse.builder()
                        .tenLoi("Lỗi thông tin không trùng khớp")
                        .moTaLoi("Thông tin khách đặt không chính xác").build(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi hóa đơn")
                .moTaLoi("Không tìm thấy thông tin hóa đơn của khách hàng, hãy kiểm tra lại mã hóa đơn vừa nhập").build(), HttpStatus.BAD_REQUEST);
    }

//    public void xuLiGiaHanVeTau() {
//        veTauService.capNhatVeTauHetHanThanhToan();
//    }
}
