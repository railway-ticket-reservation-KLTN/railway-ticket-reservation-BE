package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.dtos.requests.TrangThaiGheRequest;
import com.example.raiwayticketreservation.dtos.responses.TrangThaiGheResponse;

import java.util.List;

public interface TrangThaiGheService {
    public List<TrangThaiGheResponse> getTrangThaiGhesByMaGheTenTauNgayDi(TrangThaiGheRequest trangThaiGheRequest);
}
