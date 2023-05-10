package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.CTHD;
import com.example.raiwayticketreservation.Entity.HoaDon;
import com.example.raiwayticketreservation.Entity.VeTau;
import com.example.raiwayticketreservation.Service.*;
import com.example.raiwayticketreservation.constants.SystemConstant;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/nhanvien")
public class QuanLyVeController {

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
    @Operation(summary = "Lấy danh sách vé theo mã đặt chỗ",
            description = "Lấy danh sách vé theo mã đặt chỗ để load lên bảng vé tàu",
            tags = "API Quản lí vé - NHAN_VIEN")
    @PostMapping("/vestheomadatcho")
    public ResponseEntity getVesTheoMaDatCho(@RequestParam String maDatCho) {
        Set<VeTau> veTaus = veTauService.getVeTauByMaDatCho(Long.valueOf(maDatCho));
        if(veTaus != null) {
            return new ResponseEntity<>(veTaus, HttpStatus.OK);
        } return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi hóa đơn")
                .moTaLoi("Không tìm thấy hóa đơn vui lòng kiểm tra lại thông tin mã đặt chỗ").build(), HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Xác nhận đặt vé",
            description = "Lưu dữ liệu khi khách hàng xác nhận thanh toán vé trả sau",
            tags = "API Đặt vé trả sau - NHAN_VIEN")
    @PostMapping("/datvetrasau")
    public ResponseEntity xacNhanDatVe(@RequestBody List<VeTau> veTaus) {
        try {
            String maDatVe = "";
            Random random = new Random();
            int numRand = random.nextInt(999999999);
            maDatVe = 1 + String.format("%09d", numRand);

            LocalDate localDate = LocalDate.now();
            Date ngayLap = Date.valueOf(localDate);

            HoaDon hoaDon = HoaDon.builder()
                    .hinhThucThanhToan(SystemConstant.TRA_SAU)
                    .maDatVe(maDatVe)
                    .ngayLap(ngayLap)
                    .tinhTrang(SystemConstant.DA_THANH_TOAN)
                    .trangThai(1)
                    .khachDatVe(veTaus.get(0).getKhachDatVe())
                    .build();

            HoaDon hoaDonReturn = hoaDonService.themHoaDon(hoaDon);
            Set<CTHD> cthds = new HashSet<>();

            veTaus.forEach(veTau -> {
                CTHD cthd = CTHD.builder()
                        .veTau(veTau)
                        .hoaDon(hoaDonReturn)
                        .donGia(veTau.getDonGia())
                        .build();
                cthds.add(cthd);
                veTauService.capNhatTinhTrangVeTau(veTau.getMaVe(), SystemConstant.DA_MUA);
            });

            cthdService.themCTHD(cthds);
            return new ResponseEntity<>(cthds, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(ErrorResponse.builder()
                    .tenLoi("Lỗi thanh toán trả sau")
                    .moTaLoi("Xử lí thanh toán đặt vé gặp lỗi").build(), HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Xác nhận trả vé tại nhà Ga",
            description = "Cập nhật thông tin vé trả",
            tags = "API Trả vé tại nhà Ga - NHAN_VIEN")
    @PostMapping("/trave")
    public ResponseEntity xacNhanTraVe(@RequestBody List<VeTau> veTaus){
        if(veTaus != null) {
            veTaus.forEach(veTau -> {
                veTauService.capNhatTinhTrangVeTau(veTau.getMaVe(), SystemConstant.TRA_VE);
                trangThaiGheService.xoaTrangThaiGheTheoMaVe(veTau.getMaVe());
            });
            return new ResponseEntity<>(veTaus, HttpStatus.OK);
        } return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi vé tàu")
                .moTaLoi("Không có vé tàu cần trả").build(), HttpStatus.BAD_REQUEST);
    }

}
