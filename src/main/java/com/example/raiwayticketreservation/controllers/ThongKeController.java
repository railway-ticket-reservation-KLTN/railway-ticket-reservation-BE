package com.example.raiwayticketreservation.controllers;

import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import com.example.raiwayticketreservation.service.VeTauService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/admin")
@CrossOrigin("http://localhost:4200")
public class ThongKeController {
    @Autowired
    private VeTauService veTauService;

    @Operation(summary = "Get số vé bán trong tháng hiện tại",
            description = "Get số vé bán trong tháng hiện tại để thống kê ở trang quản trị",
            tags = "API Thống kê - ADMIN")
    @GetMapping("/sovethang")
    public ResponseEntity thongKeSoVeBanTrongThang() {
        int tongSoVe = veTauService.getTongSoVeBanTrongThangHienTai();
        if(tongSoVe > 0) {
            return new ResponseEntity<>(tongSoVe, HttpStatus.OK);
        } else return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Không có vé bán trong tháng")
                .moTaLoi("Không có vé nào được bán trong tháng này").build(), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get doanh thu trong tháng hiện tại",
            description = "Get doanh thu trong tháng hiện tại để thống kê ở trang quản trị",
            tags = "API Thống kê - ADMIN")
    @GetMapping("/doanthuthang")
    public ResponseEntity thongKeDoanhThuBanTrongThang() {
        double doanhThu = veTauService.getDoanhThuBanVeTrongThangHienTai();
        if (doanhThu > 0) {
            return new ResponseEntity(doanhThu, HttpStatus.OK);
        } else return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Không có doanh thu trong tháng")
                .moTaLoi("Không có doanh thu trong tháng này").build(), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get doanh thu theo từng tháng trong năm",
            description = "Get doanh thu theo từng tháng trong năm để thống kê ở trang quản trị",
            tags = "API Thống kê - ADMIN")
    @GetMapping("/doanthuthangtrongnam")
    public ResponseEntity thongKeDoanhThuTheoTungThang() {
        List<Map<String, Object>> doanhThuTheoTungThangTrongNam = veTauService.getDoanhThuTheoTungThangTrongNam();
        if(doanhThuTheoTungThangTrongNam.size() > 0) {
            return new ResponseEntity<>(doanhThuTheoTungThangTrongNam, HttpStatus.OK);
        } else return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Không có doanh thu trong năm")
                .moTaLoi("Không có doanh thu trong năm này").build(), HttpStatus.NOT_FOUND);
    }
}
