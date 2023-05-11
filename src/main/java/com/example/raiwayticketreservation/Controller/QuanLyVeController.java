package com.example.raiwayticketreservation.Controller;

import com.example.raiwayticketreservation.Entity.CTHD;
import com.example.raiwayticketreservation.Entity.HoaDon;
import com.example.raiwayticketreservation.Entity.VeTau;
import com.example.raiwayticketreservation.Service.*;
import com.example.raiwayticketreservation.constants.SystemConstant;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/nhanvien")
public class QuanLyVeController {

    @Autowired
    private HanhTrinhService hanhTrinhService;
    @Autowired
    private VeTauService veTauService;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private CTHDService cthdService;

    @Autowired
    private KhachDatVeService khachDatVeService;

    @Autowired
    private TrangThaiGheService trangThaiGheService;


}
