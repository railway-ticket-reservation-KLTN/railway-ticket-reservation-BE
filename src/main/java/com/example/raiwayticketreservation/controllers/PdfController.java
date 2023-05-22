package com.example.raiwayticketreservation.controllers;

import com.example.raiwayticketreservation.service.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/v1/khachhang")
public class PdfController {

    @Autowired
    private PDFService pdfService;

//    @GetMapping("/taopdf")
//    public ResponseEntity<InputStreamResource> taoFilePDF(List<VeTauRequest> veTaus) {
//        ByteArrayInputStream pdfFile = pdfService.taoPDFFileNoParam();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline;file=vetau.pdf");
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdfFile));
//    }

    @GetMapping("/taopdf")
    public ResponseEntity<InputStreamResource> taoFilePDF() throws IOException {
        ByteArrayInputStream pdfFile = pdfService.taoPDFFileNoParam();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline;file=vetau.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdfFile));
    }
}
