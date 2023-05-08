package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.NhanVien;
import com.example.raiwayticketreservation.Repository.NhanVienRepo;
import com.example.raiwayticketreservation.Service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NhanVienServiceImpl implements NhanVienService {
    @Autowired
    NhanVienRepo nhanVienRepo;

    @Override
    public NhanVien themNhanVien(NhanVien nhanVien) {
        return nhanVienRepo.save(nhanVien);
    }

}
