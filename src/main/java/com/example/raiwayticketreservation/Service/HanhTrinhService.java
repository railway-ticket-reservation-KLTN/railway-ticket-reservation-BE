package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.HanhTrinh;


public interface HanhTrinhService{
    public HanhTrinh getHanhTrinh(String gaDi, String gaDen, String ngayDi);

    public Long getIDHanhTrinh(HanhTrinh hanhTrinh);

    public HanhTrinh getHanhTrinhTheoMaHanhTrinh(Long hanhTrinhID);
}