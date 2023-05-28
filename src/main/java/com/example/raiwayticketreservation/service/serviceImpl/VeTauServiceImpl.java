package com.example.raiwayticketreservation.service.serviceImpl;

import com.example.raiwayticketreservation.entities.VeTau;
import com.example.raiwayticketreservation.repository.VeTauRepo;
import com.example.raiwayticketreservation.service.VeTauService;
import com.example.raiwayticketreservation.constants.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    public List<VeTau> getDanhSachVe() {
        return veTauRepo.findAll();
    }

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
    public VeTau getVeTheoMaVe(String maVe) {
        return veTauRepo.getVeTheoMaVe(maVe);
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
    public void capNhatTinhTrangVeTau(String maVe, String tinhTrang) {
        veTauRepo.capNhatTinhTrangVeTauTheoMaVe(tinhTrang, maVe);
    }

    @Override
    public Set<VeTau> getVeTauTheoMaKhachDat(Long maKhachDat) {
        return veTauRepo.getVeTauTheoMaKhachDat(maKhachDat);
    }

    @Override
    public Set<VeTau> getVeTauByMaDatCho(Long maDatCho) {
        return veTauRepo.getVeByMaDatCho(maDatCho.toString());
    }

    @Override
    public int getTongSoVeBanTrongThangHienTai() {
        return veTauRepo.getSoVeBanTrongThang();
    }

    @Override
    public double getDoanhThuBanVeTrongThangHienTai() {
        return veTauRepo.getDoanhThuBanTrongThang();
    }

    @Override
    public List<Map<String, Object>> getDoanhThuTheoTungThangTrongNam() {
        return veTauRepo.getDoanhThuTheoTungThangONamHienTai();
    }

    private Timestamp tinhThoiHanGiuVe(int soPhutHetHan) {
        Calendar calendar =Calendar.getInstance();
        calendar.add(Calendar.MINUTE, soPhutHetHan);
        return new Timestamp(calendar.getTime().getTime());
    }

//    @Override
//    @Scheduled(cron = "0 */1 * * * *")
//    public void capNhatVeTauHetHanThanhToan() {
//        LocalDateTime dt = LocalDateTime.now();
//        log.info("Thời gian cập nhật: " + dt);
//        veTauRepo.capNhatVeHetHan();
//    }
}
