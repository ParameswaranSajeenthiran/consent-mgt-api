package org.wso2.financial.services.accelerator.consent.mgt.endpoint.api;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.wso2.financial.services.accelerator.consent.mgt.dao.exceptions.ConsentManagementException;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto.AmendmentResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto.AmendmentResponse;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto.ConsentMgtDTO;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto.ConsentResponse;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto.*;

import org.wso2.financial.services.accelerator.consent.mgt.endpoint.exception.ConsentException;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.handler.ConsentMgtApiHandler;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ResponseStatus;
import org.wso2.financial.services.accelerator.consent.mgt.service.constants.ConsentCoreServiceConstants;
import org.wso2.financial.services.accelerator.consent.mgt.service.impl.ConsentCoreServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;

/**
 * ConsentSearchEndpoint.
 * <p>
 * This specifies a REST API for consent search to be used at consent user and
 * customer service portals.
 **/
@SuppressFBWarnings("JAXRS_ENDPOINT")
// Suppressed content - Endpoints
// Suppression reason - False Positive : These endpoints are secured with access
// control
// as defined in the IS deployment.toml file
// Suppressed warning count - 5
@Path("/")
public class ConsentApi {
    ConsentMgtApiHandler consentMgtApiHandler = new ConsentMgtApiHandler();

//    private static final Log log = LogFactory.getLog(ConsentApi.class);

    public ConsentApi() {

    }

    /**
     * get Consent retrieval
     **/
    @GET
    @Path("/{consentId}")
    @Produces({"application/json"})
    @ApiOperation(value = "Consent retrieval", notes = "", response = DetailedConsentResource.class, tags = {"consent"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DetailedConsentResource.class),
            @ApiResponse(code = 404, message = "Invalid consent id", response = Void.class)
    })
    public Response consentConsentIdGet(@PathParam("consentId") @ApiParam("consent id") String consentId, @HeaderParam("OrgInfo") @ApiParam("jwt header containing tenant related information") String orgInfo, @QueryParam("detailedConsent") boolean detailedConsent, @QueryParam("UserId") String userId, @QueryParam("WithAttributes") boolean withAttributes) {
        return Response.ok().entity(consentMgtApiHandler.handleGetConsent(consentId, detailedConsent, withAttributes)).build();
    }

    @GET
    @Produces({"application/json"})
    @ApiOperation(value = "consent search", notes = "", response = DetailedConsentResource.class, responseContainer = "List", tags = {"consent"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = DetailedConsentResource.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid consent id", response = Void.class)
    })
    public Response consentSearch(@HeaderParam("OrgInfo") @ApiParam("jwt header containing tenant related information") ArrayList<String> orgInfo, @QueryParam("consentTypes") ArrayList<String> consentTypes, @QueryParam("consentStatuses") ArrayList<String> consentStatuses, @QueryParam("userIds") ArrayList<String> userIds, @QueryParam("fromTime") int fromTime, @QueryParam("toTime") int toTime, @QueryParam("limit") int limit, @QueryParam("offset") int offset) {
        return Response.ok().entity(consentMgtApiHandler.handleSearchConsent(consentTypes, consentStatuses, userIds, fromTime, toTime, limit, offset)).build();
    }

    @POST
    @Path("/")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "Consent initiation", notes = "", response = ConsentResponse.class, tags = {"consent"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = ConsentResponse.class),
            @ApiResponse(code = 400, message = "Invalid request body", response = Void.class)
    })
    public Response consentPost(@Valid @NotNull ConsentResourceDTO consentResource, @HeaderParam("OrgInfo") @ApiParam("jwt header containing tenant related information") String orgInfo, @QueryParam("IsImplicitAuth") @ApiParam("Flag to determine whether authorization is implicit or not") boolean isImplicitAuth, @QueryParam("ExclusiveConsent") @ApiParam("Flag to determine whether this is an exclusive consent") boolean exclusiveConsent) {
        try {
            // Normal processing logic
            ConsentMgtApiHandler consentMgtApiHandler = new ConsentMgtApiHandler();
            ConsentResponse consentResponse = consentMgtApiHandler.handleCreateConsent(consentResource, orgInfo, isImplicitAuth, exclusiveConsent);
            return Response.ok().entity(consentResponse).build();

        } catch (Exception e) {
            // Handle other errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An unexpected error occurred: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{consentId}")
    @ApiOperation(value = "Consent purging", notes = "", response = Void.class, tags = {"consent"})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successful operation", response = Void.class),
            @ApiResponse(code = 404, message = "Invalid consent id", response = Void.class)
    })
    public Response consentConsentIdDelete(@PathParam("consentId") @ApiParam("consent id") String consentId, @HeaderParam("OrgInfo") @ApiParam("jwt header containing tenant related information") String orgInfo, @QueryParam("userId") String userId) {
        return Response.ok().entity(consentMgtApiHandler.handleRevokeConsent(consentId, userId)).build();
    }

    @PUT
    @Path("/{consentId}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "Consent amendment", notes = "", response = AmendmentResponse.class, tags = {"consent"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = AmendmentResponse.class),
            @ApiResponse(code = 400, message = "Invalid request body", response = Void.class)
    })
    public Response updateConsent(@PathParam("consentId") @ApiParam("consent id") String consentId, @Valid @NotNull AmendmentResource amendmentResource, @HeaderParam("OrgInfo") @ApiParam("jwt header containing tenant related information") String orgInfo) {
        try {
            return Response.ok().entity(consentMgtApiHandler.amendConsent(consentId, amendmentResource)).build();
        } catch (ConsentManagementException e) {
            throw new RuntimeException(e);
        }
    }

    @PUT
    @Path("/{consentId}/status")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "Consent status update", notes = "", response = String.class, tags = {"consent"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = String.class),
            @ApiResponse(code = 400, message = "Invalid consent id", response = Void.class)
    })
    public Response updateConsentStatus(@PathParam("consentId") @ApiParam("consent id") String consentId, @Valid @NotNull ConsentStatusUpdateResource consentStatusUpdateResource, @HeaderParam("OrgInfo") @ApiParam("jwt header containing tenant related information") String orgInfo) {

        try {
            return Response.ok().entity(consentMgtApiHandler.updateConsentStatus(consentId, consentStatusUpdateResource)).build();
        } catch (ConsentManagementException e) {
            throw new RuntimeException(e);
        }
    }


    @PUT
    @Path("/status")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    @ApiOperation(value = "Bulk consent status change", notes = "", response = String.class, tags = {"consent"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = String.class),
            @ApiResponse(code = 400, message = "Invalid consent id", response = Void.class)
    })
    public Response consentStatusPut(@Valid @NotNull BulkConsentStatusUpdateResource bulkConsentStatusUpdateResource, @HeaderParam("OrgInfo") @ApiParam("jwt header containing tenant related information") String orgInfo) {
        try {
            return Response.ok().entity(consentMgtApiHandler.bulkUpdateConsentStatus(bulkConsentStatusUpdateResource)).build();
        } catch (ConsentManagementException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/{consentId}/history")
    @Produces({"application/json"})
    @ApiOperation(value = "get consent history", notes = "", response = ConsentHistory.class, responseContainer = "List", tags = {"consent"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = ConsentHistory.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid consent id", response = Void.class)
    })
    public Response consentConsentIdHistoryGet(@PathParam("consentId") @ApiParam("consent id") String consentId, @HeaderParam("OrgInfo") @ApiParam("jwt header containing tenant related information") String orgInfo, @QueryParam("detailed") Boolean detailed, @QueryParam("status") @ApiParam("status") String status, @QueryParam("actionBy") @ApiParam("actionBy") String actionBy, @QueryParam("fromTime") @ApiParam("fromTime") String fromTime, @QueryParam("toTime") @ApiParam("toTime") String toTime, @QueryParam("statusAuditId") @ApiParam("statusAuditId") String statusAuditId) {
        return Response.ok().entity("magic!").build();
    }

    //test
    @GET
    @Path("/get")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces({"application/json; charset=utf-8"})
    public Response test(@Context HttpServletRequest request, @Context HttpServletResponse response,
                         @Context UriInfo uriInfo) {

        try {
            ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();

            return Response.ok(ConsentCoreServiceConstants.TEST).build();

        } catch (Exception e) {
//            log.error("Error occurred while searching consent data", e);
            return Response.ok(e).build();
        }
    }

    /**
     * Method to send response using the payload and response status.
     *
     * @param consentMgtDTO Consent admin data
     * @return Response
     */
    private Response sendResponse(ConsentMgtDTO consentMgtDTO) {
        if (consentMgtDTO.getPayload() != null || consentMgtDTO.getResponseStatus() != null) {
            return Response.status(consentMgtDTO.getResponseStatus().getStatusCode())
                    .entity(consentMgtDTO.getResponsePayload().toString()).build();
        } else {
//            log.debug("Response status or payload unavailable. Throwing exception");
            throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, "Response data unavailable");
        }
    }
}
