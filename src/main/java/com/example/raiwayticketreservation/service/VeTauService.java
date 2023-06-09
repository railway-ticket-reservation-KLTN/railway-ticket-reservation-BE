package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.entities.VeTau;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface VeTauService {
    public List<VeTau> getDanhSachVe();
    public List<VeTau> themVe(Set<VeTau> veTau);
    public Long getIDVeTau(VeTau veTau);

    public VeTau getVeTheoMaVe(String  maVe);

    public VeTau getVeTauTheoID(Long Id);

    public void capNhatTrangThaiTinhTrangVeTau(Long maVeTau, String trangThai);

    public void capNhatTinhTrangVeTau(String maVe, String tinhTrang);

    public Set<VeTau> getVeTauTheoMaKhachDat(Long maKhachDat);

    public Set<VeTau> getVeTauByMaDatCho(Long maDatCho);

    public int getTongSoVeBanTrongThangHienTai();

    public double getDoanhThuBanVeTrongThangHienTai();

    public List<Map<String, Object>> getDoanhThuTheoTungThangTrongNam();

    public int getSoVeBanTheoNam(int nam);

    public double getDoanhThuBanTheoNam(int nam);

    public int getSoVeBanTheoNamThang(int nam, int thang);

    public double getDoanhThuBanTheoNamThang(int nam, int thang);

    public void capNhatVeTauHetHanThanhToan();
}
