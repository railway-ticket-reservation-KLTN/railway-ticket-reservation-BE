package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.dtos.interfaceDTO.TrangThaiGheResponseProjection;
import com.example.raiwayticketreservation.entities.TrangThaiGhe;
import com.example.raiwayticketreservation.dtos.requests.TrangThaiGheRequest;
import com.example.raiwayticketreservation.dtos.responses.TrangThaiGheResponse;

import java.util.List;

public interface TrangThaiGheService {
    public List<TrangThaiGheResponseProjection> getTrangThaiGhesByMaGheTenTauNgayDi(TrangThaiGheRequest trangThaiGheRequest);
    public void capNhatTrangThaiGhe(String maVeTau, String trangThai, Long id);

    public Long getIdTrangThaiGhe(String gaDi, String gaDen, String ngayDi, Long maGhe, int soToa, String trangThai);

    public void xoaTrangThaiGheByID(Long id);

    public void xoaTrangThaiGheTheoMaVe(String maVe);

    public void capNhatThoiHanGiuGheTheoMaVe(String maVe);
    public List<TrangThaiGhe> getTrangThaiGheByThongTinHanhTrinh(String gaDi, String gaDen, String ngayDi, int soToa, String trangThai);


    public void xoaTrangThaiGheDaHetHan();
}
