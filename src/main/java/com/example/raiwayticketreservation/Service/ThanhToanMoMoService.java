package com.example.raiwayticketreservation.Service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ThanhToanMoMoService {
    public Object getDataThanhToanMoMo(Map<String, Long> param);

}
