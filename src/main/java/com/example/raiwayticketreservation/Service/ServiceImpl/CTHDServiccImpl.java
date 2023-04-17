package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.CTHD;
import com.example.raiwayticketreservation.Repository.CTHDRepo;
import com.example.raiwayticketreservation.Service.CTHDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CTHDServiccImpl implements CTHDService {
    @Autowired
    private CTHDRepo cthdRepo;
    @Override
    public List<CTHD> themCTHD(Set<CTHD> cthds) {
        return cthdRepo.saveAll(cthds);
    }
}
