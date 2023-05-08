package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.NhanVien;
import com.example.raiwayticketreservation.Entity.TaiKhoan;
import com.example.raiwayticketreservation.Service.NhanVienService;
import com.example.raiwayticketreservation.Service.TaiKhoanService;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class TaiKhoanController {
    @Autowired
    TaiKhoanService taiKhoanService;

    @Autowired
    NhanVienService nhanVienService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Thêm tài khoản mới",
            description = "Thêm tài khoản mới vào hệ thống ở trang quản trị",
            tags = "API Quản lí tài khoản - ADMIN")
    @PostMapping("/admin/themtk")
    public ResponseEntity themTaiKhoan(@RequestBody TaiKhoan taiKhoan) {
        NhanVien nhanVien = nhanVienService.themNhanVien(taiKhoan.getNhanVien());
        TaiKhoan taiKhoanReturn = taiKhoanService.themTaiKhoan(taiKhoan);

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
    @PostMapping("/admin/xoatk")
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
    @PostMapping("/admin/capnhattk")
    public ResponseEntity capNhatTaiKhoanTheoMa(@RequestBody TaiKhoan taiKhoan) {
        if(taiKhoanService.capNhatTaiKhoan(taiKhoan)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi cập nhật tài khoản")
                .moTaLoi("Xử lí cập nhật tài khoản gặp lỗi").build(), HttpStatus.BAD_REQUEST);
    }

}
