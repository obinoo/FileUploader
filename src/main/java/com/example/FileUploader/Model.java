package com.example.FileUploader;

import lombok.Data;

@Data
public class Model {

    private String imageUrls;

    public Model(String imageUrls){
        this.imageUrls = imageUrls;
    }

    public Model() {
    }


}
