package org.wso2.financial.services.accelerator.consent.mgt.endpoint.handler;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.financial.services.accelerator.consent.mgt.dao.exceptions.ConsentManagementException;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.AuthorizationResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentMappingResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.DetailedConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.exception.ConsentException;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.AmendmentResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.AuthResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.AuthResponse;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.BulkConsentStatusUpdateResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.ConsentResponse;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.ConsentStatusUpdateResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.ReauthorizeResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.Resource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ConsentUtils;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ResponseStatus;
import org.wso2.financial.services.accelerator.consent.mgt.service.impl.ConsentCoreServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * ConsentMgtApiHandler.
 */
public class ConsentMgtApiHandler {
    private static final Log log = LogFactory.getLog(ConsentMgtApiHandler.class);
    private final ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();

    public ConsentMgtApiHandler() {
    }


    public ArrayList<DetailedConsentResource> consentGet(
            String orgInfo,
            String consentType, String consentStatuse
            , String userID, long fromTimeValue, long toTimeValue, int limitValue, int offsetValue) throws
            ConsentException {

        int total = 0;
        ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();


        int count;
        ArrayList<DetailedConsentResource> results = new ArrayList<>();
        JSONObject response = new JSONObject();

        // intialize the search results
        ArrayList<String> consentIDs = new ArrayList<>();
        ArrayList<String> clientIDs = new ArrayList<>();
        ArrayList<String> consentTypes = new ArrayList<>();
        ArrayList<String> consentStatuses = new ArrayList<>();
        ArrayList<String> userIDs = new ArrayList<>();
        if (consentType != null) {
            consentTypes.add(consentType);
        }
        if (consentStatuse != null) {
            consentStatuses.add(consentStatuse);
        }
        if (userID != null) {
            userIDs.add(userID);
        }


        Long fromTime = null;
        Long toTime = null;
        Integer limit = null;
        Integer offset = null;
        fromTime = fromTimeValue == 0L ? null : fromTimeValue;
        toTime = toTimeValue == 0L ? null : toTimeValue;
        limit = limitValue == 0 ? null : limitValue;
        offset = offsetValue == 0 ? null : offsetValue;

        try {


            results = consentCoreService.searchDetailedConsents(
                    consentIDs,
                    clientIDs,
                    consentTypes,
                    consentStatuses,
                    userIDs,
                    fromTime,
                    toTime,
                    limit,
                    offset);
            JSONArray searchResults = new JSONArray();

            for (DetailedConsentResource result : results) {
                searchResults.put(ConsentUtils.detailedConsentToJSON(result));
            }

            response.put("Data".toLowerCase(),
                    searchResults);
            count = searchResults.length();
            total = results.size();
        } catch (ConsentManagementException e) {
            throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    e);
        }

        if (limit != null || offset != null) {
            try {
                results = consentCoreService.searchDetailedConsents(consentIDs,
                        clientIDs,
                        consentTypes,
                        consentStatuses,
                        userIDs,
                        fromTime,
                        toTime,
                        null,
                        null);
                total = results.size();
            } catch (ConsentManagementException e) {
                throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage(),
                        e);
            }
        }

        JSONObject metadata = new JSONObject();
        metadata.put("count",
                count);
        metadata.put("offset",
                offset);
        metadata.put("limit",
                limit);
        metadata.put("total",
                total);
        response.put("metadata",
                metadata);

        return results;
    }

    //

    public ConsentResponse consentPost(
            org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.ConsentResource consentResourceDTO,
            String orgInfo, boolean isImplicitAuth, boolean exclusiveConsent)
            throws
            ConsentException,
            ConsentManagementException,
            InvocationTargetException,
            IllegalAccessException {


        //parse consentResource

        ConsentResource consentResource = new ConsentResource();
        BeanUtils.copyProperties(consentResource,
                consentResourceDTO);

        // parse Authorization objects
        ArrayList<AuthorizationResource> authorizations = new ArrayList<>();
        for (AuthResource authResource : consentResourceDTO.getAuthorizationResources()) {
            AuthorizationResource auth = new AuthorizationResource();
            auth.setAuthorizationType(authResource.getAuthorizationType());
            auth.setAuthorizationStatus(authResource.getAuthorizationStatus());
            auth.setUserID(authResource.getUserId());
            authorizations.add(auth);
        }


        DetailedConsentResource result = null;
        if (!exclusiveConsent) {
            result = consentCoreService.createAuthorizableConsentWithBulkAuth(consentResource,
                    authorizations,
                    isImplicitAuth);

        }
        // map detailedConsent to consentResponse
        ConsentResponse consentResponse = new ConsentResponse();
        BeanUtils.copyProperties(consentResponse,
                result);

//        consentResponse.setAuthorizations(result.getAuthorizationResources());
        // loop throuhg authorizations and map to authResource
        if (isImplicitAuth) {
            ArrayList<AuthResponse> authResources = new ArrayList<>();
            for (AuthorizationResource auth : result.getAuthorizationResources()) {
                AuthResponse authResource = new AuthResponse();
                authResource.setAuthorizationType(auth.getAuthorizationType());
                authResource.setAuthorizationStatus(auth.getAuthorizationStatus());
                authResource.setUserId(auth.getUserID());
                authResources.add(authResource);
                authResource.setAuthId(auth.getAuthorizationID());
            }
            consentResponse.setAuthorizationResources(authResources);
        }

        return consentResponse;

//        if (ExclusiveConsent){
//            consentCoreService.createExclusiveConsent(consentResource, authorizations, orgInfo, isImplicitAuth);
//
//        }

    }

    public String consentConsentIdStatusPut(
            String consentID, ConsentStatusUpdateResource consentStatusUpdateResource
            , String orgInfo) throws
            ConsentManagementException {
        try {
            consentCoreService.updateConsentStatusWithImplicitReasonAndUserId(consentID,
                    consentStatusUpdateResource.getStatus(),
                    consentStatusUpdateResource.getReason(),
                    consentStatusUpdateResource.getUserId());
            return "updated";
        } catch (ConsentManagementException e) {
            return e.getMessage();
        }
    }

    public String consentStatusPut(BulkConsentStatusUpdateResource bulkConsentStatusUpdateResource, String orgInfo)
            throws
            ConsentManagementException {
        try {
            consentCoreService.bulkUpdateConsentStatus(bulkConsentStatusUpdateResource.getClientID(),
                    bulkConsentStatusUpdateResource.getStatus(),
                    bulkConsentStatusUpdateResource.getReason(),
                    bulkConsentStatusUpdateResource.getUserId(),
                    bulkConsentStatusUpdateResource.getConsentType());
            return "updated";
        } catch (ConsentManagementException e) {
            return e.getMessage();
        }
    }

    public DetailedConsentResource consentConsentIdPut(
            String consentID, AmendmentResource amendmentResource,
            String orgInfo) throws
            ConsentManagementException {
        try {

            // get authorization resources without authId
            Map<String, AuthorizationResource> newAuthorization = new HashMap<>();

            // get authorization resources with authId
            ArrayList<AuthorizationResource> reAuthorization = new ArrayList<>();

            // get resources without authId
            Map<String, ArrayList<Resource>> newResources = new HashMap<>();

            // get resources with authId
            ArrayList<ConsentMappingResource> reResources = new ArrayList<>();

            for (ReauthorizeResource authResource : amendmentResource.getAuthorizationResources()) {
                if (authResource.getAuthId() != null) {
                    AuthorizationResource auth = new AuthorizationResource();
                    auth.setAuthorizationID(authResource.getAuthId());
                    auth.setAuthorizationType(authResource.getAuthorizationType());
                    auth.setAuthorizationStatus(authResource.getAuthorizationStatus());
                    auth.setUserID(authResource.getUserId());
                    reAuthorization.add(auth);

                    for (Resource resource : authResource.getResources()) {
                        if (resource.getResourceMappingId() != null) {
                            ConsentMappingResource res = new ConsentMappingResource();
                            res.setAccountID(resource.getAccountID());
                            res.setPermission(resource.getPermission());
                            res.setMappingID(resource.getResourceMappingId());
                            res.setMappingStatus(resource.getResourceMappingStatus());
                            res.setAuthorizationID(authResource.getAuthId());
                            reResources.add(res);

                        } else {
                            Resource newRes = new Resource();
                            newRes.setAccountID(resource.getAccountID());
                            newRes.setPermission(resource.getPermission());
                            newRes.setResourceMappingStatus(resource.getResourceMappingStatus());
                            ///  check if the user already exists
                            if (!newResources.containsKey(authResource.getUserId())) {
                                newResources.put(authResource.getUserId(),
                                        new ArrayList<>());
                            } else {
                                newResources.get(authResource.getUserId()).add(newRes);
                            }

                        }
                    }
                } else {
                    AuthorizationResource auth = new AuthorizationResource();
                    auth.setAuthorizationType(authResource.getAuthorizationType());
                    auth.setAuthorizationStatus(authResource.getAuthorizationStatus());
                    auth.setUserID(authResource.getUserId());
                    auth.setAuthorizationID(authResource.getAuthId());
                    auth.setConsentID(consentID);
                    reAuthorization.add(auth);
                }


            }

            Map<String, Object> newEntities = new HashMap<>();
            newEntities.put("AdditionalAuthorizationResources",
                    newAuthorization);
            newEntities.put("AdditionalMappingResources",
                    newResources);

            DetailedConsentResource amendmentResponse = consentCoreService.amendDetailedConsentWithBulkAuthResource(
                    consentID,
                    amendmentResource.getReceipt(),
                    Long.valueOf(amendmentResource.getValidityPeriod()),
                    reAuthorization,
                    amendmentResource.getCurrentStatus(),
                    ConsentUtils.convertToMap(amendmentResource.getConsentAttributes()),
                    amendmentResource.getAuthorizationResources().get(0).getUserId(),
                    newEntities);
            return amendmentResponse;
        } catch (ConsentManagementException e) {
            return null;
        }
    }


    //
//
    public Object consentConsentIdGet(
            String consentID, String orgInfo, boolean isDetailedConsent, String userId,
            boolean isWithAttributes) throws
            ConsentException {


        if (ConsentUtils.isConsentIdValid(consentID)) {
            ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();

            try {

                if (isDetailedConsent) {
                    DetailedConsentResource detailedConsentResource =
                            consentCoreService.getDetailedConsent(consentID);

                    if (detailedConsentResource == null) {
                        log.error("Consent not found");
                        throw new ConsentException(ResponseStatus.NOT_FOUND,
                                "Consent ID Not Found");
                    }
                    return detailedConsentResource;


                } else {
                    ConsentResource consent = null;

                    if (Objects.equals(isWithAttributes,
                            "true")) {
                        consent = consentCoreService.getConsent(consentID,
                                true);

                    } else {
                        consent = consentCoreService.getConsent(consentID,
                                false);
                    }
                    if (consent == null) {
                        log.error("Consent not found");
                        throw new ConsentException(ResponseStatus.NOT_FOUND,
                                "Consent ID Not Found");
                    } else {

                        return consent;

                    }

                }


            } catch (JSONException | ConsentManagementException e) {
                log.error("Error Occurred while handling the request",
                        e);
                throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR,
                        e.getMessage());

            }
        } else {
            log.error("Consent ID  Not Found");
            throw new ConsentException(ResponseStatus.NOT_FOUND,
                    "Consent ID Not Found");
        }
    }

    public String consentConsentIdDelete(String consentID, String orgInfo, String userID) {
        try {
            ConsentResource consentResource = consentCoreService.getConsent(consentID,
                    false);
            consentCoreService.revokeConsent(consentID,
                    consentResource.getCurrentStatus(),
                    userID,
                    false);
            return "Revoked";
        } catch (ConsentManagementException e) {
            return e.getMessage();
        }
    }


    public Object consentConsentIdHistoryGet(
            String consentID, String orgInfo, Boolean detailed, String status, String actionBy, String fromTime,
            String toTime, String statusAuditId) {
        return null;
    }
}
