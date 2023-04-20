package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import com.example.raiwayticketreservation.Entity.VeTau;
import com.example.raiwayticketreservation.Repository.HanhTrinhRepo;
import com.example.raiwayticketreservation.Service.HanhTrinhService;
import com.example.raiwayticketreservation.dtos.requests.KiemTraVeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        String strNgayDi = hanhTrinh.getNgayDi().toString();
        String strNgayDen = hanhTrinh.getNgayDen().toString();
        String strGioDi = hanhTrinh.getGioDi().toString();
        String strGioDen = hanhTrinh.getGioDen().toString();
        return hanhTrinhRepo.getIdHanhTrinh(hanhTrinh.getGaDi(), hanhTrinh.getGaDen(), strNgayDi,
                strNgayDen, strGioDi, strGioDen);
    }

    @Override
    public HanhTrinh getHanhTrinhTheoMaHanhTrinh(Long hanhTrinhID) {
        return hanhTrinhRepo.getHanhTrinhByHanhTrinhID(hanhTrinhID);
    }
}
