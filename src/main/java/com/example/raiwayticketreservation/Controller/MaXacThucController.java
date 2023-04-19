package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.KhachDatVe;
import com.example.raiwayticketreservation.Entity.MaXacThuc;
import com.example.raiwayticketreservation.Service.EmailService;
import com.example.raiwayticketreservation.Service.KhachDatVeService;
import com.example.raiwayticketreservation.Service.MaXacThucService;
import com.example.raiwayticketreservation.dtos.requests.XacThucRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/v1")
public class MaXacThucController {
    @Autowired
    private MaXacThucService maXacThucService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private KhachDatVeService khachDatVeService;

    @PostMapping("/guima")
    public ResponseEntity guiMaXacThucEmail(@RequestBody KhachDatVe khachDatVe) {
        Random random = new Random();
        int numRand = random.nextInt(999999);
        String maXacThuc = String.format("%06d", numRand);
        emailService.sendMessage(khachDatVe.getEmail(), "[RaiwalVN] Mã xác thực emai", "Xin chào"+khachDatVe.getHoTen()+"," +
                " mã xác thực của bạn là: "+maXacThuc);

        KhachDatVe khachDatVeResult = KhachDatVe.builder()
                .id(khachDatVeService.getIDKhachDat(khachDatVe))
                .build();
;
        maXacThucService.themMaXacThuc(khachDatVeResult, maXacThuc);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/xacthuc")
    public ResponseEntity xacThucEmail(@RequestBody XacThucRequest xacThucRequest) {
        MaXacThuc maXacThuc = maXacThucService.timMaXacThucTheoToken(xacThucRequest.getMaXacThuc());
        if (maXacThuc != null) {
            return null;
        }
        return null;
    }
}
