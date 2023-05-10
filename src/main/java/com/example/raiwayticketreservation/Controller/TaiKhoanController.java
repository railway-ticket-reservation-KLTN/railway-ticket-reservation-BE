package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.NhanVien;
import com.example.raiwayticketreservation.Entity.TaiKhoan;
import com.example.raiwayticketreservation.Service.NhanVienService;
import com.example.raiwayticketreservation.Service.TaiKhoanService;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class TaiKhoanController {
    @Autowired
    TaiKhoanService taiKhoanService;

    @Autowired
    NhanVienService nhanVienService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Thêm tài khoản mới",
            description = "Thêm tài khoản mới vào hệ thống ở trang quản trị",
            tags = "API Quản lí tài khoản - ADMIN")
    @PostMapping("/themtk")
    public ResponseEntity themTaiKhoan(@RequestBody TaiKhoan taiKhoan) {
        NhanVien nhanVien = taiKhoan.getNhanVien();
        TaiKhoan taiKhoanReturn = null;
        if(!nhanVienService.kiemTraNhanVienTonTai(taiKhoan.getNhanVien())) {
            nhanVien = nhanVienService.themNhanVien(taiKhoan.getNhanVien());
        } else {
            nhanVien.setId(nhanVienService.getIdNhanVien(nhanVien));
        }
        if(!taiKhoanService.kiemTraTaiKhoanNhanVienTonTai(taiKhoan))
        {
            taiKhoanReturn = taiKhoanService.themTaiKhoan(taiKhoan);
        } else {
            return new ResponseEntity<>(ErrorResponse.builder()
                    .tenLoi("Lỗi thêm tài khoản")
                    .moTaLoi("Nhân viên đã có tài khoản").build(), HttpStatus.BAD_REQUEST);
        }
        taiKhoanReturn.setNhanVien(nhanVien);

        if(taiKhoanReturn != null) {
            return new ResponseEntity<>(taiKhoanReturn, HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi thêm tài khoản")
                .moTaLoi("Xử lí thêm tài khoản gặp lỗi").build(), HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Xóa tài khoản",
            description = "Cập nhật trạng thái tài khoản bằng 0 vào hệ thống ở trang quản trị",
            tags = "API Quản lí tài khoản - ADMIN")
    @PostMapping("/xoatk")
    public ResponseEntity xoaTaiKhoanTheoMa(@RequestBody TaiKhoan taiKhoan) {
        if(taiKhoanService.xoaTaiKhoan(taiKhoan)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi xóa tài khoản")
                .moTaLoi("Xử lí xóa tài khoản gặp lỗi").build(), HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Cập nhật tài khoản",
            description = "Cập nhật thông tin tài khoản vào hệ thống ở trang quản trị",
            tags = "API Quản lí tài khoản - ADMIN")
    @PostMapping("/capnhattk")
    public ResponseEntity capNhatTaiKhoanTheoMa(@RequestBody TaiKhoan taiKhoan) {
        if(taiKhoanService.capNhatTaiKhoan(taiKhoan)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi cập nhật tài khoản")
                .moTaLoi("Xử lí cập nhật tài khoản gặp lỗi").build(), HttpStatus.BAD_REQUEST);
    }

}
