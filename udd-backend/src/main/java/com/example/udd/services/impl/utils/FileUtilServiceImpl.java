package com.example.udd.services.impl.utils;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUtilServiceImpl {
    private static String docFile = "./saved_documents";
    public String saveFile(MultipartFile cv, String folderPath, String fileName) throws IOException {

        if(cv != null)
            save(cv, docFile + folderPath, fileName + ".pdf");

        return folderPath + "/" + fileName + ".pdf";
    }

    private void save(MultipartFile pdfFile, String directoryToUpload , String fileName) throws IOException {
        Path uploadPath = Paths.get(directoryToUpload);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = pdfFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save file: " + fileName, ioe);
        }
    }

    public String extractPDFFile(String filePath) throws IOException {
        try {
            PDDocument pdDoc = Loader.loadPDF(new File(docFile + filePath));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(pdDoc);
        } catch (IOException e) {
            throw new IOException("Could not extract file: " + docFile + filePath);
        }
    }
}
