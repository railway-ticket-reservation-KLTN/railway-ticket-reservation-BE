package com.example.raiwayticketreservation.controllers;

import com.example.raiwayticketreservation.entities.Ghe;
import com.example.raiwayticketreservation.service.GheService;
import com.example.raiwayticketreservation.service.TrangThaiGheService;
import com.example.raiwayticketreservation.dtos.requests.GheRequest;
import com.example.raiwayticketreservation.dtos.requests.TrangThaiGheRequest;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Set;

@RestController
@RequestMapping("/v1/khachhang")
public class GheController {

    @Autowired
    private GheService gheService;

    @Autowired
    private TrangThaiGheService trangThaiGheService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Chọn chỗ",
            description = "Khi khách hàng chọn vào một ghế bất kỳ thì API này sẽ thực thi " +
                    "để thực hiện việc đặt chỗ tạm thời trong thời gian 600s",
            tags = "API Chọn trả chỗ" )
    @PostMapping("/datcho")
    public ResponseEntity datChoTam(@RequestBody TrangThaiGheRequest trangThaiGheRequest) throws ParseException {
        return gheService.datChoTam(trangThaiGheRequest);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Nhả chỗ",
            description = "Sau khi khách hàng chọn lại ghế đã chọn hoặc hủy bỏ vé trong giỏ " +
                    "thì API này thực thi để xóa chỗ đã đặt",
            tags = "API Chọn trả chỗ")
    @RequestMapping(value = "/tracho", method = RequestMethod.DELETE)
    public ResponseEntity traCho(@RequestBody TrangThaiGheRequest trangThaiGheRequest) {
        if(gheService.xoaDatChoTam(trangThaiGheRequest))
            return new ResponseEntity<>(true, HttpStatus.OK);
        return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Lấy danh sách ghế",
            description = "Khi Click chọn vào toa nào thì sẽ thực thi API này để lấy ra danh sách ghế theo toa, nếu ghế nào có trạng thái là 0 có nghĩa đã được đặt",
            tags = "API Get danh sách ghế theo toa")
    @PostMapping(value = "/ghes")
    public ResponseEntity getDanhSachGhe(@RequestBody GheRequest gheRequest) {
        Set<Ghe> ghes = gheService.getGhesTheoMaToa(gheRequest);
        if(ghes.size() == 0) {
            return new ResponseEntity<>(ErrorResponse.builder().tenLoi("Lỗi lấy danh sách ghế").moTaLoi("Ghế không có trong cơ sở dữ liệu").build(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ghes, HttpStatus.OK);
    }

    public void xoaTrangThaiGhe() {
        trangThaiGheService.xoaTrangThaiGheDaHetHan();
    }
}
