package com.example.raiwayticketreservation.service.serviceImpl;

import com.example.raiwayticketreservation.entities.HanhTrinh;
import com.example.raiwayticketreservation.repository.HanhTrinhRepo;
import com.example.raiwayticketreservation.service.HanhTrinhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@CacheConfig(cacheNames = {"hanhTrinhs"})
public class HanhTrinhServiceImpl implements HanhTrinhService {
    @Autowired
    private HanhTrinhRepo hanhTrinhRepo;

    @Override
    public List<HanhTrinh> getDanhSachHanhTrinh() {
        return hanhTrinhRepo.findAll();
    }

    @Cacheable
    @Override
    public List<HanhTrinh> getHanhTrinh(String gaDi, String gaDen, String ngayDi) {
        return hanhTrinhRepo.getHanhTrinhByGaDiGaDenNgayDiNgayDen(gaDi, gaDen, ngayDi);
    }

    @Override
    public Long getIDHanhTrinh(HanhTrinh hanhTrinh, Long maTau) {
        String strNgayDi = hanhTrinh.getNgayDi().toString();
        String strNgayDen = hanhTrinh.getNgayDen().toString();
        String strGioDi = hanhTrinh.getGioDi().toString();
        String strGioDen = hanhTrinh.getGioDen().toString();
        return hanhTrinhRepo.getIdHanhTrinh(hanhTrinh.getGaDi(), hanhTrinh.getGaDen(), strNgayDi,
                strNgayDen, strGioDi, strGioDen, maTau);
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
    public boolean kiemTraHanhTrinhTonTai(List<HanhTrinh> hanhTrinhs) {
        for (HanhTrinh hanhTrinh : hanhTrinhs) {
            if (hanhTrinh.getId() == null) {
                if (hanhTrinhRepo.kiemTraHanhTrinhTonTai(hanhTrinh.getGaDi(), hanhTrinh.getGaDen(), hanhTrinh.getNgayDi().toString(),
                        hanhTrinh.getNgayDen().toString(), String.valueOf(hanhTrinh.getGioDi()), String.valueOf(hanhTrinh.getGioDen()), hanhTrinh.getTau().getId()) > 0) {
                    return true;
                } else return false;
            } else if (hanhTrinh.getId() != null) {
                if (hanhTrinhRepo.existsById(hanhTrinh.getId())) {
                    return true;
                } else return false;
            }
        }
        return false;
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
