package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Entity.Ghe;
import com.example.raiwayticketreservation.Entity.TrangThaiGhe;
import com.example.raiwayticketreservation.Repository.GheRepo;
import com.example.raiwayticketreservation.Repository.TrangThaiGheRepo;
import com.example.raiwayticketreservation.Service.GheService;
import com.example.raiwayticketreservation.constants.SystemConstant;
import com.example.raiwayticketreservation.dtos.requests.GheRequest;
import com.example.raiwayticketreservation.dtos.requests.TrangThaiGheRequest;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import com.example.raiwayticketreservation.dtos.responses.GheResponse;
import com.example.raiwayticketreservation.dtos.responses.TrangThaiGheResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Calendar;
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
            Date ngayDi =  Date.valueOf(trangThaiGheRequest.getNgayDi());
            TrangThaiGhe trangThaiGhe = TrangThaiGhe.builder()
                    .ghe(ghe)
                    .gaDi(trangThaiGheRequest.getGaDi())
                    .gaDen(trangThaiGheRequest.getGaDen())
                    .ngayDi(ngayDi)
                    .tenTau(trangThaiGheRequest.getTenTau())
                    .soToa(trangThaiGheRequest.getSoToa())
                    .gioDen(trangThaiGheRequest.getGioDen())
                    .gioDi(trangThaiGheRequest.getGioDi())
                    .thoiHanGiuGhe(tinhThoiHanMaXacThuc(600))
                    .trangThai(trangThaiGheRequest.getTrangThai()).build();

            trangThaiGheRepo.save(trangThaiGhe);
            return new ResponseEntity(HttpStatus.OK);
        } else
            return new ResponseEntity(ErrorResponse.builder().tenLoi("Lỗi đặt chỗ").moTaLoi("Chỗ đã được đặt").build(), HttpStatus.LOCKED);
    }

    public boolean kiemTraDatCho(List<TrangThaiGheResponse> trangThaiGheResponses, TrangThaiGheRequest trangThaiGheRequest) throws ParseException {
        for (int i = 0; i < trangThaiGheResponses.size(); i++) {
            Time gioDiRequest = trangThaiGheRequest.getGioDi();
            Time gioDenResponse = trangThaiGheResponses.get(i).getGioDen();
            if (trangThaiGheRequest.getGaDi().equals(trangThaiGheResponses.get(i).getGaDi())
                    || trangThaiGheRequest.getGaDen().equals(trangThaiGheResponses.get(i).getGaDen())
                    || gioDiRequest.before(gioDenResponse)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean xoaDatChoTam(TrangThaiGheRequest trangThaiGheRequest) {
        Long trangThaigheid = trangThaiGheRepo.getIdByTrangThaiGheRequest(trangThaiGheRequest.getGaDi(), trangThaiGheRequest.getGaDen(), trangThaiGheRequest.getNgayDi(),
                trangThaiGheRequest.getMaGhe(), trangThaiGheRequest.getSoToa(), trangThaiGheRequest.getTrangThai());
        if (trangThaigheid != null) {
            trangThaiGheRepo.deleteById(trangThaigheid);
            return true;
        }
        return false;
    }

    @Override
    public Set<Ghe> getGhesTheoMaToa(GheRequest gheRequest) {
        Set<Ghe> dsGhe = gheRepo.getDsGheTheoMaToa(gheRequest.getMaToa());
        dsGhe.forEach(gheItem -> {
            List<TrangThaiGheResponse> trangThaiGhes = trangThaiGheRepo.getTrangThaiGhesBangMaGheTenTauNgayDiSoToa(gheItem.getId(), gheRequest.getTenTau(), gheRequest.getNgayDi(), gheRequest.getSoToa());
            trangThaiGhes.forEach(trangThaiGheResponse -> {
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                if(trangThaiGheResponse.getTrangThai() != null
                        &&  trangThaiGheResponse.getThoiHanGiuGhe().after(currentTime)
                        || trangThaiGheResponse.getTrangThai().equals(SystemConstant.DA_MUA)) {
                        gheItem.setTrangThai(0);
                }
            });
        });
        return dsGhe;
    }

    private Timestamp tinhThoiHanMaXacThuc(int soGiayHetHan) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, soGiayHetHan);
        return new Timestamp(calendar.getTime().getTime());
    }
}

