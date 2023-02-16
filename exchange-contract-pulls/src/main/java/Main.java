import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.bjs.ws.EchoClient;

import java.io.IOException;

@SpringBootApplication
@ComponentScan("com.bjs")
public class Main {

    public static void main(String[] args) throws IOException, WebSocketException {
        SpringApplication.run(Main.class);
    }

}
