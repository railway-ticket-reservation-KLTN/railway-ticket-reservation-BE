package com.example.raiwayticketreservation.controllers;

import com.example.raiwayticketreservation.entities.HanhTrinh;
import com.example.raiwayticketreservation.service.HanhTrinhService;
import com.example.raiwayticketreservation.service.ToaService;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import com.example.raiwayticketreservation.dtos.requests.TimChuyenTauRequest;
import com.example.raiwayticketreservation.dtos.responses.TimChuyenTauResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/v1")
public class HanhTrinhTauController {
    @Autowired
    private HanhTrinhService hanhTrinhService;

    @Autowired
    private ToaService toaService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Tìm chuyến tàu",
            description = "Tìm chuyến tàu một chiều và khứ hồi",
            tags = "API Tìm vé")
    @PostMapping("/khachhang/hanhtrinhtau")
    public ResponseEntity getHanhTrinhTauByGaDiGaDenNgayDiNgayDen(@RequestBody TimChuyenTauRequest timChuyenTauRequest) throws ParseException {
        if (timChuyenTauRequest.getLoaiHanhTrinh().equals("MOT_CHIEU")) {
            List<HanhTrinh> hanhTrinhSet = hanhTrinhService.getHanhTrinh(timChuyenTauRequest.getGaDi(), timChuyenTauRequest.getGaDen(), timChuyenTauRequest.getNgayDi());
            if(hanhTrinhSet != null) {
                Set toaTheoTaus = new HashSet<>();
                toaTheoTaus.addAll(toaService.getToasByTauID(hanhTrinhSet.get(0).getTau().getId()));
                hanhTrinhSet.get(0).getTau().setToas(toaTheoTaus);
//                hanhTrinhSet.forEach(hanhTrinh -> {
//                    Set toaTheoTaus = new HashSet<>();
//                    toaTheoTaus.addAll(toaService.getToasByTauID(hanhTrinh.getTau().getId()));
//                    hanhTrinh.getTau().setToas(toaTheoTaus);
//                });
                return new ResponseEntity(hanhTrinhSet, HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .tenLoi("Không tìm thấy")
                        .moTaLoi("Không tìm thấy chuyến tàu")
                        .build();
                return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
            }
        } else if (timChuyenTauRequest.getLoaiHanhTrinh().equals("KHU_HOI")) {
            List<HanhTrinh> hanhTrinhDiSet = hanhTrinhService.getHanhTrinh(timChuyenTauRequest.getGaDi(), timChuyenTauRequest.getGaDen(), timChuyenTauRequest.getNgayDi());
            List<HanhTrinh> hanhTrinhVeSet = hanhTrinhService.getHanhTrinh(timChuyenTauRequest.getGaDen(), timChuyenTauRequest.getGaDi(), timChuyenTauRequest.getNgayVe());

            if(hanhTrinhDiSet != null && hanhTrinhVeSet != null ) {
                Set toaTheoTaus = new HashSet<>();
                toaTheoTaus.addAll(toaService.getToasByTauID(hanhTrinhDiSet.get(0).getTau().getId()));
                hanhTrinhDiSet.get(0).getTau().setToas(toaTheoTaus);
//                hanhTrinhDiSet.forEach(hanhTrinh -> {
//                    Set toaTheoTaus = new HashSet<>();
//                    toaTheoTaus.addAll(toaService.getToasByTauID(hanhTrinh.getTau().getId()));
//                    hanhTrinh.getTau().setToas(toaTheoTaus);
//                });
                Set toaTheoTauVes = new HashSet<>();
                toaTheoTauVes.addAll(toaService.getToasByTauID(hanhTrinhVeSet.get(0).getTau().getId()));
                hanhTrinhVeSet.get(0).getTau().setToas(toaTheoTaus);
//                hanhTrinhVeSet.forEach(hanhTrinh -> {
//                    Set toaTheoTaus = new HashSet<>();
//                    toaTheoTaus.addAll(toaService.getToasByTauID(hanhTrinh.getTau().getId()));
//                    hanhTrinh.getTau().setToas(toaTheoTaus);
//                });
                TimChuyenTauResponse timChuyenTauResponse = TimChuyenTauResponse.builder()
                        .hanhTrinhDi(hanhTrinhDiSet)
                        .hanhTrinhVe(hanhTrinhVeSet)
                        .build();
                return new ResponseEntity(timChuyenTauResponse, HttpStatus.OK);
            } else {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .tenLoi("Không tìm thấy")
                        .moTaLoi("Không tìm thấy chuyến tàu")
                        .build();
                return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
            }
        }
        ErrorResponse errorResponse = ErrorResponse.builder()
                .tenLoi("Không tìm thấy")
                .moTaLoi("Không tìm thấy chuyến tàu")
                .build();
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Thêm hành trình tàu",
            description = "Thêm hành trình tàu trên trang quản trị",
            tags = "API Quản lí hành trình - ADMIN")
    @PostMapping("/admin/themhanhtrinh")
    public ResponseEntity themHanhTrinhs(@RequestBody List<HanhTrinh> hanhTrinhs) {
        if (hanhTrinhs.size() != 0) {
            if(!hanhTrinhService.kiemTraHanhTrinhTonTai(hanhTrinhs)) {
                List<HanhTrinh> hanhTrinhList = hanhTrinhService.themHanhTrinhs(hanhTrinhs);
                return new ResponseEntity<>(hanhTrinhList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ErrorResponse.builder()
                        .tenLoi("Lỗi thêm hành trình")
                        .moTaLoi("Hành trình đã có trong hệ thống").build(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi thêm hành trình")
                .moTaLoi("Không có hành trình để thêm").build(), HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Xóa hành trình tàu",
            description = "Xóa hành trình tàu trên trang quản trị",
            tags = "API Quản lí hành trình - ADMIN")
    @PostMapping("/admin/xoahanhtrinh")
    public ResponseEntity xoaHanhTrinhs(@RequestBody List<HanhTrinh> hanhTrinhs) {
        if (hanhTrinhs.size() != 0) {
            if(hanhTrinhService.kiemTraHanhTrinhTonTai(hanhTrinhs)) {
                if(hanhTrinhService.xoaHanhTrinh(hanhTrinhs)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(ErrorResponse.builder()
                            .tenLoi("Lỗi xóa hành trình")
                            .moTaLoi("Xóa hành trình gặp lỗi").build(), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(ErrorResponse.builder()
                        .tenLoi("Lỗi xóa hành trình")
                        .moTaLoi("Hành trình không tồn tại trong hệ thống").build(), HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi xóa hành trình")
                .moTaLoi("Không có hành trình để xóa").build(), HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Cập nhật hành trình tàu",
            description = "Cập nhật hành trình tàu trên trang quản trị",
            tags = "API Quản lí hành trình - ADMIN")
    @PostMapping("/admin/capnhathanhtrinh")
    public ResponseEntity capNhatHanhTrinh(@RequestBody HanhTrinh hanhTrinh) {
        if (hanhTrinh != null) {
            List<HanhTrinh> hanhTrinhs = new ArrayList<>();
            hanhTrinhs.add(hanhTrinh);
            if(hanhTrinhService.kiemTraHanhTrinhTonTai(hanhTrinhs)) {
                if (hanhTrinhService.capNhatHanhTrinh(hanhTrinh)) {
                    return new ResponseEntity(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(ErrorResponse.builder()
                            .tenLoi("Lỗi cập nhật hành trình")
                            .moTaLoi("Xử lí cập nhật hành trình xảy ra lỗi").build(), HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>(ErrorResponse.builder()
                        .tenLoi("Lỗi cập nhật hành trình")
                        .moTaLoi("Hành trình không tồn tại trong hệ thống").build(), HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi cập nhật hành trình")
                .moTaLoi("Không có hành trình để cập nhật").build(), HttpStatus.BAD_REQUEST);
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Lấy danh sách hành trình tàu",
            description = "Lấy danh sách hành trình tàu trên trang quản trị",
            tags = "API Quản lí hành trình - ADMIN")
    @GetMapping("/admin/hanhtrinhs")
    public ResponseEntity getDanhSachHanhTrinhNgayHienTai() {
        List<HanhTrinh> hanhTrinhs = hanhTrinhService.getDanhSachHanhTrinh();
        if(hanhTrinhs.size() > 0) {
            return new ResponseEntity<>(hanhTrinhs, HttpStatus.OK);
        } else  return new ResponseEntity<>(ErrorResponse.builder()
                .tenLoi("Lỗi lấy danh sách hành trình")
                .moTaLoi("Không có hành trình trong hệ thống").build(), HttpStatus.BAD_REQUEST);
    }
}
