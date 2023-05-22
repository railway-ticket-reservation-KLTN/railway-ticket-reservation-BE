package com.example.raiwayticketreservation.service.serviceImpl;

import com.example.raiwayticketreservation.entities.KhachDatVe;
import com.example.raiwayticketreservation.repository.KhachDatVeRepo;
import com.example.raiwayticketreservation.service.KhachDatVeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KhachDatVeServiceImpl implements KhachDatVeService {
    @Autowired
    private KhachDatVeRepo khachDatVeRepo;

    @Override
    public KhachDatVe themKhachDat(KhachDatVe khachDatVe) {
        return khachDatVeRepo.save(khachDatVe);
    }

    @Override
    public Long getIDKhachDat(KhachDatVe khachDatVe) {
        return khachDatVeRepo.getIDKhachDatVe(khachDatVe.getHoTen(), khachDatVe.getSdt(),
                khachDatVe.getEmail(), khachDatVe.getSoGiayTo());
    }

    @Override
    public KhachDatVe getKhachDatVeTheoID(Long id) {
        return khachDatVeRepo.getKhachDatByKhachDatID(id);
    }
}
