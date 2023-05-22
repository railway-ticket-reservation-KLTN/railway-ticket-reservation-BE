package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.dtos.requests.VeTauRequest;
import com.example.raiwayticketreservation.entities.VeTau;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface PDFService {
    public ByteArrayInputStream taoPDFFile(List<VeTauRequest> veTauList);
    public ByteArrayInputStream taoPDFFileNoParam() throws IOException;
}
