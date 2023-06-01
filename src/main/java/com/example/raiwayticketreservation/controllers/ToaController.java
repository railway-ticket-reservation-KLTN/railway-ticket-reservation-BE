package com.example.raiwayticketreservation.controllers;

import com.example.raiwayticketreservation.dtos.interfaceDTO.ToaResponseProjection;
import com.example.raiwayticketreservation.entities.ToaResponseWithGhe;
import com.example.raiwayticketreservation.service.GheService;
import com.example.raiwayticketreservation.service.ToaService;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import com.example.raiwayticketreservation.dtos.requests.TimToaRequest;
import com.example.raiwayticketreservation.dtos.responses.ToaResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/khachhang")
public class ToaController {
    @Autowired
    private ToaService toaService;

    @Autowired
    private GheService gheService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Tìm toa theo tàu",
            description = "Khi Click chọn vào toa request tới API này để lấy ra danh sách toa theo tàu và theo hành trình",
            tags = "API Get danh sách toa")
    @PostMapping("/toas")
    public ResponseEntity getToaTauBangTauID(@RequestBody TimToaRequest timToaRequest) {

        Long maTau = timToaRequest.getTauID();
        Set<ToaResponseProjection> toaResponseSet = toaService.getToasByTauID(maTau);
        List<ToaResponseWithGhe> toaResponseWithGhes = new ArrayList<>();
        toaResponseSet.forEach(toaResponse -> {
            ToaResponseWithGhe toaResponseWithGhe = new ToaResponseWithGhe();
            Long maToa = toaResponse.getMaToa();
            int soToa = Integer.parseInt(toaResponse.getSoToa());
            if(toaResponseWithGhes.size() == 0) {
                toaResponseWithGhe = ToaResponseWithGhe.builder()
                        .maToa(maToa)
                        .tenToa(toaResponse.getTenToa())
                        .soToa(String.valueOf(soToa))
                        .tenTau(toaResponse.getTenTau())
                        .moTaToa(toaResponse.getMoTaToa())
                        .soLuongGhe(toaResponse.getSoLuongGhe())
                        .ghes(gheService.getDsGheTheoMaToaSoToa(maToa, maTau, soToa))
                        .build();
            } else {
                toaResponseWithGhe = ToaResponseWithGhe.builder()
                        .maToa(maToa)
                        .tenToa(toaResponse.getTenToa())
                        .soToa(String.valueOf(soToa))
                        .tenTau(toaResponse.getTenTau())
                        .moTaToa(toaResponse.getMoTaToa())
                        .soLuongGhe(toaResponse.getSoLuongGhe())
                        .build();
            }
            toaResponseWithGhes.add(toaResponseWithGhe);
        });

        if(toaResponseWithGhes.size() == 0) {
            return new ResponseEntity(ErrorResponse.builder().tenLoi("Không tìm thấy toa").moTaLoi("Chưa có danh sách toa").build(), HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(toaResponseWithGhes, HttpStatus.OK);
    }
}
