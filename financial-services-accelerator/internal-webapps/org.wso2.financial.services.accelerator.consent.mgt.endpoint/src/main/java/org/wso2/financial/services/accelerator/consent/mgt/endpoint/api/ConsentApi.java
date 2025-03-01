package org.wso2.financial.services.accelerator.consent.mgt.endpoint.api;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.handler.ConsentMgtApiHandler;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.models.AmendmentResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.models.AmendmentResponse;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.models.BulkConsentStatusUpdateResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.models.ConsentHistory;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.models.ConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.models.ConsentResponse;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.models.ConsentStatusUpdateResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.models.DetailedConsentResource;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

    import io.swagger.annotations.*;

import java.io.InputStream;
import java.util.Map;
import java.util.List;
import javax.validation.constraints.*;
import javax.validation.Valid;

/**
* Represents a collection of functions to interact with the API endpoints.
*/
@Path("/consent")
    @Api(description = "the consent API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-03-01T15:45:20.830641389+05:30[Asia/Colombo]", comments = "Generator version: 7.12.0")
public class ConsentApi {

ConsentMgtApiHandler consentMgtApiHandler = new ConsentMgtApiHandler();



            @DELETE
    @Path("/{consentId}")
    @ApiOperation(value = "Consent purging", notes = "", response = Void.class, tags={ "consent" })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 404, message = "Invalid consent id", response = Void.class)
    })
    public Response consentConsentIdDelete(

@PathParam("consentId") @ApiParam("consent id") String consentId,

@HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo,@QueryParam("userId") String userId

) {
        try{
            return Response.ok().entity(consentMgtApiHandler.consentConsentIdDelete( consentId, orgInfo, userId)).build();
        } catch (Exception e) {
            // Handle other errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("An unexpected error occurred: " + e.getMessage())
            .build();
            }
    }

            @GET
    @Path("/{consentId}")
    @Produces({ "application/json" })
    @ApiOperation(value = "Consent retrieval", notes = "", response = DetailedConsentResource.class, tags={ "consent" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = DetailedConsentResource.class),
        @ApiResponse(code = 404, message = "Invalid consent id", response = Void.class)
    })
    public Response consentConsentIdGet(

@PathParam("consentId") @ApiParam("consent id") String consentId,

@HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo,@QueryParam("detailedConsent") boolean detailedConsent

,@QueryParam("UserId") String userId

,@QueryParam("WithAttributes") boolean withAttributes

) {
        try{
            return Response.ok().entity(consentMgtApiHandler.consentConsentIdGet( consentId, orgInfo, detailedConsent, userId, withAttributes)).build();
        } catch (Exception e) {
            // Handle other errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("An unexpected error occurred: " + e.getMessage())
            .build();
            }
    }

            @GET
    @Path("/{consentId}/history")
    @Produces({ "application/json" })
    @ApiOperation(value = "get consent history", notes = "", response = ConsentHistory.class, responseContainer = "List", tags={ "consent" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = ConsentHistory.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid consent id", response = Void.class)
    })
    public Response consentConsentIdHistoryGet(

@PathParam("consentId") @ApiParam("consent id") String consentId,

@HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo,@QueryParam("detailed") boolean detailed

,@QueryParam("status")  @ApiParam("status")String status

,@QueryParam("actionBy")  @ApiParam("actionBy")String actionBy

,@QueryParam("fromTime")  @ApiParam("fromTime")String fromTime

,@QueryParam("toTime")  @ApiParam("toTime")String toTime

,@QueryParam("statusAuditId")  @ApiParam("statusAuditId")String statusAuditId

) {
        try{
            return Response.ok().entity(consentMgtApiHandler.consentConsentIdHistoryGet( consentId, orgInfo, detailed, status, actionBy, fromTime, toTime, statusAuditId)).build();
        } catch (Exception e) {
            // Handle other errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("An unexpected error occurred: " + e.getMessage())
            .build();
            }
    }

            @PUT
    @Path("/{consentId}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Consent amendment", notes = "", response = AmendmentResponse.class, tags={ "consent" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = AmendmentResponse.class),
        @ApiResponse(code = 400, message = "Invalid request body", response = Void.class)
    })
    public Response consentConsentIdPut(

@PathParam("consentId") @ApiParam("consent id") String consentId,

@Valid @NotNull AmendmentResource amendmentResource,

@HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo) {
        try{
            return Response.ok().entity(consentMgtApiHandler.consentConsentIdPut( consentId, amendmentResource, orgInfo)).build();
        } catch (Exception e) {
            // Handle other errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("An unexpected error occurred: " + e.getMessage())
            .build();
            }
    }

            @PUT
    @Path("/{consentId}/status")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Consent status update", notes = "", response = String.class, tags={ "consent" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = String.class),
        @ApiResponse(code = 400, message = "Invalid consent id", response = Void.class)
    })
    public Response consentConsentIdStatusPut(

@PathParam("consentId") @ApiParam("consent id") String consentId,

@Valid @NotNull ConsentStatusUpdateResource consentStatusUpdateResource,

@HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo) {
        try{
            return Response.ok().entity(consentMgtApiHandler.consentConsentIdStatusPut( consentId, consentStatusUpdateResource, orgInfo)).build();
        } catch (Exception e) {
            // Handle other errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("An unexpected error occurred: " + e.getMessage())
            .build();
            }
    }

            @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "consent search", notes = "", response = DetailedConsentResource.class, responseContainer = "List", tags={ "consent" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = DetailedConsentResource.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "Invalid consent id", response = Void.class)
    })
    public Response consentGet(

@HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo,@QueryParam("consentTypes") String consentTypes

,@QueryParam("consentStatuses") String consentStatuses

,@QueryParam("userIds") String userIds

,@QueryParam("fromTime") int fromTime

,@QueryParam("toTime") int toTime

,@QueryParam("limit") int limit

,@QueryParam("offset") int offset

) {
        try{
            return Response.ok().entity(consentMgtApiHandler.consentGet( orgInfo, consentTypes, consentStatuses, userIds, fromTime, toTime, limit, offset)).build();
        } catch (Exception e) {
            // Handle other errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("An unexpected error occurred: " + e.getMessage())
            .build();
            }
    }

            @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Consent initiation", notes = "", response = ConsentResponse.class, tags={ "consent" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = ConsentResponse.class),
        @ApiResponse(code = 400, message = "Invalid request body", response = Void.class)
    })
    public Response consentPost(

@Valid @NotNull ConsentResource consentResource,

@HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo,

@HeaderParam("IsImplicitAuth")   @ApiParam("Flag to determine whether authorization is implicit or not") Boolean isImplicitAuth,

@HeaderParam("ExclusiveConsent")   @ApiParam("Flag to determine whether this is an exclusive consent") Boolean exclusiveConsent) {
        try{
            return Response.ok().entity(consentMgtApiHandler.consentPost( consentResource, orgInfo, isImplicitAuth, exclusiveConsent)).build();
        } catch (Exception e) {
            // Handle other errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("An unexpected error occurred: " + e.getMessage())
            .build();
            }
    }

            @PUT
    @Path("/status")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Bulk consent status change", notes = "", response = String.class, tags={ "consent" })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = String.class),
        @ApiResponse(code = 400, message = "Invalid consent id", response = Void.class)
    })
    public Response consentStatusPut(

@Valid @NotNull BulkConsentStatusUpdateResource bulkConsentStatusUpdateResource,

@HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo) {
        try{
            return Response.ok().entity(consentMgtApiHandler.consentStatusPut( bulkConsentStatusUpdateResource, orgInfo)).build();
        } catch (Exception e) {
            // Handle other errors
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("An unexpected error occurred: " + e.getMessage())
            .build();
            }
    }
    }
