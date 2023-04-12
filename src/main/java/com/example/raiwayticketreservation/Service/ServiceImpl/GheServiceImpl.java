package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Repository.GheRepo;
import com.example.raiwayticketreservation.Service.GheService;
import com.example.raiwayticketreservation.dtos.GheResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GheServiceImpl implements GheService {

    @Autowired
    private GheRepo gheRepo;
    @Override
    public Set<GheResponse> getGheTheoToaID(Long id) {
        return gheRepo.getGheByToaId(id);
    }
}
