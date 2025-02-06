package br.com.paulopinheiro.jsimbaconv.control;

import java.io.File;
import java.io.IOException;

public class SimbaConvService {
    private final File zipFile;
    private File convertedDirectory;

    public SimbaConvService(File zipFile) throws IOException {
        this.zipFile = zipFile;
        convert();
    }

    private void convert() throws IOException {
        setConvertedDirectory(extractZipFile(this.zipFile));
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
