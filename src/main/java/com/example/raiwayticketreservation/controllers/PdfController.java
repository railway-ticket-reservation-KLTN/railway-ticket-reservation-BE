package com.example.raiwayticketreservation.controllers;

import com.example.raiwayticketreservation.dtos.requests.VeTauRequest;
import com.example.raiwayticketreservation.dtos.responses.ErrorResponse;
import com.example.raiwayticketreservation.service.PDFService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/khachhang")
public class PdfController {

    @Autowired
    private PDFService pdfService;

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "In vé tàu",
            description = "In vé tàu cho khách hàng",
            tags = "API In vé tàu")
    @PostMapping("/taopdf")
    public ResponseEntity taoFilePDF(@RequestBody List<VeTauRequest> veTaus) throws IOException {
        if(veTaus.size() > 0) {
            ByteArrayInputStream pdfFile = pdfService.taoPDFFile(veTaus);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline;file=vetau.pdf");
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdfFile));
        } return new ResponseEntity(ErrorResponse.builder()
                .tenLoi("Lỗi in vé")
                .moTaLoi("Danh sách vé rỗng").build(), HttpStatus.BAD_REQUEST);
    }

//    @GetMapping("/taopdf")
//    public ResponseEntity<InputStreamResource> taoFilePDF() throws IOException {
//        ByteArrayInputStream pdfFile = pdfService.taoPDFFileNoParam();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", "inline;file=vetau.pdf");
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdfFile));
//    }
}
