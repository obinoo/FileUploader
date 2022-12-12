package com.example.FileUploader.Service;

import com.example.FileUploader.Model;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class FileService {

    private Storage storage;
    private  Blob blob;
    private String objectName;
    private  DatabaseReference myRef;
    private FirebaseDatabase db;
    //static String var;



    //METHOD FOR UPLOADING THE IMAGES TO FIREBASE CLOUD STORAGE
    public ResponseEntity<String> uploadFile(MultipartFile multipartFile) throws IOException {
        objectName = generateFileName(multipartFile);

        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setProjectId("my-project-68596")
                .setCredentials(GoogleCredentials
                        .fromStream(new ClassPathResource("serviceAccountKeys.json").getInputStream()))
                .build();

        storage = storageOptions.getService();

        BlobId blobId = BlobId.of("my-project-68596.appspot.com", objectName);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(multipartFile.getContentType()).build();

        blob = storage.create(blobInfo, multipartFile.getBytes());


        return ResponseEntity.status(HttpStatus.CREATED).body("File uploaded Successfully");
    }

    //GENERATING NAME FOR FILE
    public String generateFileName(MultipartFile multipartFile) {
        return UUID.randomUUID().toString();
    }



    //GETTING LINK FOR THE UPLOADED FILE/IMAGE
    public Object imageUrl() throws StorageException, IOException, ExecutionException, InterruptedException {
        URL var = blob.signUrl(7, TimeUnit.DAYS, Storage.SignUrlOption.withV4Signature());

        String imageUrls = String.valueOf(var);

        Model model = new Model(imageUrls);

     db = FirebaseDatabase.getInstance();
     myRef = db.getReference().child("Model");
     myRef.push().setValue(model, (databaseError, databaseReference) -> {});
        return  ResponseEntity.status(HttpStatus.CREATED).body(var);

    }


    //DELETE IMAGE
    public void deleteImage(){

        storage.delete("my-project-68596.appspot.com", objectName);
    }
}
