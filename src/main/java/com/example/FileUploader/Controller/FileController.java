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
        return "File uploaded Successfully" ;
    }


     @GetMapping("/links")
    public Object imageUrl() throws IOException, ExecutionException, InterruptedException {
         return fileService.imageUrl();
     }

     @DeleteMapping("/{id}")
     public void delete(@PathVariable("id") long id){
         this.id = id;
         fileService.deleteImage();
     }



















//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
//        String message = "";
//
//        fileService.store(file);
//
//        message = "Uploaded the file successfully: " + file.getOriginalFilename();
//        return ResponseEntity.status(HttpStatus.OK).body("Success");
//    }
//
//    @GetMapping("/files")
//    public ResponseEntity<List<ResponseFile>> getListFiles() {
//        List<ResponseFile> files = fileService.getAllFiles().map(dbFile -> {
//            String fileDownloadUri = ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path("/files/")
//                    .path(dbFile.getId())
//                    .toUriString();
//
//            return new ResponseFile(
//                    dbFile.getName(),
//                    fileDownloadUri,
//                    dbFile.getType());
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(files);
//    }

}
