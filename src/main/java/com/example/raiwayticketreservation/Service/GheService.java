package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.dtos.GheResponse;
import com.example.raiwayticketreservation.dtos.TrangThaiGheRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.function.EntityResponse;

import java.text.ParseException;
import java.util.Set;

public interface GheService {
    public Set<GheResponse> getGheTheoToaID(Long id);

    public ResponseEntity datChoTam(TrangThaiGheRequest trangThaiGheRequest) throws ParseException;

    public boolean xoaDatChoTam(TrangThaiGheRequest trangThaiGheRequest);
}
