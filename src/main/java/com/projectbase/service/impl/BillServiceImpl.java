package com.projectbase.service.impl;

import static com.projectbase.factory.Utility.STORED_REPORTS_LOCATION;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.projectbase.config.security.JwtRequestFilter;
import com.projectbase.entity.BillEntity;
import com.projectbase.entity.DetailedBill;
import com.projectbase.exception.ApplicationException;
import com.projectbase.factory.PdfSession;
import com.projectbase.mapper.BillMapper;
import com.projectbase.model.Bill;
import com.projectbase.repository.BillRepository;
import com.projectbase.service.BillService;

@Service
public class BillServiceImpl implements BillService{

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Transactional
    @Override
    public String generate(Bill bill){

        String fileName = "BILL_"+UUID.randomUUID();
        bill.setUuid(fileName);
        bill.setCreatedBy(jwtRequestFilter.getCurrentUser());

        BillEntity entity = billMapper.toEntity(bill);
        billRepository.save(entity);

        generatePdf(bill);

        return fileName;
    }

    @Override
    public List<Bill> findAll(){
        return billRepository.findAll().stream().map(billMapper::fromEntity).collect(Collectors.toList());
    }

    @Override
    public byte[] downloadReport(String uuid){
        String fileName = STORED_REPORTS_LOCATION+"/"+uuid+".pdf";
        try{
            return Files.readAllBytes(Paths.get(fileName));
        }catch(IOException e){
            e.printStackTrace();
            throw new ApplicationException("Cannot download the report: "+e.getMessage());
        }
    }

    private void generatePdf(Bill bill){

        StringBuilder dataBuilder = new StringBuilder()
                .append("Name: ").append(bill.getName()).append("\n")
                .append("Contact Number: ").append(bill.getContactNumber()).append("\n")
                .append("Email: ").append(bill.getEmail()).append("\n")
                .append("Payment Method: ").append(bill.getPaymentMethod());

        Document document = new Document();
        try{

            PdfWriter.getInstance(document, new FileOutputStream(STORED_REPORTS_LOCATION+"/"+bill.getUuid()+".pdf"));

            document.open();

            setPDFBorder(document);

            Paragraph chunk = new Paragraph("Cafe Management System", getFont(PdfSession.HEADER));
            chunk.setAlignment(Element.ALIGN_CENTER);
            document.add(chunk);

            Paragraph paragraph = new Paragraph(dataBuilder+"\n \n", getFont(PdfSession.BODY));
            document.add(paragraph);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            addTableHeader(table);

            List<DetailedBill> detailedBills = getDetailedBill(bill.getDetailedBill());
            assert detailedBills != null;
            for(DetailedBill d:detailedBills){
                addRow(table, d);
            }
            document.add(table);

            Paragraph footer = new Paragraph("Total: "+bill.getTotal()+"\n"+"Thank you for visiting.");
            document.add(footer);
            document.close();
        }catch(DocumentException | FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void addRow(PdfPTable table, DetailedBill detailedBill){
        table.addCell(detailedBill.getName());
        table.addCell(detailedBill.getCategory());
        table.addCell(detailedBill.getQuantity());
        table.addCell(detailedBill.getPrice());
        table.addCell(detailedBill.getTotal());
    }

    private void addTableHeader(PdfPTable table){
        Stream.of("Name","Category", "Quantity", "Price", "Sub Total")
                .forEach(c -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(c));
                    header.setBackgroundColor(BaseColor.YELLOW);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }

    private Font getFont(PdfSession pdfSession){
        Font font = new Font();
        font.setStyle(Font.BOLD);

        if(PdfSession.HEADER == pdfSession){
            font = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
        }

        if(PdfSession.BODY == pdfSession){
            font = FontFactory.getFont(FontFactory.TIMES_ROMAN,11, BaseColor.BLACK);
        }

        return font;
    }

    private void setPDFBorder(Document document) throws DocumentException{

        Rectangle rectangle = new Rectangle(577, 825, 18, 15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBackgroundColor(BaseColor.WHITE);
        rectangle.setBorder(1);

        document.add(rectangle);
    }

    private List<DetailedBill> getDetailedBill(String data){
        ObjectMapper mapper = new ObjectMapper();
        try{
            return mapper.readValue(data, new TypeReference<>(){});
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
