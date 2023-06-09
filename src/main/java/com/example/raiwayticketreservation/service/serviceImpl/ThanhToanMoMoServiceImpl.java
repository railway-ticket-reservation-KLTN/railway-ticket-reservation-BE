package com.example.raiwayticketreservation.service.serviceImpl;

import com.example.raiwayticketreservation.service.ThanhToanMoMoService;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ThanhToanMoMoServiceImpl implements ThanhToanMoMoService {
    final String THANH_TOAN_ENDPOINT_URl = "https://railwayvn.up.railway.app/v1/khachhang/thanhtoan/thanhtoanmomo/{amount}/{orderId}";
    RestTemplate restTemplate = new RestTemplate();

    @Override
    @JsonCreator
    public Object getDataThanhToanMoMo(Map<String, Long> param) {
        Object result = restTemplate.getForObject(THANH_TOAN_ENDPOINT_URl, Object.class, param);
      return result;
    }
}
