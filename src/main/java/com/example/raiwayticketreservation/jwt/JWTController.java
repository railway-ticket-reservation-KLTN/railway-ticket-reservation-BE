package com.example.raiwayticketreservation.jwt;

import com.example.raiwayticketreservation.entities.TaiKhoan;
import com.example.raiwayticketreservation.service.TaiKhoanService;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class JWTController {
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    private final TaiKhoanService taiKhoanService;

    @PostMapping("/dangnhap")
    public ResponseEntity getTokenForAuthenticatedUser(@RequestBody JWTAuthenticationRequest authRequest){
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            if (authentication.isAuthenticated()){
                TaiKhoan taiKhoan = TaiKhoan.builder()
                        .tenTaiKhoan(authRequest.getUserName())
                        .matKhau(authRequest.getPassword()).build();
                if(taiKhoanService.kiemTraTaiKhoanConHoatDong(taiKhoan)) {
                    return new ResponseEntity(JWTAuthenticationResponse.builder().token(jwtService.generateToken(authRequest.getUserName())).build(), HttpStatus.OK);
                } else return new ResponseEntity(ErrorResponse.builder()
                        .tenLoi("Lỗi đăng nhập").moTaLoi("Tài khoản không còn hoạt động").build(), HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity(ErrorResponse.builder()
                .tenLoi("Lỗi đăng nhập").moTaLoi("Tài khoản không hợp lệ").build(), HttpStatus.UNAUTHORIZED);
    }
}
