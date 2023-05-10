package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import com.example.raiwayticketreservation.Service.HanhTrinhService;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class QuanLyHanhTrinhTauController {
    @Autowired
    private HanhTrinhService hanhTrinhService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Thêm hành trình tàu",
            description = "Thêm hành trình tàu trên trang quản trị",
            tags = "API Quản lí hành trình - ADMIN")
    @PostMapping("/themhanhtrinh")
    public ResponseEntity themHanhTrinhs(@RequestBody List<HanhTrinh> hanhTrinhs) {
        if (hanhTrinhs.size() != 0) {
            if(hanhTrinhService.kiemTraHanhTrinhTonTai(hanhTrinhs)) {
                List<HanhTrinh> hanhTrinhList = hanhTrinhService.themHanhTrinhs(hanhTrinhs);
                return new ResponseEntity<>(hanhTrinhList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ErrorResponse.builder()
                        .tenLoi("Lỗi thêm hành trình")
                        .moTaLoi("Hành trình đã tồn tại").build(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi thêm hành trình")
                .moTaLoi("Không có hành trình để thêm").build(), HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Xóa hành trình tàu",
            description = "Xóa hành trình tàu trên trang quản trị",
            tags = "API Quản lí hành trình - ADMIN")
    @PostMapping("/xoahanhtrinh")
    public ResponseEntity xoaHanhTrinhs(@RequestBody List<HanhTrinh> hanhTrinhs) {
        if (hanhTrinhs.size() != 0) {
            if(hanhTrinhService.xoaHanhTrinh(hanhTrinhs)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ErrorResponse.builder()
                        .tenLoi("Lỗi xóa hành trình")
                        .moTaLoi("Xóa hành trình gặp lỗi").build(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi xóa hành trình")
                .moTaLoi("Không có hành trình để xóa").build(), HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Cập nhật hành trình tàu",
            description = "Cập nhật hành trình tàu trên trang quản trị",
            tags = "API Quản lí hành trình - ADMIN")
    @PostMapping("/capnhathanhtrinh")
    public ResponseEntity capNhatHanhTrinh(@RequestBody HanhTrinh hanhTrinh) {
        if (hanhTrinh != null) {
            if (hanhTrinhService.capNhatHanhTrinh(hanhTrinh)) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity<>(ErrorResponse.builder()
                    .tenLoi("Lỗi cập nhật hành trình")
                    .moTaLoi("Xử lí cập nhật hành trình xảy ra lỗi").build(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi cập nhật hành trình")
                .moTaLoi("Không có hành trình để cập nhật").build(), HttpStatus.BAD_REQUEST);
    }

}
