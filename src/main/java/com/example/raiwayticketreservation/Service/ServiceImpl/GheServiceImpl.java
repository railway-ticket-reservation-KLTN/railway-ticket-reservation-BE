package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.Ghe;
import com.example.raiwayticketreservation.Entity.TrangThaiGhe;
import com.example.raiwayticketreservation.Entity.VeTau;
import com.example.raiwayticketreservation.Repository.GheRepo;
import com.example.raiwayticketreservation.Repository.TrangThaiGheRepo;
import com.example.raiwayticketreservation.Service.GheService;
import com.example.raiwayticketreservation.dtos.DatChoResponse;
import com.example.raiwayticketreservation.dtos.GheResponse;
import com.example.raiwayticketreservation.dtos.TrangThaiGheRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class GheServiceImpl implements GheService {

    @Autowired
    private GheRepo gheRepo;

    @Autowired
    private TrangThaiGheRepo trangThaiGheRepo;
    @Override
    public Set<GheResponse> getGheTheoToaID(Long id) {
        return gheRepo.getGheByToaId(id);
    }

    @Override
    public boolean datChoTam(TrangThaiGheRequest trangThaiGheRequest) {
        Ghe ghe = Ghe.builder().id(trangThaiGheRequest.getMaGhe()).build();

        VeTau veTau = VeTau.builder().id(trangThaiGheRequest.getMaVe()).build();
        TrangThaiGhe trangThaiGhe = TrangThaiGhe.builder().gaDi(trangThaiGheRequest.getGaDi()).
        gaDen(trangThaiGheRequest.getGaDen()).
        ghe(ghe).veTau(veTau).trangThai(trangThaiGheRequest.getTrangThai()).build();

        trangThaiGheRepo.save(trangThaiGhe);
        return true;
    }

    @Override
    public boolean xoaDatChoTam(TrangThaiGheRequest trangThaiGheRequest) {
        Long trangThaigheid = trangThaiGheRepo.getIdByTrangThaiGheRequest(trangThaiGheRequest.getGaDi(), trangThaiGheRequest.getGaDen(),
                trangThaiGheRequest.getMaGhe(), trangThaiGheRequest.getMaVe(), trangThaiGheRequest.getTrangThai());
        if(trangThaigheid != null) {
            trangThaiGheRepo.deleteById(trangThaigheid);
            return true;
        } else return false;
    }
}
