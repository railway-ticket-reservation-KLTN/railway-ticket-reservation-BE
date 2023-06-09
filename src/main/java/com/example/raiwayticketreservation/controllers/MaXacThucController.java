package com.example.raiwayticketreservation.controllers;

import com.example.raiwayticketreservation.entities.*;
import com.example.raiwayticketreservation.service.*;
import com.example.raiwayticketreservation.constants.SystemConstant;
import com.example.raiwayticketreservation.dtos.requests.VeTauRequest;
import com.example.raiwayticketreservation.dtos.requests.XacThucRequest;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@RestController
@RequestMapping("/v1/khachhang")
public class MaXacThucController {
    @Autowired
    private MaXacThucService maXacThucService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private KhachDatVeService khachDatVeService;

    @Autowired
    private HanhTrinhService hanhTrinhService;

    @Autowired
    private VeTauService veTauService;

    @Autowired
    private TrangThaiGheService trangThaiGheService;

    @Autowired
    private TauService tauService;

    @Autowired
    private GheService gheService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Gửi mã xác thực",
            description = "Gửi mã xác thực qua email",
            tags = "API Gửi mã xác thực" )
    @PostMapping("/guima")
    public ResponseEntity guiMaXacThucEmail(@RequestBody KhachDatVe khachDatVe) {
        KhachDatVe khachDatVeResult = KhachDatVe.builder()
                .id(khachDatVeService.getIDKhachDat(khachDatVe))
                .build();
        if(khachDatVeResult.getId() != null) {
            Random random = new Random();
            int numRand = random.nextInt(999999);
            String maXacThuc = 1 + String.format("%05d", numRand);
            Map<String, Object> maXacThucMap = new HashMap<>();
            maXacThucMap.put("maXacThuc", maXacThuc);
            emailService.guiMaXacThucMessage(khachDatVe.getEmail(),
                    "Railway VN - Gửi mã xác thực trả vé", maXacThucMap);
            maXacThucService.themMaXacThuc(khachDatVeResult, maXacThuc);
            return new ResponseEntity("Mã xác thực đã được gửi đến hộp thư của bạn.", HttpStatus.OK);
        } else
            return new ResponseEntity(ErrorResponse.builder()
                    .tenLoi("Lỗi thông tin")
                    .moTaLoi("Thông tin khách đặt vé không tồn tại").build(), HttpStatus.BAD_REQUEST);

    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Xác thực trả vé",
            description = "Xác thực OTP và thực thi tiến trình trả vé",
            tags = "API Trả vé" )
    @PostMapping("/xacthuctrave")
    public ResponseEntity xacThucEmailTraVe(@RequestBody XacThucRequest xacThucRequest) throws IOException {
        MaXacThuc maXacThuc = maXacThucService.timMaXacThucTheoToken(xacThucRequest.getMaXacThuc());
        Set<VeTau> veTaus = new HashSet<>();
        KhachDatVe khachDatVeID = KhachDatVe.builder()
                .id(khachDatVeService.getIDKhachDat(xacThucRequest.getKhachDatVe()))
                .build();
        if (maXacThuc != null) {
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            if(maXacThuc.getTimestamp().before(currentTimestamp)) {
                return new ResponseEntity(ErrorResponse.builder().tenLoi("Lỗi mã xác thực").moTaLoi("Mã xác thực đã hết hạn").build(),HttpStatus.BAD_REQUEST);
            } else if(maXacThuc.getKhachDatVe().getId().equals(khachDatVeID.getId())) {
                xacThucRequest.getKhachDatVe().setId(khachDatVeID.getId());
                Set<VeTauRequest> veTauRequestSet = xacThucRequest.getVeTaus();
                veTauRequestSet.forEach(veTauRequest -> {
                    Date ngayDi = Date.valueOf(veTauRequest.getNgayDi());
                    Date ngayDen = Date.valueOf(veTauRequest.getNgayDen());
                    HanhTrinh hanhTrinh = HanhTrinh.builder()
                            .gaDi(veTauRequest.getGaDi())
                            .gaDen(veTauRequest.getGaDen())
                            .ngayDi(ngayDi.toLocalDate())
                            .ngayDen(ngayDen.toLocalDate())
                            .gioDi(veTauRequest.getGioDi())
                            .gioDen(veTauRequest.getGioDen())
                            .build();

                    Long maTau = tauService.getIdTauTheoTenTau(veTauRequest.getTenTau());
                    hanhTrinh.setId(hanhTrinhService.getIDHanhTrinh(hanhTrinh, maTau));
                    VeTau veTau = VeTau.builder()
                            .tenHanhKhach(veTauRequest.getTenHanhKhach())
                            .soGiayTo(veTauRequest.getSoGiayTo())
                            .donGia(veTauRequest.getDonGia())
                            .loaiVe(veTauRequest.getLoaiVe())
                            .doiTuong(veTauRequest.getDoiTuong())
                            .tenTau(veTauRequest.getTenTau())
                            .soGhe(veTauRequest.getSoGhe())
                            .soToa(veTauRequest.getSoToa())
                            .hanhTrinh(hanhTrinh)
                            .khachDatVe(xacThucRequest.getKhachDatVe())
                            .tinhTrang(veTauRequest.getTinhTrang())
                            .build();
                    veTau.setId(veTauService.getIDVeTau(veTau));
                    veTauService.capNhatTrangThaiTinhTrangVeTau(veTau.getId(), SystemConstant.TRA_VE);
                    veTaus.add(veTau);
                    List<TrangThaiGhe> trangThaiGheResponseList = trangThaiGheService
                            .getTrangThaiGheByThongTinHanhTrinh(veTauRequest.getGaDi(), veTauRequest.getGaDen(), veTauRequest.getNgayDi().toString(),
                                    veTauRequest.getSoToa(), SystemConstant.DA_MUA);

                    trangThaiGheResponseList.forEach(trangThaiGheResponse -> {
                        if(gheService.getSoGheTheoMaGhe(trangThaiGheResponse.getGhe().getId()) == veTauRequest.getSoGhe()) {
                            trangThaiGheService.xoaTrangThaiGheByID(trangThaiGheResponse.getId());
                        }
                    });
                });
                if(veTaus.size() != 0) {
                    return new ResponseEntity<>(veTaus, HttpStatus.OK);
                } else
                    return new ResponseEntity(ErrorResponse.builder()
                            .tenLoi("Lỗi trả vé")
                            .moTaLoi("Đã xảy ra lỗi trong tiến trình trả vé").build(), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(ErrorResponse.builder()
                        .tenLoi("Lỗi xác thực")
                        .moTaLoi("Không tìm thấy mã xác thực khớp với thông tin khách đặt").build(), HttpStatus.BAD_REQUEST);

            }
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi không tìm thấy mã xác thực")
                .moTaLoi("Không tìm thấy mã xác thực trong hệ thống").build(), HttpStatus.BAD_REQUEST);
    }
}
