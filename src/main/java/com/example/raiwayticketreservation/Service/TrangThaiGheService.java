package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.dtos.TrangThaiGheRequest;
import com.example.raiwayticketreservation.dtos.TrangThaiGheResponse;

import java.util.List;

public interface TrangThaiGheService {
    public List<TrangThaiGheResponse> getTrangThaiGhesByMaGheTenTauNgayDi(TrangThaiGheRequest trangThaiGheRequest);
}
