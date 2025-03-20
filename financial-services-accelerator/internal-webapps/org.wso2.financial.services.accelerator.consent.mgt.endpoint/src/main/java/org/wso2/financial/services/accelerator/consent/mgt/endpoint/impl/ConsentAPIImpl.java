package org.wso2.financial.services.accelerator.consent.mgt.endpoint.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.wso2.financial.services.accelerator.consent.mgt.dao.exceptions.ConsentMgtException;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.AuthorizationResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentHistoryResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentMappingResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentStatusAuditRecord;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.DetailedConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.constants.ConsentConstant;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.AmendmentResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.AuthResponse;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.AuthorizationResourceDTO;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.BulkConsentStatusUpdateResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.ConsentResourceDTO;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.ConsentResponse;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.ConsentStatusUpdateResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.ReauthorizeResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.Resource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.model.ResourcePermission;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ConsentUtils;
import org.wso2.financial.services.accelerator.consent.mgt.service.impl.ConsentCoreServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.ws.rs.core.Response;


/**
 * ConsentAPIImpl.
 */
public class ConsentAPIImpl {
    private static final Log log = LogFactory.getLog(ConsentAPIImpl.class);
    private ConsentCoreServiceImpl consentCoreService;

    public ConsentAPIImpl() {
        consentCoreService = new ConsentCoreServiceImpl();
    }

    public void setConsentCoreService(ConsentCoreServiceImpl consentCoreService) {
        this.consentCoreService = consentCoreService;
    }


    public Response consentGet(
            String orgInfo,
            String consentType, String consentStatus
            , String userID, long fromTimeValue, long toTimeValue, int limitValue, int offsetValue) {


        try {


            ////////////// initialize the search query //////////////
            ArrayList<String> consentIDs = new ArrayList<>();
            ArrayList<String> clientIDs = new ArrayList<>();
            ArrayList<String> consentTypes = new ArrayList<>();
            ArrayList<String> consentStatuses = new ArrayList<>();
            ArrayList<String> userIDs = new ArrayList<>();
            if (consentType != null) {
                Collections.addAll(consentTypes, consentType.split(","));
            }
            if (consentStatus != null) {
                Collections.addAll(consentStatuses, consentStatus.split(","));
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

            ////////////// service call //////////////
            ArrayList<DetailedConsentResource> results;
            results = consentCoreService.searchDetailedConsents(
                    orgInfo,
                    consentIDs,
                    clientIDs,
                    consentTypes,
                    consentStatuses,
                    userIDs,
                    fromTime,
                    toTime,
                    limit,
                    offset);


            if (limit != null || offset != null) {
                results = consentCoreService.searchDetailedConsents(orgInfo, consentIDs,
                        clientIDs,
                        consentTypes,
                        consentStatuses,
                        userIDs,
                        fromTime,
                        toTime,
                        null,
                        null);
            }

            ////////////// build response //////////////
            ArrayList<ConsentResponse> consentResponses = new ArrayList<>();

            for (DetailedConsentResource result : results) {
                ConsentResponse consentResponse = new ConsentResponse();
                ConsentUtils.copyPropertiesToConsentResourceResponse(consentResponse, result,
                        false, false ,true);

                // build AuthorizationResource objects
                ArrayList<AuthResponse> authResponses = new ArrayList<>();

                // response should contain resources within each AuthorizationResource object

                // get consent mapping resources for each AuthorizationResource
                Map<String, ArrayList<ConsentMappingResource>> consentMappingResources = new HashMap<>();
                for (ConsentMappingResource consentMappingResource : result.getConsentMappingResources()) {
                    if (!consentMappingResources.containsKey(consentMappingResource.getAuthorizationID())) {
                        consentMappingResources.put(consentMappingResource.getAuthorizationID(),
                                new ArrayList<>());
                    }
                    consentMappingResources.get(consentMappingResource.getAuthorizationID())
                            .add(consentMappingResource);
                }


                for (AuthorizationResource authorizationResource : result.getAuthorizationResources()) {
                    AuthResponse authorizationResourceResponse = new AuthResponse();
                    ConsentUtils.copyPropertiesToAuthorizationResourceResponse(authorizationResourceResponse,
                            authorizationResource);

                    ArrayList<Resource> resources = new ArrayList<>();

                    //add the consent mapping resources to the resources
                    if (consentMappingResources.containsKey(authorizationResource.getAuthorizationID())) {
                        for (ConsentMappingResource consentMappingResource : consentMappingResources.get(
                                authorizationResource.getAuthorizationID())) {
                            Resource resource = new Resource();
                            ConsentUtils.copyPropertiesToConsentMappingResourceResponse(resource,
                                    consentMappingResource);
                            resources.add(resource);
                        }
                    }
                    authorizationResourceResponse.setResources(resources);
                    authResponses.add(authorizationResourceResponse);
                }
                consentResponse.setAuthorizationResources(authResponses);
                consentResponses.add(consentResponse);


            }

            return Response.ok().entity(consentResponses).build();
        } catch (ConsentMgtException e) {
            return handleConsentMgtException(e);
        }
    }


    public Response consentPost(
            ConsentResourceDTO consentResourceDTO,
            String orgInfo, boolean isImplicitAuth, boolean exclusiveConsent) {

        try {
            //////////////  handle request //////////////
            ConsentResource consentResource = new ConsentResource();
            ConsentUtils.copyPropertiesToConsentResource(consentResource, consentResourceDTO);
            consentResource.setOrgID(orgInfo);

            // if consentFrequency is an attribute in consentAttributes, then set it to the consentResource
            Map<String, String> consentAttributes =
                    ConsentUtils.convertToMap(consentResourceDTO.getConsentAttributes());
            if (!consentAttributes.isEmpty()) {
                if (consentAttributes.containsKey("consentFrequency")) {
                    try {
                        consentResource.setConsentFrequency(
                                Integer.parseInt(consentAttributes.get("consentFrequency")));

                    } catch (NumberFormatException e) {
                        throw new ConsentMgtException(Response.Status.BAD_REQUEST, "Invalid Number Format");
                    }
                } else {
                    consentResource.setConsentFrequency(0);
                }
            }

            // build AuthorizationResource objects
            ArrayList<AuthorizationResource> authorizations = new ArrayList<>();

            if (isImplicitAuth) {
                for (AuthorizationResourceDTO authorizationResourceDTO :
                        consentResourceDTO.getAuthorizationResources()) {
                    AuthorizationResource authorizationResource = new AuthorizationResource();
                    ConsentUtils.copyPropertiesToAuthorizationResource(authorizationResource, authorizationResourceDTO);
                    ArrayList<ConsentMappingResource> consentMappingResources = new ArrayList<>();
                    for (String resource : authorizationResourceDTO.getResources()) {
                        ConsentMappingResource res = new ConsentMappingResource();
                        res.setResource(resource);
                        res.setMappingStatus("active");
                        consentMappingResources.add(res);
                    }
                    authorizationResource.setConsentMappingResource(consentMappingResources);
                    authorizations.add(authorizationResource);

                }
            }

            ////////////// Service call //////////////
            DetailedConsentResource result = null;

            if (!exclusiveConsent) {
                result = consentCoreService.createAuthorizableConsentWithBulkAuth(consentResource,
                        authorizations,
                        isImplicitAuth);

            } else {
                //TODO : Implement exclusive consent
                throw new ConsentMgtException(Response.Status.NOT_IMPLEMENTED, "Exclusive Consent Not yet " +
                        "implemented");
            }

            //////////////  build response //////////////
            ConsentResponse consentResponse = new ConsentResponse();
            ConsentUtils.copyPropertiesToConsentResourceResponse(consentResponse, result, isImplicitAuth,
                    isImplicitAuth, true);

            return Response.status(Response.Status.CREATED).entity(consentResponse).build();
        } catch (ConsentMgtException e) {
            return handleConsentMgtException(e);
        }
    }

    public Response consentConsentIdStatusPut(
            String consentID, ConsentStatusUpdateResource consentStatusUpdateResource
            , String orgInfo) {
        try {
            consentCoreService.updateConsentStatusWithImplicitReasonAndUserId(consentID,
                    consentStatusUpdateResource.getStatus(),
                    consentStatusUpdateResource.getReason(),
                    consentStatusUpdateResource.getUserId());
            return Response.ok().entity("Status Updated").build();
        } catch (ConsentMgtException e) {
            return handleConsentMgtException(e);
        }
    }

    public Response consentStatusPut(BulkConsentStatusUpdateResource bulkConsentStatusUpdateResource, String orgInfo) {
        try {
            consentCoreService.bulkUpdateConsentStatus(
                    orgInfo,
                    bulkConsentStatusUpdateResource.getClientID(),
                    bulkConsentStatusUpdateResource.getStatus(),
                    bulkConsentStatusUpdateResource.getReason(),
                    bulkConsentStatusUpdateResource.getUserId(),
                    bulkConsentStatusUpdateResource.getConsentType(),
                    bulkConsentStatusUpdateResource.getApplicableStatusesForStateChange());

            return Response.ok().entity("Status Updated").build();
        } catch (ConsentMgtException e) {
            return handleConsentMgtException(e);
        }
    }

    public Response consentConsentIdPut(
            String consentID, AmendmentResource amendmentResource,
            String orgInfo) {

        try {
            ////////////// handle request //////////////
            // get authorization resources without authId
            ArrayList<AuthorizationResource> newAuthorization = new ArrayList<>();

            // get authorization resources with authId
            ArrayList<AuthorizationResource> reAuthorization = new ArrayList<>();

            // get resources without authId
            Map<String, ArrayList<ConsentMappingResource>> newResources = new HashMap<>();

            // get resources with authId
            ArrayList<ConsentMappingResource> reResources = new ArrayList<>();

            // iterate through authorization resources and build new and existing auth and resource objects
            for (ReauthorizeResource authResourceDTO : amendmentResource.getAuthorizationResources()) {
                //existing auth
                if (authResourceDTO.getAuthorizationId() != null) {
                    AuthorizationResource auth = new AuthorizationResource();
                    ConsentUtils.copyPropertiesToAuthorizationResource(auth, authResourceDTO);
                    auth.setConsentID(consentID);
                    reAuthorization.add(auth);

                } else {
                    // new auth
                    AuthorizationResource auth = new AuthorizationResource();
                    ConsentUtils.copyPropertiesToAuthorizationResource(auth, authResourceDTO);
                    auth.setConsentID(consentID);
                    newAuthorization.add(auth);
                }
            }

            // TODO : // action by ??
            String userID;
            if (amendmentResource.getAuthorizationResources().isEmpty()) {
                userID = null;
            } else {
                userID = amendmentResource.getAuthorizationResources().get(0).getUserId();
            }

            ////////////// service call //////////////
            DetailedConsentResource amendmentResponse = consentCoreService.amendDetailedConsentWithBulkAuthResource(
                    orgInfo,
                    consentID,
                    amendmentResource.getReceipt(),
                    Long.valueOf(amendmentResource.getValidityPeriod()),
                    reAuthorization,
                    amendmentResource.getCurrentStatus(),
                    ConsentUtils.convertToMap(amendmentResource.getConsentAttributes()),
                    userID,
                    newAuthorization);


            ////////////// build response //////////////
            ConsentResponse consentResponse = new ConsentResponse();
            ConsentUtils.copyPropertiesToConsentResourceResponse(consentResponse, amendmentResponse,
                    false, false, true);

            // build authorization resources
            ArrayList<AuthResponse> authResponses = new ArrayList<>();

            // response should contain resources within each AuthorizationResource object

            // get consent mapping resources for each AuthorizationResource
            Map<String, ArrayList<ConsentMappingResource>> consentMappingResources = new HashMap<>();
            for (ConsentMappingResource consentMappingResource : amendmentResponse.getConsentMappingResources()) {
                if (!consentMappingResources.containsKey(consentMappingResource.getAuthorizationID())) {
                    consentMappingResources.put(consentMappingResource.getAuthorizationID(),
                            new ArrayList<>());
                }
                consentMappingResources.get(consentMappingResource.getAuthorizationID()).add(consentMappingResource);
            }


            for (AuthorizationResource authorizationResource : amendmentResponse.getAuthorizationResources()) {
                AuthResponse authorizationResourceResponse = new AuthResponse();
                ConsentUtils.copyPropertiesToAuthorizationResourceResponse(authorizationResourceResponse,
                        authorizationResource);
                ArrayList<Resource> resources = new ArrayList<>();

                //add the consent mapping resources to the resources
                if (consentMappingResources.containsKey(authorizationResource.getAuthorizationID())) {
                    for (ConsentMappingResource consentMappingResource : consentMappingResources.get(
                            authorizationResource.getAuthorizationID())) {
                        Resource resource = new Resource();
                        ConsentUtils.copyPropertiesToConsentMappingResourceResponse(resource, consentMappingResource);
                        resources.add(resource);
                    }
                }
                authorizationResourceResponse.setResources(resources);
                authResponses.add(authorizationResourceResponse);
            }

            consentResponse.setAuthorizationResources(authResponses);

            return Response.ok().entity(consentResponse).build();

        } catch (ConsentMgtException e) {
            return handleConsentMgtException(e);
        }

    }


    //
//
    public Response consentConsentIdGet(
            String consentID, String orgInfo, boolean withAuthorizationResources, String userId,
            boolean isWithAttributes) throws
            ConsentMgtException {

        try {


            if (ConsentUtils.isConsentIdValid(consentID)) {

                try {
                    if (withAuthorizationResources & isWithAttributes) {
                        ////////////// Service call //////////////
                        DetailedConsentResource detailedConsentResource =
                                consentCoreService.getDetailedConsent(consentID);

                        if (!detailedConsentResource.getOrgID().equals(orgInfo)) {
                            log.error("OrgInfo does not match");
                            throw new ConsentMgtException(Response.Status.BAD_REQUEST,
                                    "OrgInfo does not match, please provide the correct OrgInfo");
                        }

                        //////////////  build Response  //////////////
                        ConsentResponse consentResponse = new ConsentResponse();
                        ConsentUtils.copyPropertiesToConsentResourceResponse(consentResponse,
                                detailedConsentResource,
                                false, false, true);
                        ArrayList<AuthResponse> authResponses = new ArrayList<>();

                        // response should contain resources within each AuthorizationResource object

                        // get consent mapping resources for each AuthorizationResource in a Map
                        Map<String, ArrayList<ConsentMappingResource>> consentMappingResources = new HashMap<>();
                        for (ConsentMappingResource consentMappingResource :
                                detailedConsentResource.getConsentMappingResources()) {
                            if (!consentMappingResources.containsKey(consentMappingResource.getAuthorizationID())) {
                                consentMappingResources.put(consentMappingResource.getAuthorizationID(),
                                        new ArrayList<>());
                            }
                            consentMappingResources.get(consentMappingResource.getAuthorizationID())
                                    .add(consentMappingResource);
                        }

                        for (AuthorizationResource authorizationResource :
                                detailedConsentResource.getAuthorizationResources()) {
                            AuthResponse authorizationResourceResponse = new AuthResponse();
                            ConsentUtils.copyPropertiesToAuthorizationResourceResponse(authorizationResourceResponse,
                                    authorizationResource);
                            ArrayList<Resource> resources = new ArrayList<>();
                            //add the consent mapping resources to the resources
                            if (consentMappingResources.containsKey(authorizationResource.getAuthorizationID())) {
                                for (ConsentMappingResource consentMappingResource : consentMappingResources.get(
                                        authorizationResource.getAuthorizationID())) {
                                    Resource resource = new Resource();
                                    ConsentUtils.copyPropertiesToConsentMappingResourceResponse(resource,
                                            consentMappingResource);
                                    resources.add(resource);
                                }
                            }
                            authorizationResourceResponse.setResources(resources);
                            authResponses.add(authorizationResourceResponse);
                        }
                        consentResponse.setAuthorizationResources(authResponses);


                        return Response.ok().entity(consentResponse).build();


                    }else if (withAuthorizationResources & !isWithAttributes)

                    {

                        ////////////// Service call //////////////
                        DetailedConsentResource consentResourceWithAuthorizationResources =
                                consentCoreService.getConsentWithAuthorizationResources(consentID);

                        if (!consentResourceWithAuthorizationResources.getOrgID().equals(orgInfo)) {
                            log.error("OrgInfo does not match");
                            throw new ConsentMgtException(Response.Status.BAD_REQUEST,
                                    "OrgInfo does not match, please provide the correct OrgInfo");
                        }

                        //////////////  build Response  //////////////
                        ConsentResponse consentResponse = new ConsentResponse();
                        ConsentUtils.copyPropertiesToConsentResourceResponse(consentResponse, consentResourceWithAuthorizationResources,
                                false, false , false);
                        ArrayList<AuthResponse> authResponses = new ArrayList<>();

                        // response should contain resources within each AuthorizationResource object

                        // get consent mapping resources for each AuthorizationResource in a Map
                        Map<String, ArrayList<ConsentMappingResource>> consentMappingResources = new HashMap<>();
                        for (ConsentMappingResource consentMappingResource :
                                consentResourceWithAuthorizationResources.getConsentMappingResources()) {
                            if (!consentMappingResources.containsKey(consentMappingResource.getAuthorizationID())) {
                                consentMappingResources.put(consentMappingResource.getAuthorizationID(),
                                        new ArrayList<>());
                            }
                            consentMappingResources.get(consentMappingResource.getAuthorizationID())
                                    .add(consentMappingResource);
                        }

                        for (AuthorizationResource authorizationResource :
                                consentResourceWithAuthorizationResources.getAuthorizationResources()) {
                            AuthResponse authorizationResourceResponse = new AuthResponse();
                            ConsentUtils.copyPropertiesToAuthorizationResourceResponse(authorizationResourceResponse,
                                    authorizationResource);
                            ArrayList<Resource> resources = new ArrayList<>();
                            //add the consent mapping resources to the resources
                            if (consentMappingResources.containsKey(authorizationResource.getAuthorizationID())) {
                                for (ConsentMappingResource consentMappingResource : consentMappingResources.get(
                                        authorizationResource.getAuthorizationID())) {
                                    Resource resource = new Resource();
                                    ConsentUtils.copyPropertiesToConsentMappingResourceResponse(resource,
                                            consentMappingResource);
                                    resources.add(resource);
                                }
                            }
                            authorizationResourceResponse.setResources(resources);
                            authResponses.add(authorizationResourceResponse);
                        }
                        consentResponse.setAuthorizationResources(authResponses);


                        return Response.ok().entity(consentResponse).build();


                    }

                    else {

                        ConsentResource consent;
                        //////////////  Service Call  //////////////
                        if (Objects.equals(isWithAttributes,
                                true)) {
                            consent = consentCoreService.getConsent(consentID,
                                    true);
                        } else {
                            consent = consentCoreService.getConsent(consentID,
                                    false);
                        }

                        if (!consent.getOrgID().equals(orgInfo)) {
                            log.error("OrgInfo does not match");
                            throw new ConsentMgtException(Response.Status.NOT_FOUND,
                                    ConsentConstant.ORG_MISMATCH);
                        } else {
                            //////////////  build Response  //////////////
                            // remove null values from the consent
                            consent.setOrgID(null);
                            ObjectMapper objectMapper = new ObjectMapper();
                            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

                            return Response.ok().entity(objectMapper.writeValueAsString(consent)).build();


                        }
                    }
                } catch (JSONException | ConsentMgtException e) {
                    log.error("Error Occurred while handling the request", e);
                    throw new ConsentMgtException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), e);
                } catch (JsonProcessingException e) {
                    throw new ConsentMgtException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage(), e);
                }
            } else {
                log.error("Invalid Consent ID");
                throw new ConsentMgtException(Response.Status.BAD_REQUEST,
                        "Invalid Consent ID");
            }
        } catch (ConsentMgtException e) {
            return handleConsentMgtException(e);
        }
    }

    public Response consentAuthorizationAuthorizationIdGet(String authorizationId, String orgInfo){
        AuthResponse authResponse = new AuthResponse();

        try{
            AuthorizationResource authorizationResource = consentCoreService.getAuthorizationResource(authorizationId,
                    orgInfo);

            ConsentUtils.copyPropertiesToAuthorizationResourceResponse(authResponse, authorizationResource );
            ArrayList<Resource> resources = new ArrayList<>();
            for (ConsentMappingResource consentMappingResource : authorizationResource.getConsentMappingResource()){
                Resource resource = new Resource();
                resource.setConsentMappingStatus(consentMappingResource.getMappingStatus());
                resource.setResourceMappingId(consentMappingResource.getMappingID());
                resource.setResource(consentMappingResource.getResource());
                resources.add(resource);
            }

            authResponse.setResources(resources);
            return  Response.ok().entity(authResponse).build();

        } catch (ConsentMgtException e){

            return handleConsentMgtException(e);
        }


    }

    public Response consentConsentIdDelete(String consentID, String orgInfo, String userID) throws
            ConsentMgtException {
        try {
            ConsentResource consentResource = consentCoreService.getConsent(consentID,
                    false);

            if (!consentResource.getOrgID().equals(orgInfo)) {
                log.error("OrgInfo does not match");

                throw new ConsentMgtException(Response.Status.NOT_FOUND,
                        "OrgInfo does not match, please provide the correct OrgInfo");
            }
            consentCoreService.revokeConsent(consentID,
                    "revoked",
                    userID,
                    false);
            JSONObject message = new JSONObject();
            message.put("errorMessage", "Consent Revoked");

            return Response.ok().entity(message).build();

        } catch (ConsentMgtException e) {
            return handleConsentMgtException(e);
        }

    }


    public Response consentConsentIdHistoryGet(
            String consentID, String orgInfo, Boolean detailed, String status, String actionBy, long fromTimeValue,
            long toTimeValue, String statusAuditId) {

        try {

            Long fromTime = null;
            Long toTime = null;
            Integer limit = null;
            Integer offset = null;
            fromTime = fromTimeValue == 0L ? null : fromTimeValue;
            toTime = toTimeValue == 0L ? null : toTimeValue;

            ArrayList<ConsentStatusAuditRecord> consentStatusAuditRecords =
                    consentCoreService.searchConsentStatusAuditRecords(consentID, status,
                            actionBy, fromTime,
                            toTime,
                            statusAuditId);

            // get the consent status audit record Ids
            ArrayList<String> consentStatusAuditRecordIds = new ArrayList<>();
            for (ConsentStatusAuditRecord consentStatusAuditRecord : consentStatusAuditRecords) {
                consentStatusAuditRecordIds.add(consentStatusAuditRecord.getStatusAuditID());
            }

            ArrayList<ConsentHistoryResource> results =
                    new ArrayList<>(consentCoreService.getConsentAmendmentHistoryData(consentStatusAuditRecordIds,
                            consentID).values());
            ArrayList<ConsentHistoryResource> newResults = new ArrayList<>();

            if (!detailed) {
                for (ConsentHistoryResource result : results) {
                    DetailedConsentResource detailedConsentResource = new DetailedConsentResource();
                    detailedConsentResource.setConsentID(result.getDetailedConsentResource().getConsentID());
                    detailedConsentResource.setReceipt(result.getDetailedConsentResource().getReceipt());
                    detailedConsentResource.setValidityPeriod(result.getDetailedConsentResource().getValidityPeriod());
                    detailedConsentResource.setCurrentStatus(result.getDetailedConsentResource().getCurrentStatus());
                    detailedConsentResource.setConsentType(result.getDetailedConsentResource().getConsentType());
                    detailedConsentResource.setUpdatedTime(detailedConsentResource.getUpdatedTime());
                    detailedConsentResource.setCreatedTime(result.getDetailedConsentResource().getCreatedTime());
                    result.setDetailedConsentResource(detailedConsentResource);
                    newResults.add(result);
                }
                // remove null values from the consent
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

                try {
                    return Response.ok().entity(objectMapper.writeValueAsString(newResults)).build();

                } catch (JsonProcessingException e) {
                    log.error("Error Occurred while handling the request", e);
                    JSONObject error = new JSONObject();
                    error.put("errorMessage", e.getMessage());
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
                }

            } else {


                for (ConsentHistoryResource result : results) {
                    DetailedConsentResource detailedConsentResource =
                            result.getDetailedConsentResource();
                    DetailedConsentResource detailedConsentResourceResponse = new DetailedConsentResource();
                    detailedConsentResourceResponse.setConsentAttributes(
                            detailedConsentResource.getConsentAttributes());
                    detailedConsentResourceResponse.setConsentID(detailedConsentResource.getConsentID());
                    detailedConsentResourceResponse.setConsentType(detailedConsentResource.getConsentType());
                    detailedConsentResourceResponse.setClientID(detailedConsentResource.getClientID());
                    detailedConsentResourceResponse.setReceipt(detailedConsentResource.getReceipt());
                    detailedConsentResourceResponse.setValidityPeriod(detailedConsentResource.getValidityPeriod());
                    detailedConsentResourceResponse.setCurrentStatus(detailedConsentResource.getCurrentStatus());
                    detailedConsentResourceResponse.setCreatedTime(detailedConsentResource.getCreatedTime());
                    detailedConsentResourceResponse.setUpdatedTime(detailedConsentResource.getUpdatedTime());
                    ArrayList<AuthorizationResource> authorizationResources = new ArrayList<>();

                    // response should contain resources within each AuthorizationResource object

                    // get consent mapping resources for each AuthorizationResource in a Map
                    Map<String, ArrayList<ConsentMappingResource>> consentMappingResources = new HashMap<>();
                    for (ConsentMappingResource consentMappingResource :
                            detailedConsentResource.getConsentMappingResources()) {
                        if (!consentMappingResources.containsKey(consentMappingResource.getAuthorizationID())) {
                            consentMappingResources.put(consentMappingResource.getAuthorizationID(),
                                    new ArrayList<>());
                        }
                        consentMappingResources.get(consentMappingResource.getAuthorizationID())
                                .add(consentMappingResource);
                    }

                    for (AuthorizationResource authorizationResource :
                            detailedConsentResource.getAuthorizationResources()) {
                        AuthorizationResource authorizationResourceResponse = new AuthorizationResource();
                        authorizationResourceResponse.setAuthorizationStatus(
                                authorizationResource.getAuthorizationStatus());
                        authorizationResourceResponse.setAuthorizationID(authorizationResource.getAuthorizationID());
                        authorizationResourceResponse.setAuthorizationType(
                                authorizationResource.getAuthorizationType());
                        authorizationResourceResponse.setUserID(authorizationResource.getUserID());

                        ArrayList<ConsentMappingResource> resources = new ArrayList<>();
                        //add the consent mapping resources to the resources
                        if (consentMappingResources.containsKey(authorizationResource.getAuthorizationID())) {
                            for (ConsentMappingResource consentMappingResource : consentMappingResources.get(
                                    authorizationResource.getAuthorizationID())) {
                                ConsentMappingResource resource = new ConsentMappingResource();
                                resource.setMappingID(consentMappingResource.getMappingID());
                                resource.setAccountID(consentMappingResource.getAccountID());
                                resource.setPermission(consentMappingResource.getPermission());
                                resources.add(resource);
                            }
                        }
                        authorizationResourceResponse.setConsentMappingResource(resources);
                        authorizationResources.add(authorizationResourceResponse);
                    }
                    detailedConsentResourceResponse.setAuthorizationResources(authorizationResources);
                    result.setDetailedConsentResource(detailedConsentResourceResponse);
                    newResults.add(result);
                }

                // remove null values from the consent
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                try {
                    return Response.ok().entity(objectMapper.writeValueAsString(newResults)).build();
                } catch (JsonProcessingException e) {
                    log.error("Error Occurred while handling the request", e);
                    JSONObject error = new JSONObject();
                    error.put("errorMessage", e.getMessage());
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(error).build();
                }

            }

        } catch (ConsentMgtException e) {
            return handleConsentMgtException(e);
        }
    }

    private Response handleConsentMgtException(ConsentMgtException e) {
        log.error("Error Occurred while handling the request", e);
        JSONObject error = new JSONObject();
        error.put("errorMessage", e.getMessage());
        return Response.status(e.getErrorCode()).entity(error).build();
    }



}
