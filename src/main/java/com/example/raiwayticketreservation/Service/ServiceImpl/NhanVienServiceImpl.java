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
