package org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils;

import org.apache.cxf.helpers.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.financial.services.accelerator.consent.mgt.dao.exceptions.ConsentManagementException;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.AuthorizationResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentMappingResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.DetailedConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.constants.ConsentConstant;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.exception.ConsentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;



/**
 * ConsentUtils.
 */
public class ConsentUtils {

    /**
     * Extract string payload from request object.
     *
     * @param request The request object
     * @return String payload
     */
    public static String getStringPayload(HttpServletRequest request) throws ConsentManagementException {
        try {
            return IOUtils.toString(request.getInputStream());
        } catch (IOException e) {
//            log.error(ConsentConstant.ERROR_PAYLOAD_READ, e);
            throw new ConsentManagementException(e.getMessage());
        }

    }
    /**
     * Validate the consent ID.
     *
     * @param consentId Consent Id to validate
     * @return Whether the consent ID is valid
     */
    public static boolean isConsentIdValid(String consentId) {
        return (Pattern.matches(ConsentConstant.UUID_REGEX, consentId));
    }

    /**
     * Get array list from query param.
     * @param queryParam    query param values
     * @return  array list constructed from the query param value
     */
    public static ArrayList<String> getArrayListFromQueryParam(String queryParam) {
        return queryParam != null ? new ArrayList<>(Arrays.asList(queryParam.split(","))) : null;
    }

    /**
     * Validate and retrieve query param.
     * 1. Check whether the key exists as a query param.
     * 2. Validate whether the value is a string.
     * 3. Retrieve query param.
     *
     * @param queryParams  query params
     * @param key          key to be retrieved
     * @return   query param value
     */
    public static String validateAndGetQueryParam(Map queryParams, String key) {
        if (queryParams.containsKey(key) && (((ArrayList<?>) queryParams.get(key)).get(0) instanceof String)) {
            return (String) ((ArrayList<?>) queryParams.get(key)).get(0);
        }
        return null;
    }

    /**
     * Validate and retrieve query param.
     * 1. Check whether the key exists as a query param.
     * 2. Validate whether the value is a string.
     * 3. Retrieve query param.
     *
     * @param pathParams  query params
     * @param key          key to be retrieved
     * @return   query param value
     */
    public static String validateAndGetPathParam(Map pathParams, String key) {
        if (pathParams.containsKey(key) && (((ArrayList<?>) pathParams.get(key)).get(0) instanceof String)) {
            return (String) ((ArrayList<?>) pathParams.get(key)).get(0);
        }
        return null;
    }

    /**
     * Get long values from query param.
     * @param queryParam    query param values
     * @return  long value constructed from the query param value
     */
    public static long getLongFromQueryParam(String queryParam) {
        return queryParam != null ? Long.parseLong(queryParam) : 0;
    }

    /**
     * Get int values from query param.
     * @param queryParam    query param values
     * @return  int value constructed from the query param value
     */
    public static int getIntFromQueryParam(String queryParam) {
        return queryParam != null ? Integer.parseInt(queryParam) : 0;
    }

    /**
     * Convert detailed consent resource to JSON.
     * @param detailedConsentResource   detailed consent resource
     * @return  JSON object constructed from the detailed consent resource
     */
    public static JSONObject detailedConsentToJSON(DetailedConsentResource detailedConsentResource) {
        JSONObject consentResource = new JSONObject();

        consentResource.put(ConsentConstant.CC_CONSENT_ID,
                detailedConsentResource.getConsentID());
        consentResource.put(ConsentConstant.CLIENT_ID, detailedConsentResource.getClientID());
        try {
            consentResource.put(ConsentConstant.RECEIPT,
                    new JSONObject(detailedConsentResource.getReceipt()));
        } catch (JSONException e) {
            throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, "Exception occurred while parsing" +
                    " receipt");
        }
        consentResource.put(ConsentConstant.CONSENT_TYPE, detailedConsentResource.getConsentType());
        consentResource.put(ConsentConstant.CURRENT_STATUS,
                detailedConsentResource.getCurrentStatus());
        consentResource.put(ConsentConstant.CONSENT_FREQUENCY,
                detailedConsentResource.getConsentFrequency());
        consentResource.put(ConsentConstant.VALIDITY_PERIOD,
                detailedConsentResource.getValidityPeriod());
        consentResource.put(ConsentConstant.CREATED_TIMESTAMP,
                detailedConsentResource.getCreatedTime());
        consentResource.put(ConsentConstant.UPDATED_TIMESTAMP,
                detailedConsentResource.getUpdatedTime());
        consentResource.put(ConsentConstant.RECURRING_INDICATOR,
                detailedConsentResource.isRecurringIndicator());
        JSONObject attributes = new JSONObject();
        Map<String, String> attMap = detailedConsentResource.getConsentAttributes();
        for (Map.Entry<String, String> entry : attMap.entrySet()) {
            attributes.put(entry.getKey(), entry.getValue());
        }
        consentResource.put(ConsentConstant.CONSENT_ATTRIBUTES, attributes);
        JSONArray authorizationResources = new JSONArray();
        ArrayList<AuthorizationResource> authArray = detailedConsentResource.getAuthorizationResources();
        for (AuthorizationResource resource : authArray) {
            JSONObject resourceJSON = new JSONObject();
            resourceJSON.put(ConsentConstant.AUTH_ID, resource.getAuthorizationID());
            resourceJSON.put(ConsentConstant.CC_CONSENT_ID, resource.getConsentID());
            resourceJSON.put(ConsentConstant.USER_ID, resource.getUserID());
            resourceJSON.put(ConsentConstant.AUTH_STATUS, resource.getAuthorizationStatus());
            resourceJSON.put(ConsentConstant.AUTH_TYPE, resource.getAuthorizationType());
            resourceJSON.put(ConsentConstant.UPDATE_TIME, resource.getUpdatedTime());
            authorizationResources.put(resourceJSON);
        }
        consentResource.put(ConsentConstant.AUTH_RESOURCES, authorizationResources);
        JSONArray consentMappingResources = new JSONArray();
        ArrayList<ConsentMappingResource> mappingArray = detailedConsentResource.getConsentMappingResources();
        for (ConsentMappingResource resource : mappingArray) {
            JSONObject resourceJSON = new JSONObject();
            resourceJSON.put(ConsentConstant.MAPPING_ID, resource.getMappingID());
            resourceJSON.put(ConsentConstant.AUTH_ID, resource.getAuthorizationID());
            resourceJSON.put(ConsentConstant.ACCOUNT_ID, resource.getAccountID());
            resourceJSON.put(ConsentConstant.PERMISSION, resource.getPermission());
            resourceJSON.put(ConsentConstant.MAPPING_STATUS, resource.getMappingStatus());
            consentMappingResources.put(resourceJSON);
        }
        consentResource.put(ConsentConstant.MAPPING_RESOURCES, consentMappingResources);
        return consentResource;
    }

    /**
     * Convert detailed consent resource to JSON.
     * @param consentResource  consent resource
     * @return  JSON object constructed from the  consent resource
     */
    public static JSONObject consentResourceToJSON(ConsentResource consentResource) {
        JSONObject consentResourceJSON = new JSONObject();
        consentResourceJSON.put(ConsentConstant.CONSENT_ID, consentResource.getConsentID());
        consentResourceJSON.put(ConsentConstant.CLIENT_ID, consentResource.getClientID());
        consentResourceJSON.put(ConsentConstant.CONSENT_TYPE, consentResource.getConsentType());
        consentResourceJSON.put(ConsentConstant.CURRENT_STATUS, consentResource.getCurrentStatus());
        consentResourceJSON.put(ConsentConstant.RECURRING_INDICATOR, consentResource.isRecurringIndicator());
        consentResourceJSON.put(ConsentConstant.CONSENT_ATTRIBUTES, consentResource.getConsentAttributes());
        consentResourceJSON.put(ConsentConstant.CREATED_TIME, consentResource.getCreatedTime());
        return consentResourceJSON;
    }

}
