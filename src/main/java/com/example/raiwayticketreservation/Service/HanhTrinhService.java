package com.example.raiwayticketreservation.Service;

import com.example.raiwayticketreservation.Entity.HanhTrinh;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Date;
import java.util.List;
import java.util.Set;


public interface HanhTrinhService{
    public List<HanhTrinh> getHanhTrinh(String gaDi, String gaDen, String ngayDi);

    public Long getIDHanhTrinh(HanhTrinh hanhTrinh);

    public HanhTrinh getHanhTrinhTheoMaHanhTrinh(Long hanhTrinhID);
}
