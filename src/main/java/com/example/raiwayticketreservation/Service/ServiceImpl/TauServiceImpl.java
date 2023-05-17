package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Repository.TauRepo;
import com.example.raiwayticketreservation.Service.TauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TauServiceImpl implements TauService {
    @Autowired
    private TauRepo tauRepo;

    @Override
    public Long getIdTauTheoTenTau(String tenTau) {
        return tauRepo.getIdTauByTenTau(tenTau);
    }
}
