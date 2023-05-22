package com.example.raiwayticketreservation.service.serviceImpl;

import com.example.raiwayticketreservation.entities.Tau;
import com.example.raiwayticketreservation.repository.TauRepo;
import com.example.raiwayticketreservation.service.TauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TauServiceImpl implements TauService {
    @Autowired
    private TauRepo tauRepo;

    @Override
    public Long getIdTauTheoTenTau(String tenTau) {
        return tauRepo.getIdTauByTenTau(tenTau);
    }

    @Override
    public List<Tau> getDanhSachTau() {
        return tauRepo.findAll();
    }
}
