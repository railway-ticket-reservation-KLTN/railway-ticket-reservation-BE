package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.TrangThaiGhe;
import com.example.raiwayticketreservation.dtos.DatChoResponse;
import com.example.raiwayticketreservation.dtos.GheResponse;
import com.example.raiwayticketreservation.dtos.TrangThaiGheRequest;

import java.util.Set;

public interface GheService {
    public Set<GheResponse> getGheTheoToaID(Long id);

    public boolean datChoTam(TrangThaiGheRequest trangThaiGheRequest);

    public boolean xoaDatChoTam(TrangThaiGheRequest trangThaiGheRequest);
}
