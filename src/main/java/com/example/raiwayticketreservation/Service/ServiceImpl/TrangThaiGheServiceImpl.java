package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.TrangThaiGhe;
import com.example.raiwayticketreservation.Repository.TrangThaiGheRepo;
import com.example.raiwayticketreservation.Service.TrangThaiGheService;
import com.example.raiwayticketreservation.dtos.requests.TrangThaiGheRequest;
import com.example.raiwayticketreservation.dtos.responses.TrangThaiGheResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrangThaiGheServiceImpl implements TrangThaiGheService {
    @Autowired
    private TrangThaiGheRepo trangThaiGheRepo;
    @Override
    public List<TrangThaiGheResponse> getTrangThaiGhesByMaGheTenTauNgayDi(TrangThaiGheRequest trangThaiGheRequest) {
        return trangThaiGheRepo.getTrangThaiGhesBangMaGheTenTauNgayDiSoToa(trangThaiGheRequest.getMaGhe(),
                trangThaiGheRequest.getTenTau(), trangThaiGheRequest.getNgayDi(),
                trangThaiGheRequest.getSoToa());
    }

    @Override
    public void capNhatTrangThaiGhe(String maVeTau, String trangThai, Long id) {
        trangThaiGheRepo.updateTrangThaiGheByID(maVeTau, trangThai, id);
    }

    @Override
    public Long getIdTrangThaiGhe(String gaDi, String gaDen, String ngayDi, Long maGhe, int soToa, String trangThai) {
        return trangThaiGheRepo.getIdByTrangThaiGheRequest(gaDi, gaDen, ngayDi, maGhe, soToa, trangThai);
    }

    @Override
    public void xoaTrangThaiGheByID(Long id) {
        trangThaiGheRepo.deleteById(id);
    }
}
