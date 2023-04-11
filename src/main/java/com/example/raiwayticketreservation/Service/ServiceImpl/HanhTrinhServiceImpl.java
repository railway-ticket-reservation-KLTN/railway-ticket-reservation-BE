package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import com.example.raiwayticketreservation.Repository.HanhTrinhRepo;
import com.example.raiwayticketreservation.Service.HanhTrinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HanhTrinhServiceImpl implements HanhTrinhService {
    @Autowired
    private HanhTrinhRepo hanhTrinhRepo;

    @Override
    public HanhTrinh getHanhTrinh(String gaDi, String gaDen, String ngayDi) {
        return hanhTrinhRepo.getHanhTrinhByGaDiGaDenNgayDiNgayDen(gaDi, gaDen, ngayDi);
    }
}
