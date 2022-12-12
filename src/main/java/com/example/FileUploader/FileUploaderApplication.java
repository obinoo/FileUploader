package com.example.FileUploader;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

@SpringBootApplication
public class FileUploaderApplication {

	public static void main(String[] args) throws IOException {

		ClassLoader classLoader = FileUploaderApplication.class.getClassLoader();
		File file = new File((Objects.requireNonNull(classLoader.getResource("serviceAccountKeys.json")))
				.getFile());
		FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

		FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount)).
		setDatabaseUrl("https://my-project-68596-default-rtdb.firebaseio.com")
				.build();
		FirebaseApp.initializeApp(options);

		SpringApplication.run(FileUploaderApplication.class, args);
	}
}