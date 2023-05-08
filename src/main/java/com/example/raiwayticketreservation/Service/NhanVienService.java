package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.NhanVien;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NhanVienService {
    public NhanVien themNhanVien(NhanVien nhanVien);
}
