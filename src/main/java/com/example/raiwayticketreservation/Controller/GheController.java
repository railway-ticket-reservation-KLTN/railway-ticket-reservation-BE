package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Service.GheService;
import com.example.raiwayticketreservation.dtos.GheResponse;
import com.example.raiwayticketreservation.dtos.ToaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/v1")
public class GheController {

    @Autowired
    private GheService gheService;

    @GetMapping("/ghes")
    public Set<GheResponse> getGheTheoMaToa(@RequestBody ToaRequest toaRequest) {
        return gheService.getGheTheoToaID(toaRequest.getId());
    }
}
