package org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto;


import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ResponseStatus;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Data wrapper for consent management data.
 */
public class ConsentMgtDTO {
    private String clientId;
    private Map<String, String> headers;
    private Object payload;
    private Map queryParams;
    private String requestPath;
    private String absolutePath;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ResponseStatus responseStatus;
    private Object responsePayload;

    public ConsentMgtDTO(String clientId, Map<String, String> headers, Object payload, Map queryParams,
                         String requestPath, String absolutePath, HttpServletRequest request, HttpServletResponse
                                 response) {
        this.clientId = clientId;
        this.headers = headers;
        this.payload = payload;
        this.queryParams = queryParams;
        this.requestPath = requestPath;
        this.absolutePath = absolutePath;
        this.request = request;
        this.response = response;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }


    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public Map getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map queryParams) {
        this.queryParams = queryParams;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Object getResponsePayload() {
        return responsePayload;
    }

    public void setResponsePayload(Object responsePayload) {
        this.responsePayload = responsePayload;
    }
}
