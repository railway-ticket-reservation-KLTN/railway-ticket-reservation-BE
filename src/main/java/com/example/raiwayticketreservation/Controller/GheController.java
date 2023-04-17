package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.TrangThaiGhe;
import com.example.raiwayticketreservation.Service.GheService;
import com.example.raiwayticketreservation.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/datcho")
    public ResponseEntity datChoTam(@RequestBody TrangThaiGheRequest trangThaiGheRequest) throws ParseException {
        return gheService.datChoTam(trangThaiGheRequest);
    }

    @RequestMapping(value = "/tracho", method = RequestMethod.DELETE)
    public ResponseEntity traCho(@RequestBody TrangThaiGheRequest trangThaiGheRequest) {
        if(gheService.xoaDatChoTam(trangThaiGheRequest))
            return new ResponseEntity<>(true, HttpStatus.OK) ;
        return new ResponseEntity(false, HttpStatus.BAD_REQUEST);
    }
}
