package Reporters;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.IOException;

public class PDFReportGenerator {
    PDDocument document;
    PDPageContentStream contentStream;
    float coverPageY;
    public PDFReportGenerator()
    {
        document=new PDDocument();
    }
    float addRow(String label,String value, float x, float y, float labelWidth, float valueWidth, float rowHeight) throws IOException {
        label=label.replaceAll("\n"," ");
        value=value.replaceAll("\n"," ");
        String[] valueLines=toLines(value);
        float totalRowHeight=rowHeight;
        if(valueLines.length>1)
        {
            totalRowHeight=rowHeight*((float) valueLines.length);
        }
        contentStream.addRect(x+10,y-totalRowHeight,labelWidth+valueWidth,totalRowHeight);
        contentStream.stroke();
        contentStream.moveTo(x+labelWidth,y);
        contentStream.lineTo(x+labelWidth,y-totalRowHeight);
        contentStream.stroke();
        contentStream.setFont(PDType1Font.TIMES_ROMAN,12);
        contentStream.beginText();
        contentStream.newLineAtOffset(x+20,y-20);
        contentStream.showText(label);
        contentStream.endText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN,12);
        float z=y-20;
        for(String line:valueLines)
        {
            contentStream.beginText();
            contentStream.newLineAtOffset(x+10+labelWidth,z);
            contentStream.showText(line);
            contentStream.endText();
            z=z-20;
        }
        return y=y-totalRowHeight;
    }
    public void addStepToPDF(String description, String expected, String actual, String status, String screenshotPath) throws IOException {
       if(contentStream!=null)
       {
           contentStream.close();
       }
        PDPage page=new PDPage(PDRectangle.A4);
        document.addPage(page);
        contentStream=new PDPageContentStream(document,page);
        float x=0;
        float y=page.getMediaBox().getHeight()-20;
        float rowHeight=25;
        float labelWidth=150;
        float valueWidth=420;
        y=addRow("Step Description",description,x,y,labelWidth,valueWidth,rowHeight);
        y=addRow("Expected Result",expected,x,y,labelWidth,valueWidth,rowHeight);
        y=addRow("Actual Result",actual,x,y,labelWidth,valueWidth,rowHeight);
        PDImageXObject image=PDImageXObject.createFromFile(screenshotPath,document);
        contentStream.drawImage(image,x+20,y-400,labelWidth+valueWidth-20,350);
        contentStream.addRect(x+10,y-450,valueWidth+labelWidth,450);
        contentStream.stroke();
        contentStream.close();
    }
    public void addCoverPage(String tcName,String objective) throws IOException {
        if(contentStream!=null)
        {
            contentStream.close();
        }
        PDPage page=new PDPage(PDRectangle.A4);
        document.addPage(page);
        contentStream=new PDPageContentStream(document,page);
        float x=0;
        float pageWidth=page.getMediaBox().getWidth();
        float y=page.getMediaBox().getHeight();
        float labelWidth=150;
        float valueWidth=420;
        float rowHeight=25;
        contentStream.setFont(PDType1Font.TIMES_ROMAN,20);
        contentStream.beginText();
        contentStream.newLineAtOffset((pageWidth/2)-100,y-50);
        contentStream.showText("Test Execution Report");
        contentStream.endText();
        y=y-70;
        y=addRow("Test Case Name",tcName,x,y,labelWidth,valueWidth,rowHeight);
        y=addRow("Objective",objective,x,y,labelWidth,valueWidth,rowHeight);
        y=addRow("TimeStamp",new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date()),x,y,labelWidth,valueWidth,rowHeight);
        y=addRow("Tester",System.getProperty("user.name"),x,y,labelWidth,valueWidth,rowHeight);
        coverPageY=addRow("Status","",x,y,labelWidth,valueWidth,rowHeight);
        contentStream.close();
    }
    public void addStatusToCoverPage(String status) throws IOException {
        PDPage page=document.getPage(0);
        if(contentStream!=null)
        {
            contentStream.close();
        }
        contentStream=new PDPageContentStream(document,page, PDPageContentStream.AppendMode.APPEND,true);
        if(status.equalsIgnoreCase("Pass"))
        {
            contentStream.setNonStrokingColor(Color.GREEN);
        }
        else {
            contentStream.setNonStrokingColor(Color.RED);
        }
        contentStream.setFont(PDType1Font.TIMES_BOLD,12);
        contentStream.beginText();
        contentStream.newLineAtOffset(160,coverPageY+5);
        contentStream.showText(status);
        contentStream.endText();
        contentStream.close();
    }
    public void save(String pdfPath) throws IOException {
        if(contentStream!=null)
        {
            contentStream.close();
        }
        document.save(pdfPath);
        document.close();
    }
    private String[] toLines(String line)
    {
        return line.split("(?<=\\G.{80})");
    }
}
