package com.tea.regDoctor.utils;

import java.io.IOException;

public class OCRUtil {
    public static final String chiSIM = "chi_sim";

    public static void runOCR(String imagePath, String outPath, String lang) throws IOException {
        Runtime r = Runtime.getRuntime();
//        StringBuffer cmd = new StringBuffer();
//        cmd.append("\"D:\\Program Files (x86)\\Tesseract-OCR\\tesseract.exe\" \"").append(imagePath).append("\" \"").append(outPath).append("\"");
        Process p = r.exec(new String[]{"D:\\Program Files (x86)\\Tesseract-OCR\\tesseract.exe", imagePath, outPath});
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
