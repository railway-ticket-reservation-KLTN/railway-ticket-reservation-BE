package com.example.raiwayticketreservation.security;

import com.example.raiwayticketreservation.repository.TaiKhoanRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class RailwayUserDetailsService implements UserDetailsService {

    @Autowired
    private TaiKhoanRepo taiKhoanRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return taiKhoanRepo.timTaiKhoanTheoTenTaiKhoan(username)
                .map(RailwayUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));
    }
}
