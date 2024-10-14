package com.humanit.recruiting.mortality.steps;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

@Component
public class Holder {
    private String response;
    private HttpStatusCode statusCode;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
