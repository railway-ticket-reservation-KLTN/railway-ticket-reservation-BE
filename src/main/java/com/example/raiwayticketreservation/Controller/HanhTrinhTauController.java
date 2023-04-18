package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import com.example.raiwayticketreservation.Entity.Tau;
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

import java.util.*;

@RestController
@RequestMapping("/v1")
public class HanhTrinhTauController {
    @Autowired
    private HanhTrinhService hanhTrinhService;

    @Autowired
    private TauService tauService;

    @Operation(summary = "Tìm chuyến tàu",
            description = "Tìm chuyến tàu một chiều và khứ hồi",
            tags = "API Tìm vé")
    @GetMapping("/hanhtrinhtau")
    public ResponseEntity getHanhTrinhTauByGaDiGaDenNgayDiNgayDen(@RequestBody TimChuyenTauRequest timChuyenTauRequest) {
        if (timChuyenTauRequest.getLoaiHanhTrinh().equals("MOT_CHIEU")) {
            HanhTrinh hanhTrinh = hanhTrinhService.getHanhTrinh(timChuyenTauRequest.getGaDi(), timChuyenTauRequest.getGaDen(), timChuyenTauRequest.getNgayDi());
            if(hanhTrinh != null) {
                Set<Tau> taus = tauService.getTauByHanhTrinhID(hanhTrinh.getId());
                ArrayList toaTheoTaus = new ArrayList<>();
                toaTheoTaus.addAll(tauService.getToaTheoTauByHanhTrinhIDTauID(hanhTrinh.getId()));
                TimChuyenTauResponse timChuyenTauResponse = TimChuyenTauResponse.builder().id(hanhTrinh.getId()).hanhTrinh(hanhTrinh).taus(taus).toaTaus(toaTheoTaus).build();
                Set<TimChuyenTauResponse> timChuyenTauResponses = new HashSet<>();
                timChuyenTauResponses.add(timChuyenTauResponse);
                return new ResponseEntity(timChuyenTauResponses, HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .tenLoi("Không tìm thấy")
                        .moTaLoi("Không tìm thấy chuyến tàu")
                        .build();
                return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
            }
        } else if (timChuyenTauRequest.getLoaiHanhTrinh().equals("KHU_HOI")) {
            HanhTrinh hanhTrinhDi = hanhTrinhService.getHanhTrinh(timChuyenTauRequest.getGaDi(), timChuyenTauRequest.getGaDen(), timChuyenTauRequest.getNgayDi());
            HanhTrinh hanhTrinhVe = hanhTrinhService.getHanhTrinh(timChuyenTauRequest.getGaDen(), timChuyenTauRequest.getGaDi(), timChuyenTauRequest.getNgayVe());
            if (hanhTrinhDi != null && hanhTrinhVe != null) {
                Set<Tau> tauDis = tauService.getTauByHanhTrinhID(hanhTrinhDi.getId());
                ArrayList toaTheoTauDis = new ArrayList<>();
                toaTheoTauDis.addAll(tauService.getToaTheoTauByHanhTrinhIDTauID(hanhTrinhDi.getId()));
                TimChuyenTauResponse chuyenTauDiResponse = TimChuyenTauResponse.builder()
                        .id(hanhTrinhDi.getId())
                        .hanhTrinh(hanhTrinhDi)
                        .taus(tauDis)
                        .toaTaus(toaTheoTauDis)
                        .build();
                Set<Tau> tauVes = tauService.getTauByHanhTrinhID(hanhTrinhVe.getId());
                ArrayList toaTheoTauVes = new ArrayList<>();
                toaTheoTauVes.addAll(tauService.getToaTheoTauByHanhTrinhIDTauID(hanhTrinhVe.getId()));
                TimChuyenTauResponse chuyenTauVeResponse = TimChuyenTauResponse.builder()
                        .id(hanhTrinhVe.getId()).hanhTrinh(hanhTrinhVe)
                        .taus(tauVes)
                        .toaTaus(toaTheoTauVes)
                        .build();

                Set<TimChuyenTauResponse> timChuyenTauResponses = new HashSet<>();
                timChuyenTauResponses.add(chuyenTauDiResponse);
                timChuyenTauResponses.add(chuyenTauVeResponse);
                return new ResponseEntity(timChuyenTauResponses, HttpStatus.OK);
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
