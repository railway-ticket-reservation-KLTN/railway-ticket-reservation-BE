package com.example.raiwayticketreservation.Service.ServiceImpl;

import com.example.raiwayticketreservation.Service.ThanhToanMoMoService;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ThanhToanMoMoServiceImpl implements ThanhToanMoMoService {
    final String THANH_TOAN_ENDPOINT_URl = "http://localhost:8080/v1/thanhtoan/thanhtoanmomo/{amount}";
    RestTemplate restTemplate = new RestTemplate();

    @Override
    @JsonCreator
    public Object getDataThanhToanMoMo(Map<String, Long> param) {
        Object result = restTemplate.getForObject(THANH_TOAN_ENDPOINT_URl, Object.class, param);
      return result;
    }
}
