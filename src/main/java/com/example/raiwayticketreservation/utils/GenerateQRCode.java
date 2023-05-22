package com.example.raiwayticketreservation.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GenerateQRCode {
//    public static BufferedImage generateQRCodeImage(String details) throws IOException {
//        try {
//            ByteArrayOutputStream outputStream = QRCode
//                    .from(details)
//                    .withSize(500, 500)
//                    .stream();
//            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
//            return ImageIO.read(inputStream);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return new BufferedImage(500, 500, 500);
//        }
//    }

    public static byte[] generateQRCode(String qrContent, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
