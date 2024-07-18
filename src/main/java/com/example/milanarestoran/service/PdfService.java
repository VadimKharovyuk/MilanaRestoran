//package com.example.milanarestoran.service.PDF;
//
//import com.example.milanarestoran.model.Order;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//
//@Service
//public class PdfService {
//
//
//    public ByteArrayInputStream generateOrderPdf(Order order) throws DocumentException, IOException {
//        Document document = new Document();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        PdfWriter.getInstance(document, out);
//        document.open();
//
//        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//
//        Paragraph title = new Paragraph("Order Confirmation", font);
//        title.setAlignment(Element.ALIGN_CENTER);
//        document.add(title);
//
//        document.add(new Paragraph("Order ID: " + order.getId()));
//        document.add(new Paragraph("Delivery Address: " + order.getDeliveryAddress()));
//        document.add(new Paragraph("Order Date: " + order.getOrderDate()));
//        document.add(new Paragraph("Email: " + order.getEmail()));
//
//        PdfPTable table = new PdfPTable(3);
//        table.setWidthPercentage(100);
//        table.addCell("Dish Name");
//        table.addCell("Quantity");
//        table.addCell("Price");
//
//        order.getOrderItems().forEach(item -> {
//            table.addCell(item.getDish().getName());
//            table.addCell("1");
//            table.addCell(item.getDish().getPrice().toString());
//        });
//
//        document.add(table);
//
//        document.add(new Paragraph("Total Amount: " + order.getTotalAmount()));
//
//        document.close();
//        return new ByteArrayInputStream(out.toByteArray());
//    }
//}
package com.example.milanarestoran.service;
import com.example.milanarestoran.model.Order;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {

    public ByteArrayInputStream generateOrderPdf(Order order) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

        Paragraph title = new Paragraph("Order Confirmation", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        document.add(new Paragraph("Order ID: " + order.getId()));
        document.add(new Paragraph("Delivery Address: " + order.getDeliveryAddress()));
        document.add(new Paragraph("Order Date: " + order.getOrderDate()));
        document.add(new Paragraph("Email: " + order.getEmail()));
        document.add(new Paragraph("Phone Number: " + order.getPhoneNumber()));

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.addCell("Dish Name");
        table.addCell("Quantity");
        table.addCell("Price");

        order.getOrderItems().forEach(item -> {
            table.addCell(item.getDish().getName());
            table.addCell("1");
            table.addCell(item.getDish().getPrice().toString());
        });

        document.add(table);

        document.add(new Paragraph("Total Amount: " + order.getTotalAmount()));

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }
}
