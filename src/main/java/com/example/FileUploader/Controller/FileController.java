package com.example.FileUploader.Controller;

import com.example.FileUploader.Model;
import com.example.FileUploader.Service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;


@Slf4j
@RestController
//@CrossOrigin("http://localhost:8081")
public class FileController {

    @Autowired
    private FileService fileService;
    private long id;

    @PostMapping("/upload-image")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws Exception {
        fileService.uploadFile(file);
        return "File uploaded Successfully";
    }


    @GetMapping("/links")
    public Object imageUrl() throws IOException, ExecutionException, InterruptedException {
        return fileService.imageUrl();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") long id) {
        this.id = id;
        fileService.deleteImage();
    }
}