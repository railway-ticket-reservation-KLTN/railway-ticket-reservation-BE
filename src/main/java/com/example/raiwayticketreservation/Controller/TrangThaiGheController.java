package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Service.TrangThaiGheService;
import com.example.raiwayticketreservation.dtos.TrangThaiGheRequest;
import com.example.raiwayticketreservation.dtos.TrangThaiGheResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class TrangThaiGheController {
    @Autowired
    private TrangThaiGheService trangThaiGheService;
    @GetMapping("/trangthaighes")
    public List<TrangThaiGheResponse> getTrangThaiGhes(@RequestBody TrangThaiGheRequest trangThaiGheRequest) {
        return trangThaiGheService.getTrangThaiGhesByMaGheTenTauNgayDi(trangThaiGheRequest);
    }
}

