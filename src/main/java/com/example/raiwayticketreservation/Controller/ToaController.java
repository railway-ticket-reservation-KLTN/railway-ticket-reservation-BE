package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Service.ToaService;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import com.example.raiwayticketreservation.dtos.requests.TimToaRequest;
import com.example.raiwayticketreservation.dtos.responses.ToaResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/v1")
public class ToaController {
    @Autowired
    private ToaService toaService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Tìm toa theo tàu",
            description = "Khi Click chọn vào toa request tới API này để lấy ra danh sách toa theo tàu và theo hành trình",
            tags = "API Get danh sách toa")
    @PostMapping("/toas")
    public ResponseEntity getToaTauBangHanhTrinhIDTauID(@RequestBody TimToaRequest timToaRequest) {
        Set<ToaResponse> toaResponseSet;
        toaResponseSet = toaService.getToasByHanhTrinhIDTauID(timToaRequest.getHanhTrinhID(), timToaRequest.getTauID());
        if(toaResponseSet.size() == 0) {
            return new ResponseEntity(ErrorResponse.builder().tenLoi("Không tìm thấy toa").moTaLoi("Chưa có danh sách toa").build(), HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(toaResponseSet, HttpStatus.OK);
    }
}
