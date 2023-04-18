package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.HoaDon;
import com.example.raiwayticketreservation.Repository.HoaDonRepo;
import com.example.raiwayticketreservation.Service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepo hoaDonRepo;
    @Override
    public HoaDon themHoaDon(HoaDon hoaDon) {
        return hoaDonRepo.save(hoaDon);
    }

    @Override
    public Long getIDHoaDon(String ngayLap, Long maKhachDat) {
        return hoaDonRepo.getIDHoaDon(ngayLap, maKhachDat);
    }

    @Override
    public HoaDon getHoaDonByMaDatVe(String maDatVe) {
        return hoaDonRepo.getHDByMaDatVe(maDatVe);
    }
}
