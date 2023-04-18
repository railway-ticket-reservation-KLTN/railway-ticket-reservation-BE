package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.CTHD;
import com.example.raiwayticketreservation.Repository.CTHDRepo;
import com.example.raiwayticketreservation.Service.CTHDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CTHDServiceImpl implements CTHDService {
    @Autowired
    private CTHDRepo cthdRepo;
    @Override
    public List<CTHD> themCTHD(Set<CTHD> cthds) {
        List<CTHD> cthdList = new ArrayList<>();
        cthds.forEach(cthd -> {
            cthdRepo.themCTHD(cthd.getHoaDon().getId(), cthd.getVeTau().getId(), cthd.getDonGia());
            cthdList.add(cthd);
        });
        return cthdList;
    }

    @Override
    public Set<CTHD> getCTHDTheoHoaDonId(Long id) {
        return cthdRepo.getCTHDByHoaDonId(id);
    }
}
