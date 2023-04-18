package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.CTTauToa;
import com.example.raiwayticketreservation.Entity.Tau;
import com.example.raiwayticketreservation.Entity.Toa;
import com.example.raiwayticketreservation.dtos.ToaTheoTauResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface TauService {
    public Set<Tau> getTauByHanhTrinhID(Long id);

    public ArrayList<ToaTheoTauResponse> getToaTheoTauByHanhTrinhIDTauID(Long hanhTrinhID);
}
