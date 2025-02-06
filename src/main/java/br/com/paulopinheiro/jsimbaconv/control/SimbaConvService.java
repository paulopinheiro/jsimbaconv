package br.com.paulopinheiro.jsimbaconv.control;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public final class SimbaConvService {
    private final File zipFile;
    private File convertedDirectory;

    public SimbaConvService(File zipFile) throws IOException {
        this.zipFile = zipFile;
        convert();
    }

    private void convert() throws IOException {
        File extractedDirectory = extractZipFile(this.zipFile);
        setConvertedDirectory(extractedDirectory);
        replaceTextFilesByPDF();
    }

    private void replaceTextFilesByPDF() throws IOException {
        searchAndConvert(getConvertedDirectory());
    }

    private void searchAndConvert(File file) throws IOException {
        for (File f:file.listFiles()) {
            if (f.isDirectory()) searchAndConvert(f);
            else {
                if (isPlainTextFile(f)) {
                    convertTextToPdf(f);
                    //f.delete();
                }
            }
        }
    }

    private boolean isPlainTextFile(File f) throws IOException {
        Path path = FileSystems.getDefault().getPath(f.getAbsolutePath(), new String());
        String mimeType = Files.probeContentType(path);

        return mimeType.toLowerCase().contains("text/plain");
    }

    private void convertTextToPdf(File f) throws IOException {
        PdfConverter converter = new PdfConverter(f);
    }

    private File extractZipFile(File zipFile) throws IOException {
        ZipExtractor zipExtractor = new ZipExtractor(zipFile);
        return zipExtractor.getExtractedDirectory();
    }

    private void setConvertedDirectory(File convertedDirectory) {
        this.convertedDirectory = convertedDirectory;
    }

    public File getConvertedDirectory() {
        return this.convertedDirectory;
    }
}
