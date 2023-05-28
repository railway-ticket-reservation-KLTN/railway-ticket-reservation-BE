package com.example.raiwayticketreservation.service.serviceImpl;

import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import com.example.raiwayticketreservation.entities.Ghe;
import com.example.raiwayticketreservation.entities.TrangThaiGhe;
import com.example.raiwayticketreservation.repository.GheRepo;
import com.example.raiwayticketreservation.repository.TrangThaiGheRepo;
import com.example.raiwayticketreservation.service.GheService;
import com.example.raiwayticketreservation.service.TauService;
import com.example.raiwayticketreservation.constants.SystemConstant;
import com.example.raiwayticketreservation.dtos.requests.GheRequest;
import com.example.raiwayticketreservation.dtos.requests.TrangThaiGheRequest;
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
import java.util.Calendar;
import java.util.List;
import java.util.Set;

@Service
public class GheServiceImpl implements GheService {

    @Autowired
    private GheRepo gheRepo;

    @Autowired
    private TrangThaiGheRepo trangThaiGheRepo;

    @Autowired
    private TauService tauService;

    @Override
    public Set<GheResponse> getGheTheoToaID(Long id) {
        return gheRepo.getGheByToaId(id);
    }

    @Override
    public ResponseEntity datChoTam(TrangThaiGheRequest trangThaiGheRequest) throws ParseException {
        int soGiayHetHan = 600;
        List<TrangThaiGheResponse> trangThaiGheResponses = trangThaiGheRepo.getTrangThaiGhesBangMaGheTenTauNgayDiSoToa(trangThaiGheRequest.getMaGhe(),
                trangThaiGheRequest.getTenTau(), trangThaiGheRequest.getNgayDi(), trangThaiGheRequest.getSoToa());
        if(kiemTraDatCho(trangThaiGheResponses, trangThaiGheRequest)) {
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
                    .thoiHanGiuGhe(tinhThoiHanMaXacThuc(soGiayHetHan))
                    .trangThai(trangThaiGheRequest.getTrangThai()).build();

            trangThaiGheRepo.save(trangThaiGhe);
            return new ResponseEntity(soGiayHetHan, HttpStatus.OK);
        } else return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi đặt chỗ")
                .moTaLoi("Ghế đã được đặt").build(), HttpStatus.BAD_REQUEST);
    }

    public boolean kiemTraDatCho(List<TrangThaiGheResponse> trangThaiGheResponses, TrangThaiGheRequest trangThaiGheRequest) throws ParseException {
        for (TrangThaiGheResponse trangThaiGheResponse : trangThaiGheResponses) {
            Time gioDiRequest = trangThaiGheRequest.getGioDi();
            Time gioDenResponse = trangThaiGheResponse.getGioDen();
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            if (trangThaiGheResponse.getTrangThai().equals(SystemConstant.DAT_CHO)
                    && trangThaiGheResponse.getThoiHanGiuGhe().after(currentTime)
                    || trangThaiGheResponse.getTrangThai().equals(SystemConstant.DA_MUA)) {

                if (trangThaiGheResponse.getGaDi().equals(trangThaiGheRequest.getGaDi())
                        || trangThaiGheResponse.getGaDen().equals(trangThaiGheRequest.getGaDen())
                        || gioDenResponse.after(gioDiRequest)
                        && trangThaiGheResponse.getNgayDi().toString().equals(trangThaiGheRequest.getNgayDi())) {
                    return false;
                }
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
        Long idTau = tauService.getIdTauTheoTenTau(gheRequest.getTenTau());
        Set<Ghe> dsGhe = gheRepo.getDsGheTheoMaToaSoToa(gheRequest.getMaToa(), idTau, gheRequest.getSoToa());
        dsGhe.forEach(gheItem -> {
            List<TrangThaiGheResponse> trangThaiGhes = trangThaiGheRepo.getTrangThaiGhesBangMaGheTenTauNgayDiSoToa(gheItem.getId(), gheRequest.getTenTau(), gheRequest.getNgayDi(), gheRequest.getSoToa());
            trangThaiGhes.forEach(trangThaiGheResponse -> {
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                if(trangThaiGheResponse.getTrangThai().equals(SystemConstant.DAT_CHO)
                        &&  trangThaiGheResponse.getThoiHanGiuGhe().after(currentTime)
                        || trangThaiGheResponse.getTrangThai().equals(SystemConstant.DA_MUA)) {

                    if( trangThaiGheResponse.getGaDi().equals(gheRequest.getGaDi())
                            || trangThaiGheResponse.getGaDen().equals(gheRequest.getGaDen())
                            || trangThaiGheResponse.getGioDen().after(gheRequest.getGioDi())
                            && trangThaiGheResponse.getNgayDi().toString().equals(gheRequest.getNgayDi()))
                    {
                        if(trangThaiGheResponse.getTrangThai().equals(SystemConstant.DA_MUA)){
                            gheItem.setTrangThai(0);
                        } else if (trangThaiGheResponse.getTrangThai().equals(SystemConstant.DAT_CHO)) {
                        gheItem.setTrangThai(2);

                        }
                    }
                }
            });
        });
        return dsGhe;
    }

    @Override
    public int getSoGheTheoMaGhe(Long maGhe) {
        return gheRepo.getSoGheByGheID(maGhe);
    }

    @Override
    public Set<Ghe> getDsGheTheoMaToaSoToa(Long maToa, Long maTau, int soToa) {
        return gheRepo.getDsGheTheoMaToaSoToa(maToa, maTau, soToa);
    }


    private Timestamp tinhThoiHanMaXacThuc(int soGiayHetHan) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, soGiayHetHan);
        return new Timestamp(calendar.getTime().getTime());
    }
}

