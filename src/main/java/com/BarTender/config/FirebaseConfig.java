package com.BarTender.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseException;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        try {

            String currentDir = new java.io.File( "." ).getCanonicalPath();
            String sysSeparator = File.separator;
            String firebaseJsonFilePath = currentDir + sysSeparator + "serverSetup" + sysSeparator + "bartender-97336-firebase-adminsdk-nukww-1b7ac22f14.json";

//            FileInputStream serviceAccount = new FileInputStream( firebaseJsonFilePath );    // TODO: USE THIS TO INIT FIREBASE ON DIFFERENT MACHINE
            FileInputStream serviceAccount = new FileInputStream( "D:\\uni\\MSP\\Diplomna\\serverSetup\\bartender-97336-firebase-adminsdk-nukww-1b7ac22f14.json" );

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials( GoogleCredentials.fromStream( serviceAccount ) )
                    .setDatabaseUrl( "https://bartender-97336.firebaseio.com" )
                    .build();

            FirebaseApp.initializeApp( options );

        } catch ( Exception e ) {
            throw new DatabaseException( "Could not configure database" );
        }
    }
}
