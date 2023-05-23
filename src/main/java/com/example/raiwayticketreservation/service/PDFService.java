package com.example.raiwayticketreservation.service;

import com.example.raiwayticketreservation.dtos.requests.VeTauRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public interface PDFService {
    public ByteArrayInputStream taoPDFFile(List<VeTauRequest> veTauList) throws IOException;
    public ByteArrayInputStream taoPDFFileNoParam() throws IOException;
}
