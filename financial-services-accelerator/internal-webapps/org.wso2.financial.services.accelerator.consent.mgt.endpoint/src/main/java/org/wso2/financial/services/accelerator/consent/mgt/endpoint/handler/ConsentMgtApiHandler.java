package org.wso2.financial.services.accelerator.consent.mgt.endpoint.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.financial.services.accelerator.common.exception.ConsentManagementException;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.DetailedConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto.ConsentMgtDTO;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ConsentException;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ConsentExtensionUtils;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ResponseStatus;
import org.wso2.financial.services.accelerator.consent.mgt.extensions.admin.utils.ConsentAdminUtils;
import org.wso2.financial.services.accelerator.consent.mgt.service.impl.ConsentCoreServiceImpl;

import java.util.ArrayList;
import java.util.Map;

/**
 * ConsentMgtApiHandler.
 */
public class ConsentMgtApiHandler {
    private static final Log log = LogFactory.getLog(ConsentMgtApiHandler.class);

    public ConsentMgtApiHandler() {
    }


    public void handleSearch(ConsentMgtDTO consentAdminData) throws ConsentException {
        JSONObject response = new JSONObject();
        Long fromTime = null;
        Long toTime = null;
        Integer limit = null;
        Integer offset = null;
        Map queryParams = consentAdminData.getQueryParams();
        ArrayList<String> consentIDs = ConsentAdminUtils.getArrayListFromQueryParam(
                ConsentAdminUtils.validateAndGetQueryParam(queryParams, "consentIds"));
        ArrayList<String> clientIDs = ConsentAdminUtils.getArrayListFromQueryParam(
                ConsentAdminUtils.validateAndGetQueryParam(queryParams, "clientIds"));
        ArrayList<String> consentTypes = ConsentAdminUtils.getArrayListFromQueryParam(
                ConsentAdminUtils.validateAndGetQueryParam(queryParams, "consentTypes"));
        ArrayList<String> consentStatuses = ConsentAdminUtils.getArrayListFromQueryParam(
                ConsentAdminUtils.validateAndGetQueryParam(queryParams, "consentStatuses"));
        ArrayList<String> userIDs = ConsentAdminUtils.getArrayListFromQueryParam(
                ConsentAdminUtils.validateAndGetQueryParam(queryParams, "userIds"));

        try {
            long fromTimeValue = ConsentAdminUtils.getLongFromQueryParam(
                    ConsentAdminUtils.validateAndGetQueryParam(queryParams, "fromTime"));
            fromTime = fromTimeValue == 0L ? null : fromTimeValue;
        } catch (NumberFormatException var23) {
            log.warn("Number format incorrect in search for parameter fromTime. Ignoring parameter");
        }

        try {
            long toTimeValue = ConsentAdminUtils.getLongFromQueryParam(
                    ConsentAdminUtils.validateAndGetQueryParam(queryParams, "toTime"));
            toTime = toTimeValue == 0L ? null : toTimeValue;
        } catch (NumberFormatException var22) {
            log.warn("Number format incorrect in search for parameter toTime. Ignoring parameter");
        }

        try {
            int limitValue = ConsentAdminUtils.getIntFromQueryParam(ConsentAdminUtils.validateAndGetQueryParam(
                    queryParams, "limit"));
            limit = limitValue == 0 ? null : limitValue;
        } catch (NumberFormatException var21) {
            log.warn("Number format incorrect in search for parameter limit. Ignoring parameter");
        }

        try {
            int offsetValue = ConsentAdminUtils.getIntFromQueryParam(
                    ConsentAdminUtils.validateAndGetQueryParam(queryParams, "offset"));
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
                searchResults.put(ConsentAdminUtils.detailedConsentToJSON(result));
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


    public void handleGetConsent(ConsentMgtDTO consentMgtDTO) throws ConsentException {
        if (StringUtils.isEmpty(consentMgtDTO.getClientId())) {
            log.error("Client ID missing in the request.");
            throw new ConsentException(ResponseStatus.BAD_REQUEST, "Client ID missing in the request.");
        } else if (consentMgtDTO.getRequestPath() == null) {
            log.error("Resource Path Not Found");
            throw new ConsentException(ResponseStatus.BAD_REQUEST, "Resource Path Not Found");
        } else {
            String consentId = consentMgtDTO.getRequestPath();
            if (ConsentExtensionUtils.isConsentIdValid(consentId)) {
                try {
                    ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();
                    ConsentResource consent = consentCoreService.getConsent(consentId, false);
                    if (consent == null) {
                        log.error("Consent not found");
                        throw new ConsentException(ResponseStatus.BAD_REQUEST, "Consent not found");
                    } else {

                        JSONObject receiptJSON = new JSONObject(consent.getReceipt());
                        consentMgtDTO.setResponsePayload(ConsentExtensionUtils.getInitiationRetrievalResponse(
                                receiptJSON, consent));
                        consentMgtDTO.setResponseStatus(ResponseStatus.OK);

                    }
                } catch (JSONException | ConsentManagementException e) {
                    log.error("Error Occurred while handling the request", e);
                    throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, "Error Occurred while" +
                            " handling the request");
                }
            } else {
                log.error("Invalid consent Id found");
                throw new ConsentException(ResponseStatus.BAD_REQUEST, "Invalid consent Id found");
            }

        }
    }

    public void handleDelete(ConsentMgtDTO consentManageData) throws ConsentException {

        if (consentManageData.getRequestPath() == null) {
            log.error("Resource Path Not Found");
            throw new ConsentException(ResponseStatus.BAD_REQUEST, "Resource Path Not Found");
        } else {
            String consentId = consentManageData.getRequestPath();
            if (ConsentExtensionUtils.isConsentIdValid(consentId)) {
                try {
                    ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();
                    ConsentResource consentResource = consentCoreService.getConsent(consentId, false);
                    if (consentResource == null) {
                        log.error("Consent not found");
                        throw new ConsentException(ResponseStatus.BAD_REQUEST, "Consent not found");
                    } else {
                        String consentType = ConsentExtensionUtils.getConsentType(
                                consentManageData.getRequestPath());
                        if (!"Revoked".equals(consentResource.getCurrentStatus()) && !"Rejected".equals(
                                consentResource.getCurrentStatus())) {
                            boolean shouldRevokeTokens = "Authorised".equals(consentResource.getCurrentStatus());
                            boolean success = consentCoreService.revokeConsent(consentId,
                                    "Revoked", (String) null, shouldRevokeTokens);
                            if (!success) {
                                log.error("Token revocation unsuccessful");
                                throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR,
                                        "Token revocation unsuccessful");
                            } else {
                                consentManageData.setResponseStatus(ResponseStatus.NO_CONTENT);
                            }
                        } else {
                            log.error("Consent is already in revoked or rejected state");
                            throw new ConsentException(ResponseStatus.BAD_REQUEST, "Consent is already in " +
                                    "revoked or rejected state");
                        }
                    }
                } catch (ConsentManagementException e) {
                    log.error(e.getMessage().replaceAll("[\r\n]+", ""));
                    throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage().replaceAll(
                            "[\r\n]+", ""));
                }
            } else {
                log.error("Request Path Invalid");
                throw new ConsentException(ResponseStatus.BAD_REQUEST, "Request Path Invalid");
            }

        }
    }

    public void handleUpdateConsentStatus(ConsentMgtDTO consentMgtDTO) {

        if (consentMgtDTO.getRequestPath() == null) {
            log.error("Resource Path Not Found");
            throw new ConsentException(ResponseStatus.BAD_REQUEST, "Resource Path Not Found");
        } else {
            String consentId = consentMgtDTO.getRequestPath();
            if (ConsentExtensionUtils.isConsentIdValid(consentId)) {
                try {
                    ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();
                    ConsentResource consentResource = consentCoreService.getConsent(consentId, false);
                    if (consentResource == null) {
                        log.error("Consent not found");
                        throw new ConsentException(ResponseStatus.BAD_REQUEST, "Consent not found");
                    } else {
                        String consentType = ConsentExtensionUtils.getConsentType(
                                consentMgtDTO.getRequestPath());
                        if (!"Revoked".equals(consentResource.getCurrentStatus()) && !"Rejected".equals(
                                consentResource.getCurrentStatus())) {
                            boolean shouldRevokeTokens = "Authorised".equals(consentResource.getCurrentStatus());
                            boolean success = consentCoreService.revokeConsent(consentId,
                                    "Revoked", (String) null, shouldRevokeTokens);
                            if (!success) {
                                log.error("Token revocation unsuccessful");
                                throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR,
                                        "Token revocation unsuccessful");
                            } else {
                                consentMgtDTO.setResponseStatus(ResponseStatus.NO_CONTENT);
                            }
                        } else {
                            log.error("Consent is already in revoked or rejected state");
                            throw new ConsentException(ResponseStatus.BAD_REQUEST, "Consent is already in " +
                                    "revoked or rejected state");
                        }
                    }
                } catch (ConsentManagementException e) {
                    log.error(e.getMessage().replaceAll("[\r\n]+", ""));
                    throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, e.getMessage().replaceAll(
                            "[\r\n]+", ""));
                }
            } else {
                log.error("Request Path Invalid");
                throw new ConsentException(ResponseStatus.BAD_REQUEST, "Request Path Invalid");
            }

        }
    }
}
