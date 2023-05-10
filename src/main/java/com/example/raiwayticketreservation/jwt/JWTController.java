package com.example.raiwayticketreservation.jwt;

import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @PostMapping("/dangnhap")
    public ResponseEntity getTokenForAuthenticatedUser(@RequestBody JWTAuthenticationRequest authRequest){
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            if (authentication.isAuthenticated()){
                return new ResponseEntity(JWTAuthenticationResponse.builder().token(jwtService.generateToken(authRequest.getUserName())).build(), HttpStatus.OK);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity(ErrorResponse.builder()
                .tenLoi("Lỗi đăng nhập").moTaLoi("Tài khoản không hợp lệ").build(), HttpStatus.UNAUTHORIZED);
    }
}
