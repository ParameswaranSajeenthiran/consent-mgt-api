package org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils;

import org.apache.cxf.helpers.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ConsentUtils {
    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }

    /**
     * Util method to extract the payload from HTTP request object. Can be
     * JSONObject or JSONArray
     *
     * @param request The HTTP request object
     * @return Object payload can be either an instance of JSONObject or JSONArray
     *         only. Can be a ConsentException if
     *         is and error scenario. Error is returned instead of throwing since
     *         the error response should be handled by the
     *         toolkit is the manage scenario.
     */
    public static Object getPayload(HttpServletRequest request) {
        try {
            return new JSONObject(getStringPayload(request));
        } catch (JSONException e) {
            try {
                return new JSONArray(getStringPayload(request));
            } catch (JSONException ex) {
//                log.error(ConsentConstants.ERROR_PAYLOAD_PARSE + ". Returning null", ex);
                return null;
            }
        }
    }

    /**
     * Extract string payload from request object.
     *
     * @param request The request object
     * @return String payload
     */
    public static String getStringPayload(HttpServletRequest request)  {
        try {
            return IOUtils.toString(request.getInputStream());
        } catch (IOException e) {
//            log.error(ConsentConstants.ERROR_PAYLOAD_READ, e);
//            throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, ConsentConstants.ERROR_PAYLOAD_READ);
        }
        return null;
    }
}
