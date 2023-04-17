package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.KhachDatVe;
import com.example.raiwayticketreservation.Repository.KhachDatVeRepo;
import com.example.raiwayticketreservation.Service.KhachDatVeService;
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
}
