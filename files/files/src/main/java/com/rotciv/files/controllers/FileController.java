package com.rotciv.files.controllers;

import com.rotciv.files.services.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/api/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile[] files,
                                         @RequestParam("folder") String folderName) throws IOException {
        List<String> fileUrl = new ArrayList<>();
        for (MultipartFile file : files) {
            fileUrl.add(fileService.uploadFile(file, folderName).get("url").toString());
        }
        return ResponseEntity.ok(fileUrl);
    }
    @PostMapping("/upload/video")
    public ResponseEntity<?> uploadVideo(@RequestParam("file") MultipartFile file,
                                         @RequestParam("folder") String folderName) throws IOException {
        return ResponseEntity.ok(fileService.uploadVideo(file, folderName));
    }
}
