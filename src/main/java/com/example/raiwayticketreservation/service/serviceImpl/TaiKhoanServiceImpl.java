package com.example.raiwayticketreservation.service.serviceImpl;

import com.example.raiwayticketreservation.entities.TaiKhoan;
import com.example.raiwayticketreservation.repository.TaiKhoanRepo;
import com.example.raiwayticketreservation.service.TaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaiKhoanServiceImpl implements TaiKhoanService {
    @Autowired
    private TaiKhoanRepo taiKhoanRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public List<TaiKhoan> getDanhSachTaiKhoan() {
        return taiKhoanRepo.findAll();
    }

    @Override
    public TaiKhoan themTaiKhoan(TaiKhoan taiKhoan) {
        taiKhoan.setMatKhau(passwordEncoder.encode(taiKhoan.getMatKhau()));
        return taiKhoanRepo.save(taiKhoan);
    }

    @Override
    public boolean xoaTaiKhoan(TaiKhoan taiKhoan) {
        try {
            taiKhoanRepo.xoaTaiKhoan(taiKhoan.getId());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean capNhatTaiKhoan(TaiKhoan taiKhoan) {
        try {
            taiKhoan.setMatKhau(passwordEncoder.encode(taiKhoan.getMatKhau()));
            taiKhoanRepo.capNhatTaiKhoan(taiKhoan.getLoaiTK(), taiKhoan.getTenTaiKhoan(), taiKhoan.getMatKhau(),
                    taiKhoan.getNhanVien().getId(), taiKhoan.getTrangThai(), taiKhoan.getId());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean kiemTraTaiKhoanNhanVienTonTai(TaiKhoan taiKhoan) {
        if(taiKhoanRepo.kiemTraTaiKhoanTheoMaNhanVien(taiKhoan.getNhanVien().getId()) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean kiemTraTaiKhoanTheoMa(TaiKhoan taiKhoan) {
        if(taiKhoanRepo.existsById(taiKhoan.getId())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean kiemTraTaiKhoanConHoatDong(TaiKhoan taiKhoan) {
        if(taiKhoanRepo.getTrangThaiTheoTenTK(taiKhoan.getTenTaiKhoan()) == 1) {
            return true;
        }
        return false;
    }


}
