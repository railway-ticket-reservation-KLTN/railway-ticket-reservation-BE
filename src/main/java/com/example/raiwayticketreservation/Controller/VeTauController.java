package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.*;
import com.example.raiwayticketreservation.Service.*;
import com.example.raiwayticketreservation.dtos.MuaVeRequest;
import com.example.raiwayticketreservation.dtos.MuaVeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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

           HoaDon hoaDon = new HoaDon();
           if(muaVeRequest.getHinhThucThanhToan() == "TRA_SAU") {
               hoaDon.builder().hinhThucThanhToan(muaVeRequest.getHinhThucThanhToan())
                       .ngayLap(muaVeRequest.getNgayLap())
                       .khachDatVe(khachDatVeID)
                       .trangThai(1)
                       .tinhTrang("CHUA_THANH_TOAN")
                       .maDatCho(UUID.randomUUID())
                       .build();
           } else if (muaVeRequest.getHinhThucThanhToan() == "THANH_TOAN_ONLINE"){
               hoaDon.builder().hinhThucThanhToan(muaVeRequest.getHinhThucThanhToan())
                       .ngayLap(muaVeRequest.getNgayLap())
                       .khachDatVe(khachDatVeID)
                       .trangThai(1)
                       .tinhTrang("CHUA_THANH_TOAN")
                       .tinhTrang("DA_THANH_TOAN")
                       .maDatVe(UUID.randomUUID())
                       .build();
           }

        HoaDon hoaDonSaved = hoaDonService.themHoaDon(hoaDon);

        HoaDon hoaDonID = HoaDon.builder()
                .id(hoaDonService.getIDHoaDon(muaVeRequest.getNgayLap(), khachDatVeID.getId()))
                .build();
        Set<VeTau> veTauDis = new HashSet<>();
        muaVeRequest.getVeTaus().forEach(veTau -> {
                HanhTrinh hanhTrinh = HanhTrinh.builder()
                        .gaDi(veTau.getGaDi())
                        .gaDen(veTau.getGaDen())
                        .ngayDi(veTau.getNgayDi())
                        .build();
                HanhTrinh hanhTrinhID = HanhTrinh.builder()
                        .id(hanhTrinhService.getIDHanhTrinh(hanhTrinh))
                        .build();
                VeTau veTauDi = VeTau.builder()
                        .doiTuong(veTau.getDoiTuong())
                        .donGia(veTau.getDonGia())
                        .loaiVe(veTau.getLoaiVe())
                        .soGiayTo(veTau.getSoGiayTo())
                        .tenHanhKhach(veTau.getTenHanhKhach())
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
//        cthdService.themCTHD(cthds);

        MuaVeResponse muaVeResponse = MuaVeResponse.builder()
                .veTaus(veTauSaved)
                .khachDatVe(khachDatVeSaved)
                .hinhThucThanhToan(muaVeRequest.getHinhThucThanhToan())
                .ngayLap(muaVeRequest.getNgayLap())
                .build();

        return new ResponseEntity(muaVeResponse, HttpStatus.OK);
    }
}
