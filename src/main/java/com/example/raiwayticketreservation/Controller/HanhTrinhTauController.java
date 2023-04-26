package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import com.example.raiwayticketreservation.Service.HanhTrinhService;
import com.example.raiwayticketreservation.Service.TauService;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import com.example.raiwayticketreservation.dtos.requests.TimChuyenTauRequest;
import com.example.raiwayticketreservation.dtos.responses.TimChuyenTauResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/v1")
public class HanhTrinhTauController {
    @Autowired
    private HanhTrinhService hanhTrinhService;

    @Autowired
    private TauService tauService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Tìm chuyến tàu",
            description = "Tìm chuyến tàu một chiều và khứ hồi",
            tags = "API Tìm vé")
    @PostMapping("/hanhtrinhtau")
    public ResponseEntity getHanhTrinhTauByGaDiGaDenNgayDiNgayDen(@RequestBody TimChuyenTauRequest timChuyenTauRequest) throws ParseException {

        if (timChuyenTauRequest.getLoaiHanhTrinh().equals("MOT_CHIEU")) {
            List<HanhTrinh> hanhTrinhSet = hanhTrinhService.getHanhTrinh(timChuyenTauRequest.getGaDi(), timChuyenTauRequest.getGaDen(), timChuyenTauRequest.getNgayDi());
            if(hanhTrinhSet != null) {
                hanhTrinhSet.forEach(hanhTrinh -> {
                    Set toaTheoTaus = new HashSet<>();
                    toaTheoTaus.addAll(tauService.getToaTheoTauByHanhTrinhIDTauID(hanhTrinh.getId(), hanhTrinh.getTau().getId()));
                    hanhTrinh.getTau().setToas(toaTheoTaus);
                });
                return new ResponseEntity(hanhTrinhSet, HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .tenLoi("Không tìm thấy")
                        .moTaLoi("Không tìm thấy chuyến tàu")
                        .build();
                return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
            }
        } else if (timChuyenTauRequest.getLoaiHanhTrinh().equals("KHU_HOI")) {
            List<HanhTrinh> hanhTrinhDiSet = hanhTrinhService.getHanhTrinh(timChuyenTauRequest.getGaDi(), timChuyenTauRequest.getGaDen(), timChuyenTauRequest.getNgayDi());
            List<HanhTrinh> hanhTrinhVeSet = hanhTrinhService.getHanhTrinh(timChuyenTauRequest.getGaDi(), timChuyenTauRequest.getGaDen(), timChuyenTauRequest.getNgayVe());

            if(hanhTrinhDiSet != null && hanhTrinhVeSet != null ) {
                hanhTrinhDiSet.forEach(hanhTrinh -> {
                    Set toaTheoTaus = new HashSet<>();
                    toaTheoTaus.addAll(tauService.getToaTheoTauByHanhTrinhIDTauID(hanhTrinh.getId(), hanhTrinh.getTau().getId()));
                    hanhTrinh.getTau().setToas(toaTheoTaus);
                });

                hanhTrinhVeSet.forEach(hanhTrinh -> {
                    Set toaTheoTaus = new HashSet<>();
                    toaTheoTaus.addAll(tauService.getToaTheoTauByHanhTrinhIDTauID(hanhTrinh.getId(), hanhTrinh.getTau().getId()));
                    hanhTrinh.getTau().setToas(toaTheoTaus);
                });
                TimChuyenTauResponse timChuyenTauResponse = TimChuyenTauResponse.builder()
                        .hanhTrinhDi(hanhTrinhVeSet)
                        .hanhTrinhVe(hanhTrinhVeSet)
                        .build();
                return new ResponseEntity(timChuyenTauResponse, HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .tenLoi("Không tìm thấy")
                        .moTaLoi("Không tìm thấy chuyến tàu")
                        .build();
                return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
            }
        }
        ErrorResponse errorResponse = ErrorResponse.builder()
                .tenLoi("Không tìm thấy")
                .moTaLoi("Không tìm thấy chuyến tàu")
                .build();
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }
}
