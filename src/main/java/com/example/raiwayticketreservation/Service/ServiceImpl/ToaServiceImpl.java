package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.Toa;
import com.example.raiwayticketreservation.Repository.ToaRepo;
import com.example.raiwayticketreservation.Service.ToaService;
import com.example.raiwayticketreservation.dtos.ToaResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ToaServiceImpl implements ToaService {
    @Autowired
    private ToaRepo toaRepo;
    @Override
    public Set<ToaResponse> getToasByHanhTrinhIDTauID(Long hanhTrinhID, Long tauID) {
        return toaRepo.getToasByHanhTrinhIDTauID(hanhTrinhID, tauID);
    }
}
