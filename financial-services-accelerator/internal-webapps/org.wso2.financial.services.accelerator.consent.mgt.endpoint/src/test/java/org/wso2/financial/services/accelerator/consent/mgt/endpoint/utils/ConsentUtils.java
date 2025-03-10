package org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.cxf.helpers.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.wso2.financial.services.accelerator.consent.mgt.dao.exceptions.ConsentManagementException;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.AuthorizationResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentMappingResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.DetailedConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.constants.ConsentConstant;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.AuthResponse;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.AuthorizationResourceDTO;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.ConsentResourceDTO;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.ConsentResponse;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.ReauthorizeResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    public static String getStringPayload(HttpServletRequest request) throws
            ConsentManagementException {
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
        return (Pattern.matches(ConsentConstant.UUID_REGEX,
                consentId));
    }

    /**
     * Get array list from query param.
     *
     * @param queryParam query param values
     * @return array list constructed from the query param value
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
     * @param queryParams query params
     * @param key         key to be retrieved
     * @return query param value
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
     * @param pathParams query params
     * @param key        key to be retrieved
     * @return query param value
     */
    public static String validateAndGetPathParam(Map pathParams, String key) {
        if (pathParams.containsKey(key) && (((ArrayList<?>) pathParams.get(key)).get(0) instanceof String)) {
            return (String) ((ArrayList<?>) pathParams.get(key)).get(0);
        }
        return null;
    }

    /**
     * Get long values from query param.
     *
     * @param queryParam query param values
     * @return long value constructed from the query param value
     */
    public static long getLongFromQueryParam(String queryParam) {
        return queryParam != null ? Long.parseLong(queryParam) : 0;
    }

    /**
     * Get int values from query param.
     *
     * @param queryParam query param values
     * @return int value constructed from the query param value
     */
    public static int getIntFromQueryParam(String queryParam) {
        return queryParam != null ? Integer.parseInt(queryParam) : 0;
    }

    public static Map<String, String> convertToMap(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(obj, Map.class);
        return map.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString()));
    }

    /**
     * copy propoerties from consentResource DTO to consentResource DAO
     *
     * @param consentResourceDTO
     */
    public static void copyPropertiesToConsentResource(ConsentResource consentResource,
                                                       ConsentResourceDTO consentResourceDTO) throws
            ConsentManagementException {
        consentResource.setConsentType(consentResourceDTO.getConsentType());
        consentResource.setClientID(consentResourceDTO.getClientID());
        consentResource.setRecurringIndicator(consentResourceDTO.getRecurringIndicator());
        consentResource.setValidityPeriod(consentResourceDTO.getValidityPeriod());
        consentResource.setConsentAttributes(ConsentUtils.convertToMap(consentResourceDTO.getConsentAttributes()));
        consentResource.setReceipt(consentResourceDTO.getReceipt());
        consentResource.setCurrentStatus(consentResourceDTO.getCurrentStatus());
    }

    /**
     * copy properties from authorizationResourceDTo to authorizationResource
     */
    public static void copyPropertiesToAuthorizationResource(AuthorizationResource authorizationResource,
                                                             AuthorizationResourceDTO authorizationResourceDTO) {
        authorizationResource.setAuthorizationType(authorizationResourceDTO.getAuthorizationType());
        authorizationResource.setAuthorizationStatus(authorizationResourceDTO.getAuthorizationStatus());
        authorizationResource.setUserID(authorizationResourceDTO.getUserId());
    }

    /**
     * copy properties from authorizationResourceDTo to authorizationResource
     */
    public static void copyPropertiesToAuthorizationResource(AuthorizationResource authorizationResource,
                                                             ReauthorizeResource authorizationResourceDTO) {
        authorizationResource.setAuthorizationID(authorizationResourceDTO.getAuthorizationId());
        authorizationResource.setAuthorizationType(authorizationResourceDTO.getAuthorizationType());
        authorizationResource.setAuthorizationStatus(authorizationResourceDTO.getAuthorizationStatus());
        authorizationResource.setUserID(authorizationResourceDTO.getUserId());
        ArrayList<ConsentMappingResource> consentMappingResources = new ArrayList<>();
        for (Resource resource : authorizationResourceDTO.getResources()) {
            ConsentMappingResource consentMappingResource = new ConsentMappingResource();
            consentMappingResource.setAccountID(resource.getAccountID());
            consentMappingResource.setPermission(resource.getPermission());
            consentMappingResource.setMappingStatus(resource.getResourceMappingStatus());
            consentMappingResources.add(consentMappingResource);

        }
        authorizationResource.setConsentMappingResource(consentMappingResources);

    }


    /**
     * copy properties from consentResource to consentResponse
     */
    public static void copyPropertiesToAuthorizationResourceResponse(AuthResponse authorizationResourceResponseResponse,
                                                                     AuthorizationResource authorizationResource) {
        authorizationResourceResponseResponse.setAuthId(authorizationResource.getAuthorizationID());
        authorizationResourceResponseResponse.setUserId(authorizationResource.getUserID());
        authorizationResourceResponseResponse.setAuthorizationStatus(authorizationResource.getAuthorizationStatus());
        authorizationResourceResponseResponse.setAuthorizationType(authorizationResource.getAuthorizationType());


    }

    /**
     * copy properties from consentResourceMapping  to consentResourceMappingResponse
     */
    public static void copyPropertiesToConsentMappingResourceResponse(Resource consentMappingResourceResponse,
                                                                      ConsentMappingResource consentMappingResource) {
        consentMappingResourceResponse.setResourceMappingId(consentMappingResource.getMappingID());
        consentMappingResourceResponse.setAccountID(consentMappingResource.getAccountID());
        consentMappingResourceResponse.setPermission(consentMappingResource.getPermission());
        consentMappingResourceResponse.setResourceMappingStatus(consentMappingResource.getMappingStatus());

    }

    /**
     * copy properties from consentResourceMapping  to consentResourceMappingResponse
     */
    public static void copyPropertiesToConsentMappingResource(Resource orgConsentMappingResource,
                                                              ConsentMappingResource desConsentMappingResource) {
        desConsentMappingResource.setMappingID(orgConsentMappingResource.getResourceMappingId());
        desConsentMappingResource.setAccountID(orgConsentMappingResource.getAccountID());
        desConsentMappingResource.setPermission(orgConsentMappingResource.getPermission());
        desConsentMappingResource.setMappingStatus(orgConsentMappingResource.getResourceMappingStatus());

    }

    /**
     * copy properties to consentResourceResponse
     */
    public static void copyPropertiesToConsentResourceResponse(ConsentResponse consentResourceResponse,
                                                               DetailedConsentResource consentResource,
                                                               boolean withAuthResources,
                                                               boolean withConsentMappingResources) {
        consentResourceResponse.setConsentID(consentResource.getConsentID());
        consentResourceResponse.setClientID(consentResource.getClientID());
        consentResourceResponse.setConsentType(consentResource.getConsentType());
        consentResourceResponse.setRecurringIndicator(consentResource.isRecurringIndicator());
        consentResourceResponse.setConsentAttributes(consentResource.getConsentAttributes());
        consentResourceResponse.setCreatedTime((int) consentResource.getCreatedTime());
        consentResourceResponse.setValidityPeriod((int) consentResource.getValidityPeriod());
        consentResourceResponse.setCurrentStatus(consentResource.getCurrentStatus());
        consentResourceResponse.setUpdatedTime((int) consentResource.getUpdatedTime());
        consentResourceResponse.setReceipt(consentResource.getReceipt());


        if (withAuthResources) {
            ArrayList<AuthResponse> authResponses = new ArrayList<>();
            for (AuthorizationResource authorizationResource : consentResource.getAuthorizationResources()) {
                AuthResponse authResponse = new AuthResponse();
                ConsentUtils.copyPropertiesToAuthorizationResourceResponse(authResponse, authorizationResource);
                ArrayList<Resource> resources = new ArrayList<>();
                if (withConsentMappingResources) {
                    for (ConsentMappingResource consentMappingResource :
                            authorizationResource.getConsentMappingResource()) {
                        Resource resource = new Resource();
                        ConsentUtils.copyPropertiesToConsentMappingResourceResponse(resource, consentMappingResource);
                        resources.add(resource);
                    }
                }
                authResponse.setResources(resources);
                authResponses.add(authResponse);
            }
            consentResourceResponse.setAuthorizationResources(authResponses);
        } else {
            consentResourceResponse.setAuthorizationResources(new ArrayList<>());
        }
    }


    /**
     * Convert detailed consent resource to JSON.
     *
     * @param detailedConsentResource detailed consent resource
     * @return JSON object constructed from the detailed consent resource
     */
    public static JSONObject detailedConsentToJSON(DetailedConsentResource detailedConsentResource) {
        JSONObject consentResource = new JSONObject();

        consentResource.put(ConsentConstant.CC_CONSENT_ID,
                detailedConsentResource.getConsentID());
        consentResource.put(ConsentConstant.CLIENT_ID,
                detailedConsentResource.getClientID());
        consentResource.put(ConsentConstant.RECEIPT,
                detailedConsentResource.getReceipt());

        consentResource.put(ConsentConstant.CONSENT_TYPE,
                detailedConsentResource.getConsentType());
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
            attributes.put(entry.getKey(),
                    entry.getValue());
        }
        consentResource.put(ConsentConstant.CONSENT_ATTRIBUTES,
                attributes);
        JSONArray authorizationResources = new JSONArray();
        ArrayList<AuthorizationResource> authArray = detailedConsentResource.getAuthorizationResources();
        for (AuthorizationResource resource : authArray) {
            JSONObject resourceJSON = new JSONObject();
            resourceJSON.put(ConsentConstant.AUTH_ID,
                    resource.getAuthorizationID());
            resourceJSON.put(ConsentConstant.CC_CONSENT_ID,
                    resource.getConsentID());
            resourceJSON.put(ConsentConstant.USER_ID,
                    resource.getUserID());
            resourceJSON.put(ConsentConstant.AUTH_STATUS,
                    resource.getAuthorizationStatus());
            resourceJSON.put(ConsentConstant.AUTH_TYPE,
                    resource.getAuthorizationType());
            resourceJSON.put(ConsentConstant.UPDATE_TIME,
                    resource.getUpdatedTime());
            authorizationResources.put(resourceJSON);
        }
        consentResource.put(ConsentConstant.AUTH_RESOURCES,
                authorizationResources);
        JSONArray consentMappingResources = new JSONArray();
        ArrayList<ConsentMappingResource> mappingArray = detailedConsentResource.getConsentMappingResources();
        for (ConsentMappingResource resource : mappingArray) {
            JSONObject resourceJSON = new JSONObject();
            resourceJSON.put(ConsentConstant.MAPPING_ID,
                    resource.getMappingID());
            resourceJSON.put(ConsentConstant.AUTH_ID,
                    resource.getAuthorizationID());
            resourceJSON.put(ConsentConstant.ACCOUNT_ID,
                    resource.getAccountID());
            resourceJSON.put(ConsentConstant.PERMISSION,
                    resource.getPermission());
            resourceJSON.put(ConsentConstant.MAPPING_STATUS,
                    resource.getMappingStatus());
            consentMappingResources.put(resourceJSON);
        }
        consentResource.put(ConsentConstant.MAPPING_RESOURCES,
                consentMappingResources);
        return consentResource;
    }

    /**
     * Convert detailed consent resource to JSON.
     *
     * @param consentResource consent resource
     * @return JSON object constructed from the  consent resource
     */
    public static JSONObject consentResourceToJSON(ConsentResource consentResource) {
        JSONObject consentResourceJSON = new JSONObject();
        consentResourceJSON.put(ConsentConstant.CONSENT_ID,
                consentResource.getConsentID());
        consentResourceJSON.put(ConsentConstant.CLIENT_ID,
                consentResource.getClientID());
        consentResourceJSON.put(ConsentConstant.CONSENT_TYPE,
                consentResource.getConsentType());
        consentResourceJSON.put(ConsentConstant.CURRENT_STATUS,
                consentResource.getCurrentStatus());
        consentResourceJSON.put(ConsentConstant.RECURRING_INDICATOR,
                consentResource.isRecurringIndicator());
        consentResourceJSON.put(ConsentConstant.CONSENT_ATTRIBUTES,
                consentResource.getConsentAttributes());
        consentResourceJSON.put(ConsentConstant.CREATED_TIME,
                consentResource.getCreatedTime());
        return consentResourceJSON;
    }

}
