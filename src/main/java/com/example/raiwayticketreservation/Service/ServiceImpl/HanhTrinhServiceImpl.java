package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import com.example.raiwayticketreservation.Entity.VeTau;
import com.example.raiwayticketreservation.Repository.HanhTrinhRepo;
import com.example.raiwayticketreservation.Service.HanhTrinhService;
import com.example.raiwayticketreservation.dtos.requests.KiemTraVeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class HanhTrinhServiceImpl implements HanhTrinhService {
    @Autowired
    private HanhTrinhRepo hanhTrinhRepo;

    @Override
    public List<HanhTrinh> getHanhTrinh(String gaDi, String gaDen, String ngayDi) {
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

    @Override
    public List<HanhTrinh> themHanhTrinhs(List<HanhTrinh> hanhTrinhs) {
        List<HanhTrinh> hanhTrinhList = new ArrayList<>();
        hanhTrinhs.forEach(hanhTrinh -> {
            HanhTrinh hanhTrinhReturn = hanhTrinhRepo.save(hanhTrinh);
            hanhTrinhList.add(hanhTrinhReturn);
        });
        return hanhTrinhList;
    }

    @Override
    public boolean xoaHanhTrinh(List<HanhTrinh> hanhTrinhs) {
        try {
            hanhTrinhs.forEach(hanhTrinh -> {
                hanhTrinhRepo.deleteById(hanhTrinh.getId());
            });
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean capNhatHanhTrinh(HanhTrinh hanhTrinh) {
        try {
            hanhTrinhRepo.capNhatHanhTrinh(hanhTrinh.getGaDi(), hanhTrinh.getGaDen(), hanhTrinh.getGioDi().toString(),
                    hanhTrinh.getGioDen().toString(), hanhTrinh.getNgayDi().toString(),
                    hanhTrinh.getNgayDen().toString(), String.valueOf(hanhTrinh.getGiaVe()),
                    hanhTrinh.getTau().getId(), hanhTrinh.getId());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


}
