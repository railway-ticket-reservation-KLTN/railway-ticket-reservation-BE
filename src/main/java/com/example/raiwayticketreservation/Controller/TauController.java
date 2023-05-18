package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.Tau;
import com.example.raiwayticketreservation.Service.TauService;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/khachhang")
public class TauController {
    @Autowired
    private TauService tauService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Lấy danh sách tàu",
            description = "Lấy danh sách tàu để thêm hành trình",
            tags = "API Lấy danh sách tàu")
    @GetMapping("/getalltau")
    public ResponseEntity getDanhSachTau() {
        List<Tau> tauList =  tauService.getDanhSachTau();
        if(tauList.size() > 0 ) {
            return new ResponseEntity<>(tauList, HttpStatus.OK);
        } else return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi lấy danh sách tàu")
                .moTaLoi("Không có tàu nào trong cơ sở dữ liệu").build(), HttpStatus.NOT_FOUND);
    }
}
