package com.example.raiwayticketreservation.dtos.interfaceDTO;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

public interface TrangThaiGheResponseProjection {
    Long getId();
    String getGaDen();
    String getGaDi();
    String getTrangThai();
    Long getMaGhe();
    Date getNgayDi();
    Time getGioDi();
    Time getGioDen();
    String getTenTau();
    int getSoToa();
    Timestamp getThoiHanGiuGhe();
}
