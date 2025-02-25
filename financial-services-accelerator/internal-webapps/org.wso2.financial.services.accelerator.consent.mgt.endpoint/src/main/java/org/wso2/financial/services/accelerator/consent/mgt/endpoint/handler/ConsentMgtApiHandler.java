package org.wso2.financial.services.accelerator.consent.mgt.endpoint.handler;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.financial.services.accelerator.common.exception.ConsentManagementException;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.DetailedConsentResource;

import org.wso2.financial.services.accelerator.consent.mgt.endpoint.constants.ConsentConstant;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto.ConsentMgtDTO;
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
