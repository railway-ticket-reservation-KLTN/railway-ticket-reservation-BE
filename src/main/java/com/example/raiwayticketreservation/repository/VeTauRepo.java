package com.example.raiwayticketreservation.repository;

import com.example.raiwayticketreservation.entities.VeTau;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface VeTauRepo extends JpaRepository<VeTau, Long> {

    @Query(value = "SELECT id FROM railwayticketreservationdb.vetau\n" +
            "WHERE ten_hanh_khach = ? AND so_giay_to = ? " +
            "AND ma_khach_dat = ? AND ma_hanh_trinh = ?", nativeQuery = true)
    public Long getIDVeTau(String tenHanhKhach, String soGiayTo, Long maKhachDat, Long maHanhTrinh);
    @Query(value = "SELECT * FROM railwayticketreservationdb.vetau WHERE ma_ve = ?", nativeQuery = true)
    public VeTau getVeTheoMaVe(String maVe);

    @Query(value = "SELECT * FROM railwayticketreservationdb.vetau WHERE id = ?", nativeQuery = true)
    public VeTau getVeTheoId(Long id);
    @Query(value = "SELECT * FROM railwayticketreservationdb.vetau WHERE ma_khach_dat = ?", nativeQuery = true)
    public Set<VeTau> getVeTauTheoMaKhachDat(Long maKhachDat);
    @Modifying
    @Transactional
    @Query(value = "UPDATE railwayticketreservationdb.vetau \n" +
            "SET tinh_trang = ? \n" +
            "WHERE id = ?", nativeQuery = true)
    public void capNhatTinhTrangVeTauTheoID (String tinhTrang, Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE railwayticketreservationdb.vetau \n" +
            "SET tinh_trang = ? \n" +
            "WHERE (ma_ve = ?)", nativeQuery = true)
    public void capNhatTinhTrangVeTauTheoMaVe (String tinhTrang, String maVe);

    @Query(value = "SELECT * FROM railwayticketreservationdb.vetau\n" +
            "    WHERE ma_dat_cho = ?", nativeQuery = true)
    public Set<VeTau> getVeByMaDatCho(String maDatCho);
    @Modifying
    @Transactional
    @Query(value = "UPDATE railwayticketreservationdb.vetau SET tinh_trang = 'HET_HAN_THANH_TOAN'\n" +
            "WHERE  TIMESTAMPDIFF(MINUTE, thoi_gian_giu_ve, NOW()) >=0 " +
            "AND tinh_trang = 'CHUA_THANH_TOAN'", nativeQuery = true)
    public void capNhatVeHetHan();

    @Query(value = "SELECT count(*) as so_ve_ban_trong_thang FROM railwayticketreservationdb.vetau  \n" +
            "WHERE MONTH(ngay_mua) = MONTH(CURRENT_DATE())\n" +
            "AND YEAR(ngay_mua) = YEAR(CURRENT_DATE()) AND tinh_trang = 'DA_MUA';", nativeQuery = true)
    public int getSoVeBanTrongThang();

    @Query(value = "SELECT SUM(don_gia) as doanh_thu FROM railwayticketreservationdb.vetau  \n" +
            "WHERE MONTH(ngay_mua) = MONTH(CURRENT_DATE())\n" +
            "AND YEAR(ngay_mua) = YEAR(CURRENT_DATE()) AND tinh_trang = 'DA_MUA';", nativeQuery = true)
    public double getDoanhThuBanTrongThang();

    @Query(value = "SELECT MONTH(ngay_mua) as thang, sum(don_gia) as doanh_thu FROM railwayticketreservationdb.vetau \n" +
            "WHERE tinh_trang = 'DA_MUA'\n" +
            "GROUP BY thang;", nativeQuery = true)
    public List<Map<String, Object>> getDoanhThuTheoTungThangONamHienTai();

    @Query(value = "SELECT count(id) as so_ve FROM railwayticketreservationdb.vetau  \n" +
            "WHERE YEAR(ngay_mua) = ? " +
            "AND tinh_trang = 'DA_MUA';", nativeQuery = true)
    public int getSoVeBanTheoNam(int nam);

    @Query(value = "SELECT sum(don_gia) as doanh_thu FROM railwayticketreservationdb.vetau \n" +
            "WHERE YEAR(ngay_mua) = ? AND tinh_trang = 'DA_MUA'", nativeQuery = true)
    public double getDoanhThuBanTheoNam(int nam);

    @Query(value = "SELECT count(id) as so_ve FROM railwayticketreservationdb.vetau  \n" +
            "WHERE YEAR(ngay_mua) = ? AND MONTH(ngay_mua) = ? " +
            "AND tinh_trang = 'DA_MUA'", nativeQuery = true)
    public int getSoVeBanTheoThangVaNam(int nam, int thang);

    @Query(value = "SELECT sum(don_gia) as doanh_thu FROM railwayticketreservationdb.vetau \n" +
            "WHERE YEAR(ngay_mua) = ? AND MONTH(ngay_mua) = ? " +
            "AND tinh_trang = 'DA_MUA'", nativeQuery = true)
    public double getDoanhThuBanTheoThangVaNam(int nam, int thang);
}
