package pw.robac.springbootfirebaseclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class SpringbootFirebaseClientApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext cas = SpringApplication.run(SpringbootFirebaseClientApplication.class, args);
        FirebaseService fbs = cas.getBean(FirebaseService.class);
        try {
            fbs.get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
