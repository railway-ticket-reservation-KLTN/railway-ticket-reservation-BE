package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import com.example.raiwayticketreservation.Entity.NhanVien;
import com.example.raiwayticketreservation.Entity.TaiKhoan;
import com.example.raiwayticketreservation.Service.NhanVienService;
import com.example.raiwayticketreservation.Service.TaiKhoanService;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import com.example.raiwayticketreservation.dtos.responses.TaiKhoanResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/admin")
public class TaiKhoanController {
    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private NhanVienService nhanVienService;

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
        if(taiKhoanService.kiemTraTaiKhoanTheoMa(taiKhoan)) {
            if(taiKhoanService.xoaTaiKhoan(taiKhoan)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(ErrorResponse.builder()
                    .tenLoi("Lỗi xóa tài khoản")
                    .moTaLoi("Xử lí xóa tài khoản gặp lỗi").build(), HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi xóa tài khoản")
                .moTaLoi("Tài khoản không tồn tại").build(), HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Cập nhật tài khoản",
            description = "Cập nhật thông tin tài khoản vào hệ thống ở trang quản trị",
            tags = "API Quản lí tài khoản - ADMIN")
    @PostMapping("/capnhattk")
    public ResponseEntity capNhatTaiKhoanTheoMa(@RequestBody TaiKhoan taiKhoan) {
        if(taiKhoanService.kiemTraTaiKhoanTheoMa(taiKhoan)) {
            if(taiKhoanService.capNhatTaiKhoan(taiKhoan)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(ErrorResponse.builder()
                    .tenLoi("Lỗi cập nhật tài khoản")
                    .moTaLoi("Xử lí cập nhật tài khoản gặp lỗi").build(), HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi cập nhật tài khoản")
                .moTaLoi("Tài khoản không tồn tại").build(), HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Get danh sách tài khoản",
            description = "Get danh sách thông tin tài khoản ở trang quản trị",
            tags = "API Quản lí tài khoản - ADMIN")
    @GetMapping("/taikhoans")
    public ResponseEntity getDanhSachTaiKhoan() {
        List<TaiKhoan> taiKhoans = taiKhoanService.getDanhSachTaiKhoan();
        List<NhanVien> nhanViens = nhanVienService.getDanhSachNhanVien();
        List<TaiKhoanResponse> taiKhoanResponses = new ArrayList<>();

        if(taiKhoans.size() > 0) {
            taiKhoans.forEach(taiKhoan -> {
                TaiKhoanResponse taiKhoanResponse = TaiKhoanResponse.builder()
                        .id(taiKhoan.getId())
                        .tenTaiKhoan(taiKhoan.getTenTaiKhoan())
                        .matKhau(taiKhoan.getMatKhau())
                        .loaiTK(taiKhoan.getLoaiTK())
                        .build();
                nhanViens.forEach(nhanVien -> {
                    if(nhanVien.getId().equals(taiKhoan.getNhanVien().getId())) {
                        taiKhoanResponse.setNhanVien(nhanVien);
                    }
                });
                taiKhoanResponses.add(taiKhoanResponse);
            });
            return new ResponseEntity<>(taiKhoanResponses, HttpStatus.OK);
        } else  return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi lấy danh sách tài khoản")
                .moTaLoi("Tài khoản không có trong hệ thống").build(), HttpStatus.BAD_REQUEST);
    }
}
