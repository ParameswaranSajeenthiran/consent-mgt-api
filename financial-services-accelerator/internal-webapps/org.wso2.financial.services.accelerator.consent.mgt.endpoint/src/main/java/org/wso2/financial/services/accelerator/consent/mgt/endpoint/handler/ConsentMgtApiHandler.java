package org.wso2.financial.services.accelerator.consent.mgt.endpoint.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.wso2.financial.services.accelerator.consent.mgt.dao.exceptions.ConsentManagementException;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.AuthorizationResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentMappingResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.DetailedConsentResource;
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
import org.wso2.financial.services.accelerator.consent.mgt.service.exception.ConsentManagementRuntimeException;
import org.wso2.financial.services.accelerator.consent.mgt.service.impl.ConsentCoreServiceImpl;

import javax.ws.rs.core.Response;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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


    public Response consentGet(
            String orgInfo,
            String consentType, String consentStatus
            , String userID, long fromTimeValue, long toTimeValue, int limitValue, int offsetValue)
             {

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
            Collections.addAll( consentTypes, consentType.split(","));
        }
        if (consentStatus != null) {
            Collections.addAll( consentStatuses, consentStatus.split(","));
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
                 ArrayList<ConsentResponse> consentResponses = new ArrayList<>();
                 for ( DetailedConsentResource result : results) {
                     ConsentResponse consentResponse = new ConsentResponse();
                     ConsentUtils.copyPropertiesToConsentResourceResponse(consentResponse, result,
                             false);
                     ArrayList<AuthResponse> authResponses = new ArrayList<>();

                     // get consent mapping resources for each AuthorizationResource in a Map
                     Map<String, ArrayList<ConsentMappingResource>> consentMappingResources = new HashMap<>();
                     for (ConsentMappingResource consentMappingResource : result.getConsentMappingResources()) {
                         if (!consentMappingResources.containsKey(consentMappingResource.getAuthorizationID())) {
                             consentMappingResources.put(consentMappingResource.getAuthorizationID(),
                                     new ArrayList<>());
                         }
                         consentMappingResources.get(consentMappingResource.getAuthorizationID()).add(consentMappingResource);
                     }


                     for (AuthorizationResource authorizationResource : result.getAuthorizationResources()) {
                         AuthResponse authorizationResourceResponse = new AuthResponse();
                         ConsentUtils.copyPropertiesToAuthorizationResourceResponse(authorizationResourceResponse, authorizationResource);
                         ArrayList<Resource> resources = new ArrayList<>();
                         //add the consent mapping resources to the resources
                         if (consentMappingResources.containsKey(authorizationResource.getAuthorizationID())) {
                             for (ConsentMappingResource consentMappingResource : consentMappingResources.get(authorizationResource.getAuthorizationID())) {
                                 Resource resource = new Resource();
                                 ConsentUtils.copyPropertiesToConsentMappingResourceResponse(resource, consentMappingResource);
                                 resources.add(resource);
                             }
                         }
                         authorizationResourceResponse.setResources(resources);
                         authResponses.add(authorizationResourceResponse);
                     }
                     consentResponse.setAuthorizationResources(authResponses);
                     consentResponses.add(consentResponse);


                 }


        return Response.ok().entity(consentResponses).build() ;
    }

    //

    public Response consentPost (
            ConsentResourceDTO consentResourceDTO,
            String orgInfo, boolean isImplicitAuth, boolean exclusiveConsent)
            throws
            ConsentManagementException,
            InvocationTargetException,
            IllegalAccessException {

        // handle request
        ConsentResource consentResource = new ConsentResource();
        ConsentUtils.copyPropertiesToConsentResource(consentResource, consentResourceDTO);
        consentResource.setOrgID(orgInfo);

        // if consentFrequency is an attribute in consentAttributes, then set it to the consentResource
        Map<String, String> consentAttributes =ConsentUtils.convertToMap(consentResourceDTO.getConsentAttributes());
        if (!consentAttributes.isEmpty()){
            if (consentAttributes.containsKey("consentFrequency")) {
                consentResource.setConsentFrequency(Integer.parseInt(consentAttributes.get("consentFrequency")));
            } else {
                consentResource.setConsentFrequency(0);
            }
        }


        ArrayList<AuthorizationResource> authorizations = new ArrayList<>();

        if(isImplicitAuth){
            for (AuthorizationResourceDTO authorizationResourceDTO : consentResourceDTO.getAuthorizationResources()) {
                AuthorizationResource authorizationResource = new AuthorizationResource();
                ConsentUtils.copyPropertiesToAuthorizationResource(authorizationResource, authorizationResourceDTO);
                ArrayList<ConsentMappingResource> consentMappingResources = new ArrayList<>();
                for (ResourcePermission resource : authorizationResourceDTO.getResources()) {
                    ConsentMappingResource res = new ConsentMappingResource();
                    res.setPermission(resource.getPermission());
                    res.setAccountID(resource.getAccountID());
                    consentMappingResources.add(res);
            }
                authorizationResource.setConsentMappingResource(consentMappingResources);
                authorizations.add(authorizationResource);

            }
        }

        // Service call
        DetailedConsentResource result = null;

        if (!exclusiveConsent) {
            result = consentCoreService.createAuthorizableConsentWithBulkAuth(consentResource,
                    authorizations,
                    isImplicitAuth);

        } else {
            //TODO : Implement exclusive consent
            throw new ConsentManagementException("Exclusive Consent Not yet implemented");
        }

        // Handle response
        ConsentResponse consentResponse = new ConsentResponse();
        ConsentUtils.copyPropertiesToConsentResourceResponse(consentResponse, result , isImplicitAuth);

        return Response.status(Response.Status.CREATED).entity(consentResponse).build();

    }

    public Response consentConsentIdStatusPut(
            String consentID, ConsentStatusUpdateResource consentStatusUpdateResource
            , String orgInfo) {
        consentCoreService.updateConsentStatusWithImplicitReasonAndUserId(consentID,
                consentStatusUpdateResource.getStatus(),
                consentStatusUpdateResource.getReason(),
                consentStatusUpdateResource.getUserId());
        return Response.ok().entity("Status Updated").build();
    }

    public Response consentStatusPut(BulkConsentStatusUpdateResource bulkConsentStatusUpdateResource, String orgInfo)
            throws
            ConsentManagementRuntimeException
             {
            consentCoreService.bulkUpdateConsentStatus(
                    orgInfo,
                    bulkConsentStatusUpdateResource.getClientID(),
                    bulkConsentStatusUpdateResource.getStatus(),
                    bulkConsentStatusUpdateResource.getReason(),
                    bulkConsentStatusUpdateResource.getUserId(),
                    bulkConsentStatusUpdateResource.getConsentType(),
                    (ArrayList<String>) bulkConsentStatusUpdateResource.getApplicableStatusesForStateChange());
            return Response.ok().entity("Status Updated").build();
    }

    public DetailedConsentResource consentConsentIdPut(
            String consentID, AmendmentResource amendmentResource,
            String orgInfo) {

        // get authorization resources without authId
        ArrayList<AuthorizationResource> newAuthorization = new ArrayList<>();

        // get authorization resources with authId
        ArrayList<AuthorizationResource> reAuthorization = new ArrayList<>();

        // get resources without authId
        Map<String, ArrayList<ConsentMappingResource>> newResources = new HashMap<>();

        // get resources with authId
        ArrayList<ConsentMappingResource> reResources = new ArrayList<>();

        for (ReauthorizeResource authResourceDTO : amendmentResource.getAuthorizationResources()) {
            if (authResourceDTO.getAuthorizationId() != null) {
                AuthorizationResource auth = new AuthorizationResource();
               ConsentUtils.copyPropertiesToAuthorizationResource(auth, authResourceDTO);
               auth.setConsentID(consentID);
                reAuthorization.add(auth);

                for (Resource resource : authResourceDTO.getResources()) {
                    if (resource.getResourceMappingId() != null) {
                        ConsentMappingResource res = new ConsentMappingResource();
                        ConsentUtils.copyPropertiesToConsentMappingResource(resource, res);
                        res.setAuthorizationID(auth.getAuthorizationID());
                        reResources.add(res);

                    } else {
                        ConsentMappingResource newRes = new ConsentMappingResource();
                        ConsentUtils.copyPropertiesToConsentMappingResource(resource, newRes);

                        ///  check if the user already exists
                        if (!newResources.containsKey(authResourceDTO.getUserId())) {
                            newResources.put(authResourceDTO.getUserId(),
                                    new ArrayList<>());
                        } else {
                            newResources.get(authResourceDTO.getUserId()).add(newRes);
                        }

                    }
                }
            } else {
                AuthorizationResource auth = new AuthorizationResource();
               ConsentUtils.copyPropertiesToAuthorizationResource(auth, authResourceDTO);
            auth.setConsentID(consentID);
                newAuthorization.add(auth);

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
                newAuthorization);
        return amendmentResponse;

    }


    //
//
    public Object consentConsentIdGet(
            String consentID, String orgInfo, boolean isDetailedConsent, String userId,
            boolean isWithAttributes)
             {


        if (ConsentUtils.isConsentIdValid(consentID)) {
            ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();

            try {

                if (isDetailedConsent) {
                    DetailedConsentResource detailedConsentResource =
                            consentCoreService.getDetailedConsent(consentID);

                    if (!detailedConsentResource.getOrgID().equals(orgInfo)) {
                        log.error("OrgInfo does not match");
                        throw new ConsentManagementRuntimeException(Response.Status.NOT_FOUND,
                                "OrgInfo does not match, please provide the correct OrgInfo");
                    }

                    ConsentResponse consentResponse = new ConsentResponse();
                    ConsentUtils.copyPropertiesToConsentResourceResponse(consentResponse, detailedConsentResource,
                            false);
                    ArrayList<AuthResponse> authResponses = new ArrayList<>();

                    // get consent mapping resources for each AuthorizationResource in a Map
                    Map<String, ArrayList<ConsentMappingResource>> consentMappingResources = new HashMap<>();
                    for (ConsentMappingResource consentMappingResource : detailedConsentResource.getConsentMappingResources()) {
                        if (!consentMappingResources.containsKey(consentMappingResource.getAuthorizationID())) {
                            consentMappingResources.put(consentMappingResource.getAuthorizationID(),
                                    new ArrayList<>());
                        }
                        consentMappingResources.get(consentMappingResource.getAuthorizationID()).add(consentMappingResource);
                    }


                    for (AuthorizationResource authorizationResource : detailedConsentResource.getAuthorizationResources()) {
                        AuthResponse authorizationResourceResponse = new AuthResponse();
                        ConsentUtils.copyPropertiesToAuthorizationResourceResponse(authorizationResourceResponse, authorizationResource);
                        ArrayList<Resource> resources = new ArrayList<>();
                      //add the consent mapping resources to the resources
                        if (consentMappingResources.containsKey(authorizationResource.getAuthorizationID())) {
                            for (ConsentMappingResource consentMappingResource : consentMappingResources.get(authorizationResource.getAuthorizationID())) {
                                Resource resource = new Resource();
                               ConsentUtils.copyPropertiesToConsentMappingResourceResponse(resource, consentMappingResource);
                                resources.add(resource);
                            }
                        }
                        authorizationResourceResponse.setResources(resources);
                        authResponses.add(authorizationResourceResponse);
                    }
                    consentResponse.setAuthorizationResources(authResponses);



                    return consentResponse;


                } else {
                    ConsentResource consent ;
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
                        throw new ConsentManagementRuntimeException(Response.Status.NOT_FOUND,
                                "OrgInfo does not match, please provide the correct OrgInfo");
                    } else {

                        // remove null values from the consent
                        consent.setOrgID(null);
                        ObjectMapper objectMapper = new ObjectMapper();
                        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

                        return objectMapper.writeValueAsString(consent);

                    }

                }


            } catch (JSONException | ConsentManagementException e) {
                log.error("Error Occurred while handling the request",
                        e);
                throw new ConsentManagementRuntimeException(Response.Status.INTERNAL_SERVER_ERROR,
                        e.getMessage(),
                        e);

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.error("Consent ID  Not Found");
                 throw new ConsentManagementRuntimeException(Response.Status.INTERNAL_SERVER_ERROR,
                         "Consent ID  Not Found");
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
