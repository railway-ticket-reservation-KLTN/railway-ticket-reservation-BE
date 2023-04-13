package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.TrangThaiGhe;
import com.example.raiwayticketreservation.Service.GheService;
import com.example.raiwayticketreservation.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.text.ParseException;
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
    public ResponseEntity datChoTam(@RequestBody TrangThaiGheRequest trangThaiGheRequest) throws ParseException {
        return gheService.datChoTam(trangThaiGheRequest);
    }

    @RequestMapping(value = "/tracho", method = RequestMethod.DELETE)
    public boolean traCho(@RequestBody TrangThaiGheRequest trangThaiGheRequest) {
        if(gheService.xoaDatChoTam(trangThaiGheRequest))
            return true;
        return false;
    }
}
