package lessons.web;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Logger;

public class RequestHandler implements Runnable {

    private static final Logger logger = Logger.getLogger(RequestHandler.class.getName());

    private static final String NOT_IMP_HTML = "<HTML><HEAD><TITLE>Not implemented</TITLE></HEAD><BODY><H1>HTTP Error 501: Not implemented</H1></BODY></HTML>";
    private static final String HTTP_GET_METHOD = "GET";
    private static final String HTTP_NOT_IMPL_RESPONSE = "HTTP/1.0 501 Not Implemented";
    private static final String SERVER_ID_HEADER = "Server: Httpd 1.0";
    private static final String HTTP_OK_RESPONSE = "HTTP/1.0 200 OK";
    private static final String NOT_FOUND_RESPONSE = "HTTP/1.0 404 File Not Found";
    private static final String NOT_FOUND_HTML = "<HTML><HEAD><TITLE>File Not Found</TITLE></HEAD><BODY><H1>HTTP Error 404: File Not Found</H1></BODY></HTML>";
    private final Socket clientSocket;
    private final File rootDir;

    public RequestHandler(Socket clientSocket, File rootDir) {
        this.clientSocket = clientSocket;
        this.rootDir = rootDir;
    }

    @Override
    public void run() {
        try {
            HttpRequest request = readRequest();
            if (request == null) {
                return;
            }
            if (request.getHttpMethod().equals(HTTP_GET_METHOD)) {
                logger.info("New GET request");
                handleGetRequest(request);

            } else {
                logger.info("Invalid request");
                sendErrorMessage(HTTP_NOT_IMPL_RESPONSE, NOT_IMP_HTML, request.getHttpVersion());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void sendErrorMessage(String code, String html, String version) throws IOException {
        PrintWriter pw = new PrintWriter(clientSocket.getOutputStream());
        if (version.startsWith("HTTP/")) {
            pw.println(code);
            pw.println("Date: " + (new Date()));
            pw.println(SERVER_ID_HEADER);
            pw.println("Content-type: text/html");
            pw.println();

        }
        pw.println(html);
        pw.flush();


    }

    private void handleGetRequest(HttpRequest request) throws IOException {
        OutputStream toClient = clientSocket.getOutputStream();
        if (request.getPath().endsWith("/")) {
            request.setPath(request.getPath() + "index.html");
        }
        try {
            byte[] fileContent = readFile(removeInitialSlash(request.getPath()));
            if (request.getHttpVersion().startsWith("HTTP/")) {
                PrintWriter pw = new PrintWriter(toClient);
                pw.println(HTTP_OK_RESPONSE);
                pw.println("Date: " + LocalDateTime.now());
                pw.println(SERVER_ID_HEADER);
                pw.println("Content-length: " + fileContent.length);
                pw.println("Content-type: " + getMimeFromExtension(request.getPath()));
                pw.println();
                pw.flush();
            }
            toClient.write(fileContent);
        } catch (IOException e) {
            sendErrorMessage(NOT_FOUND_RESPONSE, NOT_FOUND_HTML, request.getHttpVersion());
            e.printStackTrace();
        }


    }

    private String getMimeFromExtension(String name) {
        if (name.endsWith(".html") || name.endsWith(".htm")) {
            return "text/html";

        } else if (name.endsWith(".txt") || name.endsWith(".java")) {
            return "text/plain";
        } else if (name.endsWith(".gif")) {
            return "image/gif";
        } else if (name.endsWith(".class")) {
            return "application/octet-stream";
        } else if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
            return "image/jpeg";
        } else {
            return "text/plain";
        }
    }

    private String removeInitialSlash(String source) {
        return source.substring(1, source.length());
    }

    private byte[] readFile(String filePathRelativeToRootDir) throws IOException {
        File file = new File(rootDir, filePathRelativeToRootDir);
        try (FileInputStream fromFile = new FileInputStream(file)) {
            byte[] buf = new byte[(int) file.length()];
            fromFile.read(buf);
            return buf;
        }
    }

    private HttpRequest readRequest() throws IOException {
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String requestLine = fromClient.readLine();
        if (requestLine == null) {
            return null;
        }
        String[] requestTokens = requestLine.split(" ");
        HttpRequest request = new HttpRequest(requestTokens[0], requestTokens[1], requestTokens[2]);

        while ((requestLine = fromClient.readLine()) != null && !requestLine.trim().equals("")) {
            request.addHeader(requestLine);
        }
        return request;
    }


}
