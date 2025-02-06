package br.com.paulopinheiro.jsimbaconv.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipExtractor {
    private final ZipInputStream zipStream;
    private final File extractedDirectory;

    public ZipExtractor(File zipFile) throws FileNotFoundException, IOException {
        this.zipStream = new ZipInputStream(new FileInputStream(zipFile));
        String zipFilePath = zipFile.getAbsolutePath();
        String extractedDirPath = removeExtension(zipFilePath,false);
        this.extractedDirectory = new File(extractedDirPath);
        extract();
    }

    private void extract() throws IOException {
        byte[] buffer = new byte[1024];
        ZipEntry zipEntry = this.zipStream.getNextEntry();
        while (zipEntry != null) {
            File newFile = newFile(this.getExtractedDirectory(), zipEntry);
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream outputStream = new FileOutputStream(newFile);
                int len;
                while ((len = zipStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.close();
            }
            zipEntry = zipStream.getNextEntry();
        }
        zipStream.closeEntry();
        zipStream.close();
    }

    public File getExtractedDirectory() {
        return this.extractedDirectory;
    }

    private static String removeExtension(String filename, boolean removeAllExtensions) {
        String extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
        return filename.replaceAll(extPattern, "");
    }

    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
