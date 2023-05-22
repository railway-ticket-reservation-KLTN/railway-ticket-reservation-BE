package com.example.raiwayticketreservation.service.serviceImpl;

import com.example.raiwayticketreservation.dtos.requests.VeTauRequest;
import com.example.raiwayticketreservation.service.PDFService;
import com.example.raiwayticketreservation.utils.GenerateQRCode;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.librepdf.openpdf.fonts.Liberation;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PDFServiceImpl implements PDFService {

    @Override
    public ByteArrayInputStream taoPDFFile(List<VeTauRequest> veTauList) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        for (VeTauRequest veTau : veTauList) {
            String TONG_CONG_TY_DUONG_SAT_VIET_NAM = "TỔNG CÔNG TY ĐƯỜNG SẮT VIỆT NAM";
            Font tongctyFont = FontFactory.getFont(String.valueOf(Liberation.SERIF_BOLD), 12);
            Paragraph tongctyPara = new Paragraph(TONG_CONG_TY_DUONG_SAT_VIET_NAM, tongctyFont);
            tongctyPara.setAlignment(Element.ALIGN_CENTER);
            document.add(tongctyPara);

            String THE_LEN_TAU_HOA = "THẺ LÊN TÀU HỎA";
            Font theLenTauFont = FontFactory.getFont(String.valueOf(Liberation.SERIF_BOLD), 15);
            Paragraph theLenTauPara = new Paragraph(THE_LEN_TAU_HOA, theLenTauFont);
            theLenTauPara.setAlignment(Element.ALIGN_CENTER);
            document.add(theLenTauPara);

            String MA_VE = "Mã vé: " + veTau.getMaVe();
            Font maVeFont = FontFactory.getFont(String.valueOf(Liberation.SERIF), 12);
            Paragraph maVePara = new Paragraph(MA_VE, maVeFont);
            maVePara.setAlignment(Element.ALIGN_CENTER);
            document.add(maVePara);

            //Tạo bảng Ga
            Font gaTableHeaderFont = FontFactory.getFont(String.valueOf(Liberation.SERIF), 10);
            PdfPTable gaTable = new PdfPTable(3);
            gaTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            gaTable.setWidths(new float[]{6.0f, 8.0f, 6.0f});
            PdfPCell gaHcell = new PdfPCell();
            gaHcell.setBorder(Rectangle.NO_BORDER);

            //Ga table header
            gaHcell.setPhrase(new Phrase("Ga đi", gaTableHeaderFont));
            gaTable.addCell(gaHcell);

            gaHcell.setPhrase(new Phrase("", gaTableHeaderFont));
            gaTable.addCell(gaHcell);

            gaHcell.setPhrase(new Phrase("Ga đến", gaTableHeaderFont));
            gaTable.addCell(gaHcell);

            Font gaTableRowFont = FontFactory.getFont(String.valueOf(Liberation.SERIF_BOLD), 12);
            //Thêm Ga table row
            PdfPCell gaRCell = new PdfPCell();
            gaRCell.setBorder(Rectangle.NO_BORDER);
            gaRCell.setPhrase(new Phrase(veTau.getGaDi(), gaTableRowFont));
            gaTable.addCell(gaRCell);

            gaRCell.setPhrase(new Phrase(""));
            gaTable.addCell(gaRCell);

            gaRCell.setPhrase(new Phrase(veTau.getGaDen(), gaTableRowFont));
            gaTable.addCell(gaRCell);

            document.add(gaTable);

            //Tạo bảng thông tin vé
            Font thongTinVeTableInDamFont = FontFactory.getFont(String.valueOf(Liberation.SERIF_BOLD), 13);
            Font thongTinVeTableChuThuongFont = FontFactory.getFont(String.valueOf(Liberation.SERIF), 13);
            PdfPTable thongTinVeTable = new PdfPTable(2);
            thongTinVeTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            thongTinVeTable.setWidths(new float[]{6.0f, 6.0f});
            PdfPCell thongTinVecell = new PdfPCell();
            thongTinVecell.setBorder(Rectangle.NO_BORDER);

            //Tên tàu
            thongTinVecell.setPhrase(new Phrase("Tàu/Train: ", thongTinVeTableInDamFont));
            thongTinVeTable.addCell(thongTinVecell);
            thongTinVecell.setPhrase(new Phrase(veTau.getTenTau(), thongTinVeTableInDamFont));
            thongTinVeTable.addCell(thongTinVecell);

            //Ngày đi
            thongTinVecell.setPhrase(new Phrase("Ngày đi/Date: ", thongTinVeTableInDamFont));
            thongTinVeTable.addCell(thongTinVecell);
            thongTinVecell.setPhrase(new Phrase(veTau.getNgayDi(), thongTinVeTableInDamFont));
            thongTinVeTable.addCell(thongTinVecell);

            //Giờ đi
            thongTinVecell.setPhrase(new Phrase("Giờ đi/Time: ", thongTinVeTableInDamFont));
            thongTinVeTable.addCell(thongTinVecell);
            thongTinVecell.setPhrase(new Phrase(veTau.getGioDi().toString(), thongTinVeTableInDamFont));
            thongTinVeTable.addCell(thongTinVecell);

            //Toa
            thongTinVecell.setPhrase(new Phrase("Toa/Coach: " + veTau.getSoToa(), thongTinVeTableInDamFont));
            thongTinVeTable.addCell(thongTinVecell);

            //Số ghế
            thongTinVecell.setPhrase(new Phrase("Chỗ/Seat:  " + veTau.getSoGhe(), thongTinVeTableInDamFont));
            thongTinVeTable.addCell(thongTinVecell);

            //Loại vé
            thongTinVecell.setPhrase(new Phrase("Loại vé/Ticket: ", thongTinVeTableInDamFont));
            thongTinVeTable.addCell(thongTinVecell);
            thongTinVecell.setPhrase(new Phrase(veTau.getLoaiVe(), thongTinVeTableChuThuongFont));
            thongTinVeTable.addCell(thongTinVecell);

            //Họ tên
            thongTinVecell.setPhrase(new Phrase("Họ tên/Name: ", thongTinVeTableInDamFont));
            thongTinVeTable.addCell(thongTinVecell);
            thongTinVecell.setPhrase(new Phrase(veTau.getTenHanhKhach(), thongTinVeTableChuThuongFont));
            thongTinVeTable.addCell(thongTinVecell);

            //Số giấy tờ
            thongTinVecell.setPhrase(new Phrase("Số giấy tờ/Passport: ", thongTinVeTableInDamFont));
            thongTinVeTable.addCell(thongTinVecell);
            thongTinVecell.setPhrase(new Phrase(veTau.getSoGiayTo(), thongTinVeTableChuThuongFont));
            thongTinVeTable.addCell(thongTinVecell);

            //Đơn giá
            thongTinVecell.setPhrase(new Phrase("Giá vé/Price: ", thongTinVeTableInDamFont));
            thongTinVeTable.addCell(thongTinVecell);
            thongTinVecell.setPhrase(new Phrase(String.valueOf(veTau.getDonGia()), thongTinVeTableChuThuongFont));
            thongTinVeTable.addCell(thongTinVecell);

            document.add(thongTinVeTable);

            Font ghichuFont = FontFactory.getFont(String.valueOf(Liberation.SERIF_ITALIC), 13);
            String GHI_CHU_TIENG_VIET = "Ghi chú: Thẻ lên tàu hỏa không phải là hóa đơn GTGT và không có giá trị thanh toán.";
            Paragraph ghiChuTiengVietPara = new Paragraph(GHI_CHU_TIENG_VIET, ghichuFont);
            document.add(ghiChuTiengVietPara);

            String GHI_CHU_TIENG_ANH = "This boarding pass is not an official invoice.";
            Paragraph ghiChuTiengAnhPara = new Paragraph(GHI_CHU_TIENG_ANH, ghichuFont);
            document.add(ghiChuTiengAnhPara);

            String LUU_Y_TIENG_VIET = "Để đảm bảo quyền lợi, quý khách vui lòng mang theo thẻ lên tàu cũng với giấy tờ tùy thân trong suốt hành trình.";
            Paragraph luuYTiengVietPara = new Paragraph(LUU_Y_TIENG_VIET, ghichuFont);
            document.add(luuYTiengVietPara);

            String DUONG_PHAN_CACH= "----------------------------------------------------------------------------";
            Paragraph phanCachPara = new Paragraph(DUONG_PHAN_CACH, thongTinVeTableChuThuongFont);
            document.add(phanCachPara);
        }

        document.close();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    @Override
    public ByteArrayInputStream taoPDFFileNoParam() throws IOException {
        FontFactory.register("D:\\KLTN-2022-2023\\raiway-ticket-reservation\\raiway-ticket-reservation\\src\\main\\resources\\static\\font-times-new-roman.ttf");
        FontFactory.register("D:\\KLTN-2022-2023\\raiway-ticket-reservation\\raiway-ticket-reservation\\src\\main\\resources\\static\\times new roman bold.ttf");
        FontFactory.register("D:\\KLTN-2022-2023\\raiway-ticket-reservation\\raiway-ticket-reservation\\src\\main\\resources\\static\\times new roman italic.ttf");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
        document.setPageSize(new Rectangle(265.0f, 850.0f));
        document.open();

        String TONG_CONG_TY_DUONG_SAT_VIET_NAM = "TỔNG CÔNG TY ĐƯỜNG SẮT VIỆT NAM";
        Font tongctyFont = FontFactory.getFont("Times New Roman", 9);
        Paragraph tongctyPara = new Paragraph(TONG_CONG_TY_DUONG_SAT_VIET_NAM, tongctyFont);
        tongctyPara.setAlignment(Element.ALIGN_CENTER);
        document.add(tongctyPara);


        String thongTinQRCode = new StringBuilder().append("18097689").append(", ")
                .append("SE8").append(", ").toString();

        byte[] qrCodeImage =  GenerateQRCode.generateQRCode(thongTinQRCode, 100, 100);

        Image image = Image.getInstance(qrCodeImage);

        //Tạo bảng QR
        PdfPTable qrTable = new PdfPTable(1);
        qrTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        qrTable.setWidths(new float[]{15.0f});



        //QR Cell
        PdfPCell imageCell = new PdfPCell(image);
        imageCell.setBorder(Rectangle.NO_BORDER);
        imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);

        qrTable.addCell(imageCell);
        document.add(qrTable);

        String THE_LEN_TAU_HOA = "THẺ LÊN TÀU HỎA";
        Font theLenTauFont = FontFactory.getFont("Times New Roman", 12);
        Paragraph theLenTauPara = new Paragraph(THE_LEN_TAU_HOA, theLenTauFont);
        theLenTauPara.setAlignment(Element.ALIGN_CENTER);
        document.add(theLenTauPara);

        String BOARDING_PASS = "BOARDING PASS";
        Font boardingFont = FontFactory.getFont("Times New Roman", 8);
        Paragraph boardingPara = new Paragraph(BOARDING_PASS, boardingFont);
        boardingPara.setAlignment(Element.ALIGN_CENTER);
        document.add(boardingPara);

        String MA_VE = "Mã vé: " + "18097689";
        Font maVeFont = FontFactory.getFont("Times New Roman", 7);
        Paragraph maVePara = new Paragraph(MA_VE, maVeFont);
        maVePara.setAlignment(Element.ALIGN_CENTER);
        document.add(maVePara);

        //Tạo bảng Ga
        PdfPTable gaTable = new PdfPTable(3);
        gaTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        gaTable.setWidths(new float[]{15.0f, 8.0f, 15.0f});

        Font gaTableHeaderFont = FontFactory.getFont("Times New Roman", 9);
        PdfPCell gaHcell = new PdfPCell();
        gaHcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        gaHcell.setBorder(Rectangle.NO_BORDER);

        //Ga table header
        gaHcell.setPhrase(new Phrase("Ga đi", gaTableHeaderFont));
        gaTable.addCell(gaHcell);

        gaHcell.setPhrase(new Phrase("", gaTableHeaderFont));
        gaTable.addCell(gaHcell);

        gaHcell.setPhrase(new Phrase("Ga đến", gaTableHeaderFont));
        gaTable.addCell(gaHcell);

        Font gaTableRowFont = FontFactory.getFont("Times New Roman", 10);
        //Thêm Ga table row
        PdfPCell gaRCell = new PdfPCell();
        gaRCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        gaRCell.setBorder(Rectangle.NO_BORDER);
        gaRCell.setPhrase(new Phrase("Sài Gòn", gaTableRowFont));
        gaTable.addCell(gaRCell);

        gaRCell.setPhrase(new Phrase(""));
        gaTable.addCell(gaRCell);

        gaRCell.setPhrase(new Phrase("Nha Trang", gaTableRowFont));
        gaTable.addCell(gaRCell);

        document.add(gaTable);

        //Tạo bảng thông tin vé
        Font thongTinVeTableInDamFont = FontFactory.getFont("Times New Roman", 8);
        Font thongTinVeTableChuThuongFont = FontFactory.getFont("Times New Roman", 8);
        PdfPTable thongTinVeTable = new PdfPTable(2);
        thongTinVeTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        thongTinVeTable.setWidths(new float[]{15.0f, 15.0f});
        PdfPCell thongTinVecell = new PdfPCell();
        thongTinVecell.setBorder(Rectangle.NO_BORDER);

        //Tên tàu
        thongTinVecell.setPhrase(new Phrase("Tàu/Train: ", thongTinVeTableInDamFont));
        thongTinVeTable.addCell(thongTinVecell);
        thongTinVecell.setPhrase(new Phrase("SE8", thongTinVeTableInDamFont));
        thongTinVeTable.addCell(thongTinVecell);

        //Ngày đi
        thongTinVecell.setPhrase(new Phrase("Ngày đi/Date: ", thongTinVeTableInDamFont));
        thongTinVeTable.addCell(thongTinVecell);
        thongTinVecell.setPhrase(new Phrase("18/5/2023", thongTinVeTableInDamFont));
        thongTinVeTable.addCell(thongTinVecell);

        //Giờ đi
        thongTinVecell.setPhrase(new Phrase("Giờ đi/Time: ", thongTinVeTableInDamFont));
        thongTinVeTable.addCell(thongTinVecell);
        thongTinVecell.setPhrase(new Phrase("18:00:00", thongTinVeTableInDamFont));
        thongTinVeTable.addCell(thongTinVecell);

        //Toa
        thongTinVecell.setPhrase(new Phrase("Toa/Coach: " + "2", thongTinVeTableInDamFont));
        thongTinVeTable.addCell(thongTinVecell);

        //Số ghế
        thongTinVecell.setPhrase(new Phrase("Chỗ/Seat:  " + "5", thongTinVeTableInDamFont));
        thongTinVeTable.addCell(thongTinVecell);

        //Loại vé
        thongTinVecell.setPhrase(new Phrase("Loại vé/Ticket: ", thongTinVeTableInDamFont));
        thongTinVeTable.addCell(thongTinVecell);
        thongTinVecell.setPhrase(new Phrase("Một chiều", thongTinVeTableChuThuongFont));
        thongTinVeTable.addCell(thongTinVecell);

        //Họ tên
        thongTinVecell.setPhrase(new Phrase("Họ tên/Name: ", thongTinVeTableInDamFont));
        thongTinVeTable.addCell(thongTinVecell);
        thongTinVecell.setPhrase(new Phrase("Bảo Lộc", thongTinVeTableChuThuongFont));
        thongTinVeTable.addCell(thongTinVecell);

        //Số giấy tờ
        thongTinVecell.setPhrase(new Phrase("Số giấy tờ/Passport: ", thongTinVeTableInDamFont));
        thongTinVeTable.addCell(thongTinVecell);
        thongTinVecell.setPhrase(new Phrase("0987890998", thongTinVeTableChuThuongFont));
        thongTinVeTable.addCell(thongTinVecell);

        //Đơn giá
        thongTinVecell.setPhrase(new Phrase("Giá vé/Price: ", thongTinVeTableInDamFont));
        thongTinVeTable.addCell(thongTinVecell);
        thongTinVecell.setPhrase(new Phrase(String.valueOf(800000), thongTinVeTableChuThuongFont));
        thongTinVeTable.addCell(thongTinVecell);

        document.add(thongTinVeTable);

        Font ghichuFont = FontFactory.getFont("Times New Roman", 8);
        String GHI_CHU_TIENG_VIET = "Ghi chú: Thẻ lên tàu hỏa không phải là hóa đơn GTGT và không có giá trị thanh toán.";
        Paragraph ghiChuTiengVietPara = new Paragraph(GHI_CHU_TIENG_VIET, ghichuFont);
        document.add(ghiChuTiengVietPara);

        String GHI_CHU_TIENG_ANH = "This boarding pass is not an official invoice.";
        Paragraph ghiChuTiengAnhPara = new Paragraph(GHI_CHU_TIENG_ANH, ghichuFont);
        document.add(ghiChuTiengAnhPara);

        String LUU_Y_TIENG_VIET = "Để đảm bảo quyền lợi, quý khách vui lòng mang theo thẻ lên tàu cũng với giấy tờ tùy thân trong suốt hành trình.";
        Paragraph luuYTiengVietPara = new Paragraph(LUU_Y_TIENG_VIET, ghichuFont);
        document.add(luuYTiengVietPara);
        document.close();
        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
