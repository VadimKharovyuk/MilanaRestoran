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
        // Метод для генерации PDF-файла заказа. Принимает объект Order и возвращает ByteArrayInputStream.
        // Может выбрасывать исключения DocumentException и IOException.

        Document document = new Document();
        // Создание нового объекта Document для PDF-документа.
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // Создание нового объекта ByteArrayOutputStream для временного хранения содержимого PDF.


        PdfWriter.getInstance(document, out);
        // Создание экземпляра PdfWriter, связывающего документ и выходной поток.
        document.open();

        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        // Создание шрифта Helvetica размером 12 и жирного начертания.

        Paragraph title = new Paragraph("Order Confirmation", font);
        // Создание параграфа с текстом "Order Confirmation" и заданным шрифтом.

        title.setAlignment(Element.ALIGN_CENTER);
        // Установка выравнивания параграфа по центру.
        document.add(title);
        // Добавление заголовка в документ.

        document.add(new Paragraph("Order ID: " + order.getId()));
        document.add(new Paragraph("Delivery Address: " + order.getDeliveryAddress()));
        document.add(new Paragraph("Order Date: " + order.getOrderDate()));
        document.add(new Paragraph("Email: " + order.getEmail()));


        Paragraph phoneNumber = new Paragraph("Phone Number: " + order.getPhoneNumber());
        phoneNumber.setSpacingAfter(10);// Отступ после Phone Number
        // Установка отступа после параграфа с номером телефона.
        document.add(phoneNumber);


        PdfPTable table = new PdfPTable(3);
        // Создание таблицы с 3 колонками.
        table.setWidthPercentage(100);
        // Установка ширины таблицы на 100% от доступного пространства.
        table.addCell("Dish Name");
        // Добавление ячейки с заголовком "Dish Name".
        table.addCell("Quantity");
        table.addCell("Price");


        order.getOrderItems().forEach(item -> {
            table.addCell(item.getDish().getName());
            table.addCell("1");
            table.addCell(item.getDish().getPrice().toString());
            // Добавление ячейки с названием блюда.
        });

        document.add(table);

        document.add(new Paragraph("Total Amount UAH: " + order.getTotalAmount()));

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
        // Возвращение байтового потока, содержащего PDF-документ.
    }
}
