package org.wso2.financial.services.accelerator.consent.mgt.endpoint.exception;


import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.constants.AuthErrorCode;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.constants.ConsentConstant;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ResponseStatus;

import java.net.URI;

/**
 * Consent exception class to be used in consent components and extensions.
 */
public class ConsentException extends RuntimeException {

    private JSONObject payload;
    private ResponseStatus status;
    private URI errorRedirectURI;

    public ConsentException(ResponseStatus status, String errorMessage, Throwable cause) {

        super(cause);
        this.status = status;
        this.payload = createDefaultErrorObject(null, String.valueOf(this.status.getStatusCode()),
                errorMessage, null);
    }

    public ConsentException(ResponseStatus status, String errorCode, String errorMessage) {

        this.status = status;
        this.payload = createDefaultErrorObject(null, errorCode, errorMessage, null);
    }

    public ConsentException(ResponseStatus status, String errorMessage) {

        this.status = status;
        this.payload = createDefaultErrorObject(null, String.valueOf(this.status.getStatusCode()),
                errorMessage, null);
    }

    public ConsentException(ResponseStatus status, JSONObject payload) {

        this.status = status;
        this.payload = payload;
    }

    /**
     * This method is created to send error redirects in the authorization flow. The parameter validations are done
     * in compliance with the OAuth2 and OIDC specifications.
     *
     * @param errorURI         REQUIRED The base URI which the redirect should go to.
     * @param error            REQUIRED The error code of the error. Should be a supported value in OAuth2/OIDC
     * @param errorDescription OPTIONAL The description of the error.
     * @param state            REQUIRED if a "state" parameter was present in the client authorization request.
     */
    public ConsentException(URI errorURI, AuthErrorCode error, String errorDescription, String state) {

        if (errorURI != null && error != null) {
            //add 302 as error code since this will be a redirect
            errorRedirectURI = errorURI;
            this.status = ResponseStatus.FOUND;
            this.payload = createDefaultErrorObject(errorURI, error.toString(), errorDescription, state);
        }
    }

    public JSONObject createDefaultErrorObject(URI redirectURI, String errorCode, String errorDescription,
                                               String state) {

        JSONObject error = new JSONObject();
        error.put(ConsentConstant.ERROR_CODE, errorCode);
        error.put(ConsentConstant.ERROR_MSG, "Consent Management Error");
        error.put(ConsentConstant.ERROR_DESCRIPTION, errorDescription);
        if (state != null) {
            error.put(ConsentConstant.STATE, state);
        }
        if (redirectURI != null) {
            error.put(ConsentConstant.REDIRECT_URI, redirectURI.toString());
        }

        JSONArray errorList = new JSONArray();
        errorList.put(error);

        JSONObject errorObj = new JSONObject();
        errorObj.put(ConsentConstant.ERRORS, errorList);
        return errorObj;
    }

    public JSONObject getPayload() {

        return payload;
    }

    public ResponseStatus getStatus() {

        return status;
    }

    public URI getErrorRedirectURI() {

        return errorRedirectURI;
    }

    public void setErrorRedirectURI(URI errorRedirectURI) {

        this.errorRedirectURI = errorRedirectURI;
    }
}
