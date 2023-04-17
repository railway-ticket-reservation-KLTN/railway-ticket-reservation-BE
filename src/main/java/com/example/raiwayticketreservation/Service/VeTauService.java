package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.VeTau;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface VeTauService {
    public List<VeTau> themVe(Set<VeTau> veTau);
    public Long getIDVeTau(VeTau veTau);
}
