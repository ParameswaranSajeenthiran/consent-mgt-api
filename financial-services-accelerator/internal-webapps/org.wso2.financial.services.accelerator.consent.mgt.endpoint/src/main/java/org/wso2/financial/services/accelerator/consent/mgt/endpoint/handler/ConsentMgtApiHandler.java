package org.wso2.financial.services.accelerator.consent.mgt.endpoint.handler;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.financial.services.accelerator.common.exception.ConsentManagementException;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.AuthorizationResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.DetailedConsentResource;

import org.wso2.financial.services.accelerator.consent.mgt.endpoint.constants.ConsentConstant;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto.*;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.exception.ConsentException;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ConsentUtils;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ResponseStatus;
import org.wso2.financial.services.accelerator.consent.mgt.service.impl.ConsentCoreServiceImpl;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * ConsentMgtApiHandler.
 */
public class ConsentMgtApiHandler {
    private static final Log log = LogFactory.getLog(ConsentMgtApiHandler.class);
    private ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();

    public ConsentMgtApiHandler() {
    }

    public void handleCreateConsent(ConsentMgtDTO consentMgtDTO) {


    }

    //

    public void handleSearch(ConsentMgtDTO consentAdminData) throws ConsentException {
        JSONObject response = new JSONObject();
        Long fromTime = null;
        Long toTime = null;
        Integer limit = null;
        Integer offset = null;
        Map queryParams = consentAdminData.getQueryParams();
        ArrayList<String> consentIDs = ConsentUtils.getArrayListFromQueryParam(
                ConsentUtils.validateAndGetQueryParam(queryParams, "consentIds"));
        ArrayList<String> clientIDs = ConsentUtils.getArrayListFromQueryParam(
                ConsentUtils.validateAndGetQueryParam(queryParams, "clientIds"));
        ArrayList<String> consentTypes = ConsentUtils.getArrayListFromQueryParam(
                ConsentUtils.validateAndGetQueryParam(queryParams, "consentTypes"));
        ArrayList<String> consentStatuses = ConsentUtils.getArrayListFromQueryParam(
                ConsentUtils.validateAndGetQueryParam(queryParams, "consentStatuses"));
        ArrayList<String> userIDs = ConsentUtils.getArrayListFromQueryParam(
                ConsentUtils.validateAndGetQueryParam(queryParams, "userIds"));

        try {
            long fromTimeValue = ConsentUtils.getLongFromQueryParam(
                    ConsentUtils.validateAndGetQueryParam(queryParams, "fromTime"));
            fromTime = fromTimeValue == 0L ? null : fromTimeValue;
        } catch (NumberFormatException var23) {
            log.warn("Number format incorrect in search for parameter fromTime. Ignoring parameter");
        }

        try {
            long toTimeValue = ConsentUtils.getLongFromQueryParam(
                    ConsentUtils.validateAndGetQueryParam(queryParams, "toTime"));
            toTime = toTimeValue == 0L ? null : toTimeValue;
        } catch (NumberFormatException var22) {
            log.warn("Number format incorrect in search for parameter toTime. Ignoring parameter");
        }

        try {
            int limitValue = ConsentUtils.getIntFromQueryParam(ConsentUtils.validateAndGetQueryParam(
                    queryParams, "limit"));
            limit = limitValue == 0 ? null : limitValue;
        } catch (NumberFormatException var21) {
            log.warn("Number format incorrect in search for parameter limit. Ignoring parameter");
        }

        try {
            int offsetValue = ConsentUtils.getIntFromQueryParam(
                    ConsentUtils.validateAndGetQueryParam(queryParams, "offset"));
            offset = offsetValue == 0 ? null : offsetValue;
        } catch (NumberFormatException var20) {
            log.warn("Number format incorrect in search for parameter limit. Ignoring parameter");
        }

        int total = 0;
        ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();


        int count;
        try {

            ArrayList<DetailedConsentResource> results = consentCoreService.searchDetailedConsents(
                    consentIDs, clientIDs, consentTypes, consentStatuses, userIDs, fromTime, toTime, limit, offset);
            JSONArray searchResults = new JSONArray();

            for (DetailedConsentResource result : results) {
                searchResults.put(ConsentUtils.detailedConsentToJSON(result));
            }

            response.put("Data".toLowerCase(), searchResults);
            count = searchResults.length();
            total = results.size();
        } catch (ConsentManagementException e) {
            throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

        if (limit != null || offset != null) {
            try {
                ArrayList<DetailedConsentResource> results = consentCoreService.searchDetailedConsents(consentIDs,
                        clientIDs, consentTypes, consentStatuses, userIDs, fromTime, toTime, (Integer) null,
                        (Integer) null);
                total = results.size();
            } catch (ConsentManagementException e) {
                throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
            }
        }

        JSONObject metadata = new JSONObject();
        metadata.put("count", count);
        metadata.put("offset", offset);
        metadata.put("limit", limit);
        metadata.put("total", total);
        response.put("metadata", metadata);
        consentAdminData.setResponseStatus(ResponseStatus.OK);
        consentAdminData.setResponsePayload(response);
    }

    public ConsentResponse handleCreateConsent(ConsentResourceDTO consentResourceDTO, String orgInfo, boolean isImplicitAuth, boolean ExclusiveConsent) throws ConsentException, ConsentManagementException {



        //parse consentResource
        ConsentResource consentResource = new ConsentResource();
        consentResource.setClientID(consentResourceDTO.getClientId());
        consentResource.setConsentType(consentResourceDTO.getConsentType());
        consentResource.setCurrentStatus(consentResourceDTO.getConsentStatus());
        consentResource.setReceipt(consentResourceDTO.getReceipt().toString());
        consentResource.setConsentAttributes(consentResourceDTO.getConsentAttributes());
        consentResource.setRecurringIndicator(consentResourceDTO.getRecurringIndicator());

        // parse Authorization objects
        ArrayList<AuthorizationResource> authorizations = new ArrayList<>();
        for (AuthResource authResource : consentResourceDTO.getAuthorizations()) {
            AuthorizationResource auth = new AuthorizationResource();
            auth.setAuthorizationType(authResource.getAuthType());
            auth.setAuthorizationStatus(authResource.getAuthStatus());
            auth.setUserID(authResource.getUserId());
            authorizations.add(auth);
        }



        DetailedConsentResource result = null;
        if (!ExclusiveConsent){
            result=consentCoreService.createAuthorizableConsentWithBulkAuth(consentResource,authorizations,isImplicitAuth);

        }
        // map detailedConsent to consentResponse
        ConsentResponse consentResponse = new ConsentResponse();
        consentResponse.setConsentId(result.getConsentID());
        consentResponse.setConsentStatus(result.getCurrentStatus());
        consentResponse.setConsentType(result.getConsentType());
        consentResponse.setReceipt(result.getReceipt());
        consentResponse.setConsentAttributes(result.getConsentAttributes());
        consentResponse.setCreatedTime((int) result.getCreatedTime());
        consentResponse.setValidityTime((int) result.getValidityPeriod());
        consentResponse.setClientId(result.getClientID());
//        consentResponse.setAuthorizations(result.getAuthorizationResources());
        // loop throuhg authorizations and map to authResource
        if (isImplicitAuth){
            ArrayList<AuthResponse> authResources = new ArrayList<>();
            for (AuthorizationResource auth : result.getAuthorizationResources()) {
                AuthResponse authResource = new AuthResponse();
                authResource.setAuthType(auth.getAuthorizationType());
                authResource.setAuthStatus(auth.getAuthorizationStatus());
                authResource.setUserId(auth.getUserID());
                authResources.add(authResource);
                authResource.setAuthId(auth.getAuthorizationID());
            }
            consentResponse.setAuthorizations(authResources);
        }

        return consentResponse;

//        if (ExclusiveConsent){
//            consentCoreService.createExclusiveConsent(consentResource, authorizations, orgInfo, isImplicitAuth);
//
//        }

    }

    public String updateConsentStatus(String consentId, ConsentStatusUpdateResource consentStatusUpdateResource) throws ConsentManagementException {
        try{
            consentCoreService.updateConsentStatusWithImplicitReasonAndUserId(consentId, consentStatusUpdateResource.getStatus(), consentStatusUpdateResource.getReason(), consentStatusUpdateResource.getUserId());
            return "updated";
        }catch (ConsentManagementException e){
            return e.getMessage();
        }
    }

    public String bulkUpdateConsentStatus(BulkConsentStatusUpdateResource bulkConsentStatusUpdateResource) throws ConsentManagementException {
        try{
            consentCoreService.bulkUpdateConsentStatus(bulkConsentStatusUpdateResource.getClientId(), bulkConsentStatusUpdateResource.getStatus(), bulkConsentStatusUpdateResource.getReason(), bulkConsentStatusUpdateResource.getUserId(), bulkConsentStatusUpdateResource.getConsentType());
            return "updated";
        }catch (ConsentManagementException e){
            return e.getMessage();
        }
    }

    public AmendmentResponse amendConsent(String consentId, AmendmentResource amendmentResource) throws ConsentManagementException {
        try{

            DetailedConsentResource result = consentCoreService.amendConsent(consentId, amendmentResource.getAmendmentType(), amendmentResource.getAmendment(), amendmentResource.getAmendmentReason());
//            AmendmentResponse amendmentResponse = new AmendmentResponse();
//            amendmentResponse.setAmendmentId(result.getAmendmentID());
//            amendmentResponse.setAmendmentType(result.getAmendmentType());
//            amendmentResponse.setAmendment(result.getAmendment());
//            amendmentResponse.setAmendmentReason(result.getAmendmentReason());
//            amendmentResponse.setCreatedTime((int) result.getCreatedTime());
            return amendmentResponse;
        }catch (ConsentManagementException e){
            return null;
        }
    }


//
//
    public void handleGetConsent(ConsentMgtDTO consentMgtDTO) throws ConsentException {
        if (consentMgtDTO.getPathParameters().get("consentId") == null) {
            log.error("Consent Id  Not Found");
            throw new ConsentException(ResponseStatus.NOT_FOUND, "Consent Id Not Found");

        } else {
            String consentId = ConsentUtils.validateAndGetPathParam(consentMgtDTO.getPathParameters(), "consentId");

            if (ConsentUtils.isConsentIdValid(consentId)) {
                ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();

                try {
                    Map queryParams = consentMgtDTO.getQueryParams();

                    String isDetailedConsent = ConsentUtils.validateAndGetQueryParam(queryParams,
                            ConsentConstant.DETAILED_CONSENT);
                    String isWithAttributes = ConsentUtils.validateAndGetQueryParam(queryParams,
                            ConsentConstant.WITH_ATTRIBUTES);


                    if (Objects.equals(isDetailedConsent, "true")) {
                        DetailedConsentResource detailedConsentResource =
                                consentCoreService.getDetailedConsent(consentId);

                        if (detailedConsentResource == null) {
                            log.error("Consent not found");
                            throw new ConsentException(ResponseStatus.NOT_FOUND, "Consent Id Not Found");
                        }


                        consentMgtDTO.setResponsePayload(ConsentUtils.detailedConsentToJSON(detailedConsentResource));
                        consentMgtDTO.setResponseStatus(ResponseStatus.OK);


                    } else {
                        ConsentResource consent = null;

                        if (Objects.equals(isWithAttributes, "true")) {
                            consent = consentCoreService.getConsent(consentId, true);

                        } else {
                            consent = consentCoreService.getConsent(consentId, false);

                        }
                        if (consent == null) {
                            log.error("Consent not found");
                            throw new ConsentException(ResponseStatus.NOT_FOUND, "Consent Id Not Found");
                        } else {


                            consentMgtDTO.setResponsePayload(ConsentUtils.consentResourceToJSON(consent));
                            consentMgtDTO.setResponseStatus(ResponseStatus.OK);

                        }

                    }


                } catch (JSONException | ConsentManagementException e) {
                    log.error("Error Occurred while handling the request", e);
                    throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage());

                }
            }

        }


//    public void handleUpdateConsentStatus(ConsentMgtDTO consentMgtDTO) {
//
//        if (consentMgtDTO.getRequestPath() == null) {
//            log.error("Resource Path Not Found");
//            throw new ConsentException(ResponseStatus.BAD_REQUEST, "Resource Path Not Found");
//        } else {
//            String consentId = consentMgtDTO.getRequestPath();
//            if (ConsentExtensionUtils.isConsentIdValid(consentId)) {
//                try {
//                    ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();
//                    ConsentResource consentResource = consentCoreService.getConsent(consentId, false);
//                    if (consentResource == null) {
//                        log.error("Consent not found");
//                        throw new ConsentException(ResponseStatus.BAD_REQUEST, "Consent not found");
//                    } else {
//                        String consentType = ConsentExtensionUtils.getConsentType(
//                                consentMgtDTO.getRequestPath());
//                        if (!"Revoked".equals(consentResource.getCurrentStatus()) && !"Rejected".equals(
//                                consentResource.getCurrentStatus())) {
//                            boolean shouldRevokeTokens = "Authorised".equals(consentResource.getCurrentStatus());
//                            boolean success = consentCoreService.revokeConsent(consentId,
//                                    "Revoked", (String) null, shouldRevokeTokens);
//                            if (!success) {
//                                log.error("Token revocation unsuccessful");
//                                throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR,
//                                        "Token revocation unsuccessful");
//                            } else {
//                                consentMgtDTO.setResponseStatus(ResponseStatus.NO_CONTENT);
//                            }
//                        } else {
//                            log.error("Consent is already in revoked or rejected state");
//                            throw new ConsentException(ResponseStatus.BAD_REQUEST, "Consent is already in " +
//                                    "revoked or rejected state");
//                        }
//                    }
//                } catch (ConsentManagementException e) {
//                    log.error(e.getMessage().replaceAll("[\r\n]+", ""));
//                    throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage().replaceAll(
//                            "[\r\n]+", ""));
//                }
//            } else {
//                log.error("Request Path Invalid");
//                throw new ConsentException(ResponseStatus.BAD_REQUEST, "Request Path Invalid");
//            }
//
//        }
//    }
    }

    public void handleUpdateConsentStatus(ConsentMgtDTO consentMgtDTO) {

        if (consentMgtDTO.getPathParameters().get("consentId") == null) {
            log.error("Consent Id  Not Found");
            throw new ConsentException(ResponseStatus.NOT_FOUND, "Consent Id Not Found");

        } else {
            String consentId = ConsentUtils.validateAndGetPathParam(consentMgtDTO.getPathParameters(), "consentId");

            if (ConsentUtils.isConsentIdValid(consentId)) {
                ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();

                // get payload
                JSONObject payload = (JSONObject) consentMgtDTO.getPayload();

//                consentCoreService.updateConsentStatus(consentId, payload.getString("status"));

            }
        }

    }
}
