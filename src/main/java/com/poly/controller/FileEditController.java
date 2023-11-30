package com.poly.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.charset.StandardCharsets;

@Controller
public class FileEditController {

    private static final String UPLOAD_FOLDER = "uploads"; // Thư mục lưu trữ file

    @GetMapping("/edit")
    public String showEditForm() {
        return "upload";
    }

    @PostMapping("/edit")
    public String handleFileEdit(@RequestParam("file") MultipartFile file, Model model) {
        if (!file.isEmpty()) {
            try {
                InputStream inputStream = file.getInputStream();
                String fileContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

                model.addAttribute("fileName", file.getOriginalFilename());
                model.addAttribute("fileContent", fileContent);
            } catch (IOException e) {
                // Xử lý lỗi đọc file
                e.printStackTrace();
                model.addAttribute("error", "Error reading the file.");
            }
        } else {
            model.addAttribute("error", "Please select a file.");
        }

        return "result";
    }

    @PostMapping("/save")
    public String handleFileSave(@RequestParam("fileName") String fileName, @RequestParam("fileContent") String fileContent, Model model) {
        try {
            File file = new File(UPLOAD_FOLDER + File.separator + fileName);

            // Kiểm tra và tạo thư mục nếu nó không tồn tại
            file.getParentFile().mkdirs();

            // Kiểm tra sự tồn tại của file
            if (file.exists()) {
                // Mở và sửa file
                try (FileWriter fileWriter = new FileWriter(file)) {
                    fileWriter.write(fileContent);
                    model.addAttribute("success", "File saved successfully.");
                }
            } else {
                // File không tồn tại
                model.addAttribute("error", "File does not exist.");
            }
        } catch (IOException e) {
            // In ra lỗi chi tiết
            e.printStackTrace();
            model.addAttribute("error", "Error saving the file: " + e.getMessage());
        }

        return "result";
    }



}
