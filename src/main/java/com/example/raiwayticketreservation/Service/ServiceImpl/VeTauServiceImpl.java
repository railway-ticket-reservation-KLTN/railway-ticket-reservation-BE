package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.VeTau;
import com.example.raiwayticketreservation.Repository.VeTauRepo;
import com.example.raiwayticketreservation.Service.VeTauService;
import com.example.raiwayticketreservation.constants.SystemConstant;
import com.example.raiwayticketreservation.dtos.requests.KiemTraVeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class VeTauServiceImpl implements VeTauService {
    @Autowired
    private VeTauRepo veTauRepo;

    @Override
    public List<VeTau> themVe(Set<VeTau> veTaus) {
        veTaus.forEach(veTau -> {
            veTau.setThoiGianGiuVe(tinhThoiHanGiuVe(3));
        });
        return veTauRepo.saveAll(veTaus);
    }

    @Override
    public Long getIDVeTau(VeTau veTau) {
        return veTauRepo.getIDVeTau(veTau.getTenHanhKhach(), veTau.getSoGiayTo(),
                veTau.getKhachDatVe().getId(), veTau.getHanhTrinh().getId());
    }

    @Override
    public VeTau getVeTheoMaVe(KiemTraVeRequest kiemTraVeRequest) {
        return veTauRepo.getVeTheoMaVe(kiemTraVeRequest.getMaVe());
    }

    @Override
    public VeTau getVeTauTheoID(Long id) {
        return veTauRepo.getVeTheoId(id);
    }

    @Override
    public void capNhatTrangThaiTinhTrangVeTau(Long maVeTau, String trangThai) {
        if(trangThai.equals(SystemConstant.TRA_VE)) {
            veTauRepo.capNhatTinhTrangVeTauTheoID(SystemConstant.TRA_VE, maVeTau);
        } else if (trangThai.equals(SystemConstant.DA_MUA)) {
            veTauRepo.capNhatTinhTrangVeTauTheoID(SystemConstant.DA_MUA, maVeTau);
        }
    }

    @Override
    public Set<VeTau> getVeTauTheoMaKhachDat(Long maKhachDat) {
        return veTauRepo.getVeTauTheoMaKhachDat(maKhachDat);
    }

    @Override
    public Set<VeTau> getVeTauByMaDatCho(Long maDatCho) {
        return veTauRepo.getIdVeByMaDatCho(maDatCho.toString());
    }

    private Timestamp tinhThoiHanGiuVe(int soPhutHetHan) {
        Calendar calendar =Calendar.getInstance();
        calendar.add(Calendar.MINUTE, soPhutHetHan);
        return new Timestamp(calendar.getTime().getTime());
    }

    @Override
    @Scheduled(cron = "0 */1 * * * *")
    public void capNhatVeTauHetHanThanhToan() {
        LocalDateTime dt = LocalDateTime.now();
        log.info("Thời gian cập nhật: " + dt);
        veTauRepo.capNhatVeHetHan();
    }
}
