package lessons.network.TCP;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.*;

public class Server {

    static class Task implements Callable<String> {
        private String msg;

        public Task(String msg) {
            this.msg = msg;
        }


        @Override
        public String call() throws Exception {
            TimeUnit.SECONDS.sleep(2);
            return handle(msg);
        }

        private String handle(String msg) {
            if (msg.equals("hello")) return "Hello client";
            else if (msg.equals("hi")) return "Hi client";
            else if (msg.equals("bye")) return "Bye client";
            else {
                return "I'm not understand";
            }
        }
    }

    private static void accept(ServerSocket server, ForkJoinPool fjp, ForkJoinPool forSending) {
        Thread thread = new Thread(() -> {

            try (Socket client = server.accept();
                 BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                String msg = br.readLine();
                System.out.println("Server got command:" + msg);
                Future<String> handleResult = fjp.submit(new Task(msg));
//                forSending.submit(new Response(handleResult.get(), client));
      } catch (IOException e) {
//                e.printStackTrace();
      }
        });
    }
}
