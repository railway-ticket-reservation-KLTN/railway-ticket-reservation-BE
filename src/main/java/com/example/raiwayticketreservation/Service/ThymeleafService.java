package com.example.raiwayticketreservation.Service;

import java.util.List;
import java.util.Map;

public interface ThymeleafService {
    String createContent(String template, Map<String, Object> variables);
}
