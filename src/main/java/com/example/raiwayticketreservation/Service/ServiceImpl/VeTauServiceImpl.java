package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.VeTau;
import com.example.raiwayticketreservation.Repository.VeTauRepo;
import com.example.raiwayticketreservation.Service.VeTauService;
import com.example.raiwayticketreservation.dtos.requests.KiemTraVeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class VeTauServiceImpl implements VeTauService {

    @Autowired
    private VeTauRepo veTauRepo;

    @Override
    public List<VeTau> themVe(Set<VeTau> veTaus) {
        return veTauRepo.saveAll(veTaus);
    }

    @Override
    public Long getIDVeTau(VeTau veTau) {
        return veTauRepo.getIDVeTau(veTau.getTenHanhKhach(), veTau.getSoGiayTo(),
                veTau.getKhachDatVe().getId(), veTau.getHanhTrinh().getId());
    }

    @Override
    public VeTau getVeTheoMaVe(KiemTraVeRequest kiemTraVeRequest) {
        return veTauRepo.getVeTheoMaVe(kiemTraVeRequest.getMaVe());
    }
}
