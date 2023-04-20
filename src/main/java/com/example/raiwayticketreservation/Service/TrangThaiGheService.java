package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.dtos.requests.TrangThaiGheRequest;
import com.example.raiwayticketreservation.dtos.responses.TrangThaiGheResponse;

import java.util.Date;
import java.util.List;

public interface TrangThaiGheService {
    public List<TrangThaiGheResponse> getTrangThaiGhesByMaGheTenTauNgayDi(TrangThaiGheRequest trangThaiGheRequest);
    public void capNhatTrangThaiGhe(String maVeTau, String trangThai, Long id);

    public Long getIdTrangThaiGhe(String gaDi, String gaDen, String ngayDi, Long maGhe, int soToa, String trangThai);

    public void xoaTrangThaiGheByID(Long id);
}
