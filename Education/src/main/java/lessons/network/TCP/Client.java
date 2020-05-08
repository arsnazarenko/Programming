package lessons.network.TCP;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 10; i++) {
            sendRequest();
        }
    }

    private static void sendRequest() throws IOException {

        try (Socket socket = new Socket("127.0.0.1", 8080)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String text = "Arseniy";
            bw.write(text);
            bw.newLine();
            bw.flush();

            String answer = br.readLine();
            System.out.println("client got string: " + answer);

            br.close();
            bw.close();
        }

    }
}