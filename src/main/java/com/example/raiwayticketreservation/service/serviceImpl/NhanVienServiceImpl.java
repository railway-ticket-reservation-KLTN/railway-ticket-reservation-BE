package com.example.raiwayticketreservation.service.serviceImpl;

import com.example.raiwayticketreservation.entities.NhanVien;
import com.example.raiwayticketreservation.repository.NhanVienRepo;
import com.example.raiwayticketreservation.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhanVienServiceImpl implements NhanVienService {
    @Autowired
    NhanVienRepo nhanVienRepo;

    @Override
    public List<NhanVien> getDanhSachNhanVien() {
        return nhanVienRepo.findAll();
    }

    @Override
    public NhanVien themNhanVien(NhanVien nhanVien) {
        return nhanVienRepo.save(nhanVien);
    }

    @Override
    public boolean kiemTraNhanVienTonTai(NhanVien nhanVien) {
        if(nhanVienRepo.kiemTraNhanVienTonTai(nhanVien.getTenNhanVien(), nhanVien.getDiaChi()
                , nhanVien.getSdt(), nhanVien.getViTri()) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Long getIdNhanVien(NhanVien nhanVien) {
        return nhanVienRepo.getIdNhanVien(nhanVien.getTenNhanVien(), nhanVien.getDiaChi()
                , nhanVien.getSdt(), nhanVien.getViTri());
    }

}
