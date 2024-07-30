package com.goev.central.config;


import com.amazonaws.util.StringInputStream;
import com.goev.central.constant.ApplicationConstants;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
@AllArgsConstructor
public class FirebaseConfig {


    @Bean
     public FirebaseApp getFirebase(ApplicationConstants applicationConstants) throws IOException {


         FirebaseOptions options = new FirebaseOptions.Builder()
                 .setCredentials(GoogleCredentials.fromStream(new StringInputStream(applicationConstants.FIREBASE_CONFIG)))
                 .setDatabaseUrl(applicationConstants.FIREBASE_URL)
                 .build();

         return FirebaseApp.initializeApp(options);
     }

}
