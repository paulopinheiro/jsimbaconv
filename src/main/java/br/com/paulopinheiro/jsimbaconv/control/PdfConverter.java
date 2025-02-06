package br.com.paulopinheiro.jsimbaconv.control;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class PdfConverter {
    private final File inputTextFile;
    private final File outputPdfFile;

    public PdfConverter(File inputTextFile) throws IOException {
        this.inputTextFile = inputTextFile;
        this.outputPdfFile = new File(getOutputPdfPathString(inputTextFile));
        convert();
    }

    private void convert() throws IOException {
        PdfWriter writer = new PdfWriter(this.outputPdfFile);
        writer.setCloseStream(true);

        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setCloseWriter(true);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("\n"));
        if (inputTextFile.exists()) {
            FileInputStream fis = new FileInputStream(inputTextFile);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String strLine;

            while ((strLine = br.readLine()) != null) {
                Paragraph para = new Paragraph(strLine + "\n");
                document.add(para);
            }
            br.close();
        } else {
            System.out.println("No such file");
        }
        document.close();
        pdfDoc.close();
    }

    private String getOutputPdfPathString(File inputTextFile) {
        String parentDirectoryPath = inputTextFile.getParentFile().getAbsolutePath();
        String inputFileName = inputTextFile.getName();
        String outputFileName="";
        
        String[] array = inputFileName.split("\\.(?=[^\\.]+$)");
        
        if (array.length<=1) outputFileName = inputFileName + ".pdf";
        else {
            for (int i=0;i<array.length-1;i++) {
                outputFileName += array[i] + ".";
            }
            outputFileName += "pdf";
        }
        return parentDirectoryPath + File.separator + outputFileName;
    }
}
