package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import com.example.raiwayticketreservation.Entity.Tau;
import com.example.raiwayticketreservation.Service.HanhTrinhService;
import com.example.raiwayticketreservation.Service.TauService;
import com.example.raiwayticketreservation.dtos.TimChuyenTauRequest;
import com.example.raiwayticketreservation.dtos.TimChuyenTauResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/v1")
public class HanhTrinhTauController {
    @Autowired
    private HanhTrinhService hanhTrinhService;

    @Autowired
    private TauService tauService;

    @GetMapping("/hanhtrinhtau")
    public TimChuyenTauResponse getHanhTrinhTauByGaDiGaDenNgayDiNgayDen(@RequestBody TimChuyenTauRequest timChuyenTauRequest) {
        HanhTrinh hanhTrinh = hanhTrinhService.getHanhTrinh(timChuyenTauRequest.getGaDi(), timChuyenTauRequest.getGaDen(), timChuyenTauRequest.getNgayDi());
        Set<Tau> taus = tauService.getTauByHanhTrinhID(hanhTrinh.getId());
        TimChuyenTauResponse timChuyenTauResponse = TimChuyenTauResponse.builder().id(hanhTrinh.getId()).hanhTrinh(hanhTrinh).taus(taus).build();
        return timChuyenTauResponse;
    }
}
