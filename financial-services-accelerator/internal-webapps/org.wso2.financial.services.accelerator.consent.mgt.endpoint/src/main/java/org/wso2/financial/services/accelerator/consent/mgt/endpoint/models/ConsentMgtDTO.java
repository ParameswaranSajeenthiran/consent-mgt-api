package org.wso2.financial.services.accelerator.consent.mgt.endpoint.models;


import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Data wrapper for consent management data.
 */
public class ConsentMgtDTO {
    private Map<String, String> headers;
    private Object payload;
    private Map queryParams;
    private Map pathParameters;
    private String requestPath;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ResponseStatus responseStatus;
    private Object responsePayload;

    public ConsentMgtDTO(
            Map<String, String> headers, Object payload, Map queryParams,
            String requestPath, Map pathParameters, HttpServletRequest request, HttpServletResponse
                    response)
    {
        this.headers = headers;
        this.payload = payload;
        this.queryParams = queryParams;
        this.pathParameters = pathParameters;
        this.requestPath = requestPath;
        this.request = request;
        this.response = response;
    }

    public String getRequestPath()
    {
        return requestPath;
    }

    public void setRequestPath(String requestPath)
    {
        this.requestPath = requestPath;
    }

    public Map getPathParameters()
    {
        return pathParameters;
    }

    public void setPathParameters(Map pathParameters)
    {
        this.pathParameters = pathParameters;
    }

    public Map<String, String> getHeaders()
    {
        return headers;
    }

    public void setHeaders(Map<String, String> headers)
    {
        this.headers = headers;
    }

    public Object getPayload()
    {
        return payload;
    }

    public void setPayload(Object payload)
    {
        this.payload = payload;
    }

    public Map getQueryParams()
    {
        return queryParams;
    }

    public void setQueryParams(Map queryParams)
    {
        this.queryParams = queryParams;
    }


    public HttpServletRequest getRequest()
    {
        return request;
    }

    public void setRequest(HttpServletRequest request)
    {
        this.request = request;
    }

    public HttpServletResponse getResponse()
    {
        return response;
    }

    public void setResponse(HttpServletResponse response)
    {
        this.response = response;
    }

    public ResponseStatus getResponseStatus()
    {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus)
    {
        this.responseStatus = responseStatus;
    }

    public Object getResponsePayload()
    {
        return responsePayload;
    }

    public void setResponsePayload(Object responsePayload)
    {
        this.responsePayload = responsePayload;
    }
}
