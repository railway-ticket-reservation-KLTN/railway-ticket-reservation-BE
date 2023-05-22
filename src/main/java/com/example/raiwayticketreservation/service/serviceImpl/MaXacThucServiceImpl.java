package com.example.raiwayticketreservation.service.serviceImpl;

import com.example.raiwayticketreservation.entities.KhachDatVe;
import com.example.raiwayticketreservation.entities.MaXacThuc;
import com.example.raiwayticketreservation.repository.MaXacThucRepo;
import com.example.raiwayticketreservation.service.MaXacThucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class MaXacThucServiceImpl implements MaXacThucService {

    @Autowired
    private MaXacThucRepo maXacThucRepo;

    @Override
    public MaXacThuc timMaXacThucTheoToken(String maXT) {
        return maXacThucRepo.timMaXacThucTheoMaXT(maXT);
    }

    @Override
    public void themMaXacThuc(KhachDatVe khachDatVe, String maXT) {
        MaXacThuc maXacThuc = MaXacThuc.builder()
                .khachDatVe(khachDatVe)
                .maXacThuc(maXT)
                .timestamp(tinhThoiHanMaXacThuc(12*60))
                .build();
        maXacThucRepo.save(maXacThuc);
    }

    private Timestamp tinhThoiHanMaXacThuc(int soPhutHetHan) {
        Calendar calendar =Calendar.getInstance();
        calendar.add(Calendar.MINUTE, soPhutHetHan);
        return new Timestamp(calendar.getTime().getTime());
    }
}
