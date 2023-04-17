package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.Ghe;
import com.example.raiwayticketreservation.Entity.TrangThaiGhe;
import com.example.raiwayticketreservation.Entity.VeTau;
import com.example.raiwayticketreservation.Repository.GheRepo;
import com.example.raiwayticketreservation.Repository.TrangThaiGheRepo;
import com.example.raiwayticketreservation.Service.GheService;
import com.example.raiwayticketreservation.dtos.ErrorResponse;
import com.example.raiwayticketreservation.dtos.GheResponse;
import com.example.raiwayticketreservation.dtos.TrangThaiGheRequest;
import com.example.raiwayticketreservation.dtos.TrangThaiGheResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    public ResponseEntity datChoTam(TrangThaiGheRequest trangThaiGheRequest) throws ParseException {

        List<TrangThaiGheResponse> trangThaiGheResponses = trangThaiGheRepo.getTrangThaiGhesBangMaGheTenTauNgayDiSoToa(trangThaiGheRequest.getMaGhe(), trangThaiGheRequest.getTenTau(), trangThaiGheRequest.getNgayDi(), trangThaiGheRequest.getSoToa());

        if (kiemTraDatCho(trangThaiGheResponses, trangThaiGheRequest)) {
            Ghe ghe = Ghe.builder().id(trangThaiGheRequest.getMaGhe()).build();

//            VeTau veTau = VeTau.builder().id(trangThaiGheRequest.getMaVe()).build();

            TrangThaiGhe trangThaiGhe = TrangThaiGhe.builder()
                    .ghe(ghe)
                    .gaDi(trangThaiGheRequest.getGaDi())
                    .gaDen(trangThaiGheRequest.getGaDen())
                    .ngayDi(trangThaiGheRequest.getNgayDi())
                    .tenTau(trangThaiGheRequest.getTenTau())
                    .soToa(trangThaiGheRequest.getSoToa())
                    .gioDen(trangThaiGheRequest.getGioDen())
                    .gioDi(trangThaiGheRequest.getGioDi())
                    .trangThai(trangThaiGheRequest.getTrangThai()).build();

            trangThaiGheRepo.save(trangThaiGhe);
            return new ResponseEntity(HttpStatus.OK);
        } else
        return new ResponseEntity(ErrorResponse.builder().tenLoi("Lỗi đặt chỗ").moTaLoi("Chỗ đã được đặt").build(), HttpStatus.LOCKED);
    }

    public boolean kiemTraDatCho(List<TrangThaiGheResponse> trangThaiGheResponses, TrangThaiGheRequest trangThaiGheRequest) throws ParseException {
        for(int i = 0; i < trangThaiGheResponses.size(); i++) {
            DateFormat dateFormat = new SimpleDateFormat("hh:mm");
            Date gioDiRequest =  dateFormat.parse(trangThaiGheRequest.getGioDi());
            Date gioDenResponse =  dateFormat.parse(trangThaiGheResponses.get(i).getGioDen());
            if(trangThaiGheRequest.getGaDi().equals(trangThaiGheResponses.get(i).getGaDi())
                    || trangThaiGheRequest.getGaDen().equals(trangThaiGheResponses.get(i).getGaDen())
                    || gioDiRequest.before(gioDenResponse)){
                return false;
            }
        }
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
