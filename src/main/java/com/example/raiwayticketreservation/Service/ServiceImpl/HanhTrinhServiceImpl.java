package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import com.example.raiwayticketreservation.Entity.VeTau;
import com.example.raiwayticketreservation.Repository.HanhTrinhRepo;
import com.example.raiwayticketreservation.Service.HanhTrinhService;
import com.example.raiwayticketreservation.dtos.requests.KiemTraVeRequest;
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

    @Override
    public Long getIDHanhTrinh(HanhTrinh hanhTrinh) {
        return hanhTrinhRepo.getIdHanhTrinh(hanhTrinh.getGaDi(), hanhTrinh.getGaDen(), hanhTrinh.getNgayDi());
    }

    @Override
    public HanhTrinh getHanhTrinhTheoMaHanhTrinh(Long hanhTrinhID) {
        return hanhTrinhRepo.getHanhTrinhByHanhTrinhID(hanhTrinhID);
    }
}
