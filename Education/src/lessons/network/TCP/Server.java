package lessons.network.TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket socket = new ServerSocket(8080)) {
            System.out.println("Server is started....");
            while(true) {

                try (Socket client = socket.accept()) {
                    handleRequest(client);
                }
            }
        }
    }


    private static void handleRequest(Socket client) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));


        StringBuilder sb = new StringBuilder("Hello, ");
        String userName = br.readLine();
        System.out.println("Server got string:" + userName);


        sb.append(userName);
        bw.write(sb.toString());
        bw.newLine();
        bw.flush();

        br.close();
        bw.close();
        client.close();
    }
}
