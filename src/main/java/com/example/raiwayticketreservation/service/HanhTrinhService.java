package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.entities.HanhTrinh;

import java.util.List;


public interface HanhTrinhService{
    public List<HanhTrinh> getDanhSachHanhTrinh();
    public List<HanhTrinh> getHanhTrinh(String gaDi, String gaDen, String ngayDi);

    public Long getIDHanhTrinh(HanhTrinh hanhTrinh, Long maTau);

    public HanhTrinh getHanhTrinhTheoMaHanhTrinh(Long hanhTrinhID);

    public List<HanhTrinh> themHanhTrinhs(List<HanhTrinh> hanhTrinhs);

    public boolean xoaHanhTrinh(List<HanhTrinh> hanhTrinhs);

    public boolean kiemTraHanhTrinhTonTai(List<HanhTrinh> hanhTrinhs);

    public boolean capNhatHanhTrinh(HanhTrinh hanhTrinh);

}
