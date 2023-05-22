package com.example.raiwayticketreservation.service.serviceImpl;

import com.example.raiwayticketreservation.entities.HoaDon;
import com.example.raiwayticketreservation.repository.HoaDonRepo;
import com.example.raiwayticketreservation.service.HoaDonService;
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
    public Long getIDHoaDonTheoMaDatChoHoacMaDatVe(String maDatCho, String maDatVe) {

        return hoaDonRepo.getIDHoaDon(maDatCho, maDatVe);
    }

    @Override
    public HoaDon getHoaDonByMaDatVe(String maDatVe) {
        return hoaDonRepo.getHDByMaDatVe(maDatVe);
    }

    @Override
    public void capNhatHoaDonTheoMaDatCho(String maDatVe, String tinhTrang, String maDatCho) {
        hoaDonRepo.capNhatHoaDonTheoMaDatCho(maDatVe, tinhTrang, maDatCho);
    }

}
