package lessons.web;

import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    private final String httpMethod;
    private String path;
    private final String httpVersion;
    private final List<String> headers = new ArrayList<>();

    public HttpRequest(String httpMethod, String path, String httpVersion) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpVersion = httpVersion;
    }

    public void addHeader(String header) {
        headers.add(header);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public List<String> getHeaders() {
        return headers;
    }
}
