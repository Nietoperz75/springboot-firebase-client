package pw.robac.springbootfirebaseclient;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.annotations.Nullable;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.security.servlet.ApplicationContextRequestMatcher;
//import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {

//    @EventListener(ApplicationReadyEvent.class)
    public void get() throws IOException, ExecutionException, InterruptedException {

        FileInputStream serviceAccount =
                new FileInputStream("/home/roland/IdeaProjects/springboot_firebase/src/main/resources/livestream.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://livestream-cee4d.firebaseio.com")
                .build();

        FirebaseApp.initializeApp(options);

        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection("cities").document("LA");
        CollectionReference dbref2 = db.collection("cities");
        dbref2.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirestoreException e) {
                if(e !=null){
                    System.out.println("sie wywalilo");
                    return;
                }
                if(queryDocumentSnapshots!=null){
                    System.out.println("Nowe dane");
                    System.out.println(queryDocumentSnapshots.getDocumentChanges().get(0).getDocument().getData());
                    queryDocumentSnapshots.getDocumentChanges().forEach(System.out::println);
                    queryDocumentSnapshots.getDocuments().forEach(System.out::println);
//                    System.out.println("Current data "+queryDocumentSnapshots.getDocuments().get(0).);

                }
            }
        });
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirestoreException e) {
                if (e != null) {
                    System.err.println("Listen failed: " + e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    System.out.println("Current data: " + snapshot.getData());
                } else {
                    System.out.print("Current data: null");
                }
            }
        });
    }
}
