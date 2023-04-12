package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.TrangThaiGhe;
import com.example.raiwayticketreservation.Service.GheService;
import com.example.raiwayticketreservation.dtos.DatChoResponse;
import com.example.raiwayticketreservation.dtos.GheResponse;
import com.example.raiwayticketreservation.dtos.ToaRequest;
import com.example.raiwayticketreservation.dtos.TrangThaiGheRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/datcho")
    public boolean datChoTam(@RequestBody TrangThaiGheRequest trangThaiGheRequest) {
        if(gheService.datChoTam(trangThaiGheRequest))
            return true;
        return false;
    }

    @RequestMapping(value = "/tracho", method = RequestMethod.DELETE)
    public boolean traCho(@RequestBody TrangThaiGheRequest trangThaiGheRequest) {
        if(gheService.xoaDatChoTam(trangThaiGheRequest))
            return true;
        return false;
    }
}
