package org.wso2.financial.services.accelerator.consent.mgt.endpoint.api;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.json.JSONObject;
//import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentResource;
//import org.wso2.financial.services.accelerator.consent.mgt.dao.models.DetailedConsentResource;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.wso2.financial.services.accelerator.common.exception.ConsentManagementException;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.DetailedConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto.*;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.exception.ConsentException;
//import org.wso2.financial.services.accelerator.consent.mgt.endpoint.handler.ConsentMgtApiHandler;
//import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ConsentUtils;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.handler.ConsentMgtApiHandler;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ConsentUtils;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ResponseStatus;
import org.wso2.financial.services.accelerator.consent.mgt.service.constants.ConsentCoreServiceConstants;
import org.wso2.financial.services.accelerator.consent.mgt.service.impl.ConsentCoreServiceImpl;

//import java.util.Objects;
//import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * ConsentSearchEndpoint.
 * <p>
 * This specifies a REST API for consent search to be used at consent user and
 * customer service portals.
 **/
//@SuppressFBWarnings("JAXRS_ENDPOINT")
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

//        ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();

    }


    /**
     * get Consent by Id
     **/
    @GET
    @Path("/{consentId}")
    @Produces({"application/json; charset=utf-8"})
    public Response getConsent(   @Context HttpServletRequest request, @Context HttpServletResponse response,
                               @Context UriInfo uriInfo) {


        ConsentMgtApiHandler consentMgtApiHandler = new ConsentMgtApiHandler();
        ConsentMgtDTO consentMgtDTO = new ConsentMgtDTO(ConsentUtils.getHeaders(request),
                ConsentUtils.getPayload(request), uriInfo.getQueryParameters(),
                uriInfo.getPath(), uriInfo.getPathParameters(), request,
                response);

        consentMgtApiHandler.handleGetConsent(consentMgtDTO);
        return sendResponse(consentMgtDTO);


    }

    /**
     * ConsentCreateEndpoint.
     */
    @GET
    @Path("/")
    @Consumes({"application/x-www-form-urlencoded"})
    @Produces({"application/json; charset=utf-8"})
    public Response search(@Context HttpServletRequest request, @Context HttpServletResponse response,
                           @Context UriInfo uriInfo) {

        try {

            ConsentMgtApiHandler consentMgtApiHandler = new ConsentMgtApiHandler();
            ConsentMgtDTO consentMgtDTO = new ConsentMgtDTO(ConsentUtils.getHeaders(request),
                    ConsentUtils.getPayload(request), uriInfo.getQueryParameters(),
                    uriInfo.getPath(), uriInfo.getPathParameters(), request,
                    response);
            consentMgtApiHandler.handleSearch(consentMgtDTO);
            return sendResponse(consentMgtDTO);

        } catch (Exception e) {
//            log.error("Error occurred while searching consent data", e);
            return Response.ok(e).build();
        }


    }

    @POST
    @Path("/")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @ApiOperation(value = "Consent initiation", notes = "", response = ConsentResponse.class, tags={ "consent" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = ConsentResponse.class),
            @ApiResponse(code = 400, message = "Invalid request body", response = Void.class)
    })
    public Response consentPost(@Valid @NotNull ConsentResourceDTO consentResource, @HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo, @QueryParam("IsImplicitAuth")   @ApiParam("Flag to determine whether authorization is implicit or not") boolean isImplicitAuth, @QueryParam("ExclusiveConsent")   @ApiParam("Flag to determine whether this is an exclusive consent") boolean exclusiveConsent) {
        try {
            // Normal processing logic
            ConsentMgtApiHandler consentMgtApiHandler = new ConsentMgtApiHandler();
            ConsentResponse consentResponse =consentMgtApiHandler.handleCreateConsent(consentResource, orgInfo, isImplicitAuth, exclusiveConsent);
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
    @ApiOperation(value = "Consent purging", notes = "", response = Void.class, tags={ "consent" })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successful operation", response = Void.class),
            @ApiResponse(code = 404, message = "Invalid consent id", response = Void.class)
    })
    public Response consentConsentIdDelete(@PathParam("consentId") @ApiParam("consent id") String consentId,@HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo,@QueryParam("userId")   String userId) {
        return Response.ok().entity("magic!").build();
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
    public Response updateConsent(@PathParam("consentId") @ApiParam("consent id") String consentId,@Valid @NotNull AmendmentResource amendmentResource,@HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo) {
        return Response.ok().entity("magic!").build();
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
    public Response updateConsentStatus(@PathParam("consentId") @ApiParam("consent id") String consentId, @Valid @NotNull ConsentStatusUpdateResource consentStatusUpdateResource, @HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo) {

        try {
            return Response.ok().entity(consentMgtApiHandler.updateConsentStatus(consentId, consentStatusUpdateResource)).build();
        } catch (ConsentManagementException e) {
            throw new RuntimeException(e);
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
    public Response consentStatusPut(@Valid @NotNull BulkConsentStatusUpdateResource bulkConsentStatusUpdateResource,@HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo) {
        try {
            return Response.ok().entity(consentMgtApiHandler.bulkUpdateConsentStatus(bulkConsentStatusUpdateResource)).build();
        } catch (ConsentManagementException e) {
            throw new RuntimeException(e);
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
    public Response consentConsentIdHistoryGet(@PathParam("consentId") @ApiParam("consent id") String consentId,@HeaderParam("OrgInfo")   @ApiParam("jwt header containing tenant related information") String orgInfo,@QueryParam("detailed")   Boolean detailed,@QueryParam("status")  @ApiParam("status")  String status,@QueryParam("actionBy")  @ApiParam("actionBy")  String actionBy,@QueryParam("fromTime")  @ApiParam("fromTime")  String fromTime,@QueryParam("toTime")  @ApiParam("toTime")  String toTime,@QueryParam("statusAuditId")  @ApiParam("statusAuditId")  String statusAuditId) {
        return Response.ok().entity("magic!").build();
    }







//    /**
//     * test
//     */
//    @GET
//    @Path("/get")
//    @Consumes({"application/x-www-form-urlencoded"})
//    @Produces({"application/json; charset=utf-8"})
//    public Response test(@Context HttpServletRequest request, @Context HttpServletResponse response,
//                           @Context UriInfo uriInfo) {
//
//        try {
//            ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();
////            consentCoreService.
//
//
//            return Response.ok(request.getPathInfo()).build();
//
//        } catch (Exception e) {
////            log.error("Error occurred while searching consent data", e);
//            return Response.ok(e).build();
//        }
//
//
//    }

//
//    /**
//     * ConsentCreateEndpoint.
//     */
//    @GET
//    @Path("/create")
//    @Produces({"application/json; charset=utf-8"})
//    public Response createConsent(@Context HttpServletRequest request, @Context HttpServletResponse response,
//                                  @Context UriInfo uriInfo) {
//
//        try {
//            ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();
//
//
////             ConsentMgtDTO consentMgtDTO = new ConsentMgtDTO(ConsentUtils.getHeaders(request),
////                    ConsentUtils.getPayload(request), uriInfo.getQueryParameters(),
////                uriInfo.getPath(), uriInfo.getPathParameters(),  request, response);
//            ConsentResource consentResource = new ConsentResource(UUID.randomUUID().toString(), "sdfs",
//                    new JSONObject(Objects.requireNonNull(ConsentUtils.getPayload(request)).toString()).toString(),
//                    "sdfs", 2, 232, false, "test",
//                    453, 34345);
//
//
//            DetailedConsentResource detailedConsentResource = consentCoreService.createAuthorizableConsent(
//                    consentResource, "sdf", "", "sdf",
//                    false);
//            log.info("Consent created successfully with id: " + detailedConsentResource.getConsentID());
//
//        } catch (Exception e) {
//            return Response.ok(e.toString()).build();
//        }
//
//
//        return Response.ok(request.getPathInfo()).build();
//    }


//    /**
//     * ConsentCreateEndpoint.
//     */
//    @GET
//    @Path("/search")
//    @Consumes({"application/x-www-form-urlencoded"})
//    @Produces({"application/json; charset=utf-8"})
//    public Response search(@Context HttpServletRequest request, @Context HttpServletResponse response,
//                           @Context UriInfo uriInfo) {
//
//        try {
//
//            ConsentMgtApiHandler consentMgtApiHandler = new ConsentMgtApiHandler();
//            ConsentMgtDTO consentMgtDTO = new ConsentMgtDTO(ConsentUtils.getHeaders(request).get("client_id"),
//                    ConsentUtils.getHeaders(request),
//                    ConsentUtils.getPayload(request), uriInfo.getQueryParameters(),
//                    uriInfo.getPathParameters().getFirst("s"), uriInfo.getAbsolutePath().getPath(), request,
//                    response);
//            consentMgtApiHandler.handleSearch(consentMgtDTO);
//            return sendResponse(consentMgtDTO);
//
//        } catch (Exception e) {
//            log.error("Error occurred while searching consent data", e);
//            return Response.ok(e).build();
//        }
//

//    /**
//     * ConsentCreateEndpoint.
//     */
//    @GET
//    @Path("/create")
//    @Produces({"application/json; charset=utf-8"})
//    public Response createConsent(@Context HttpServletRequest request, @Context HttpServletResponse response,
//                                  @Context UriInfo uriInfo) {
//
//        try {
//            ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();
//            ConsentManageData consentManageData = new ConsentManageData(ConsentUtils.getHeaders(request),
//                    ConsentUtils.getPayload(request), uriInfo.getQueryParameters(),
//                    uriInfo.getPathParameters().getFirst("s"), request, response);
//            ConsentResource consentResource = new ConsentResource("wew", "sdfs",
//                    new JSONObject(consentManageData.getPayload().toString()).toString(),
//                    "sdfs", 2, 232, false, "test",
//                    453, 34345);
//
//
//            consentCoreService.createAuthorizableConsent(consentResource, "sdf", "", "sdf",
//                    false);
//
//        } catch (ConsentManagementException e) {
//            log.error("Error occurred while creating consent data", e);
//            return Response.ok(e.toString()).build();
//        }
//
//
//        return Response.ok(request.getPathInfo()).build();
//    }
//


//    /**
//     * UPDATE Status
//     **/
//    @PUT
//    @Path("/{consentId}/status")
//    public Response updateConsentStatus(@Context HttpServletRequest request, @Context HttpServletResponse response,
//                                        @Context UriInfo uriInfo) {
//
//        try {
//            ConsentMgtApiHandler consentMgtApiHandler = new ConsentMgtApiHandler();
//            ConsentMgtDTO consentMgtDTO = new ConsentMgtDTO(ConsentUtils.getHeaders(request),
//                    ConsentUtils.getPayload(request), uriInfo.getQueryParameters(),
//                    uriInfo.getPath(), uriInfo.getPathParameters(), request,
//                    response);
//
//            consentMgtApiHandler.handleUpdateConsentStatus(consentMgtDTO);
//            return sendResponse(consentMgtDTO);
//
//        } catch (Exception e) {
//            log.error("Error occurred while updating consent data", e);
//            return Response.ok(e).build();
//        }
//    }

//    /**
//     * DELETE Consent by Id
//     */
//    @DELETE
//    @Path("/{id}")
//    @Produces({"application/json; charset=utf-8"})
//    public Response deleteConsent(@Context HttpServletRequest request, @Context HttpServletResponse response,
//                                  @Context UriInfo uriInfo) {
//
//        try {
//            ConsentMgtApiHandler consentMgtApiHandler = new ConsentMgtApiHandler();
//            ConsentMgtDTO consentMgtDTO = new ConsentMgtDTO(ConsentUtils.getHeaders(request).get("client_id"),
//                    ConsentUtils.getHeaders(request),
//                    ConsentUtils.getPayload(request), uriInfo.getQueryParameters(),
//                    uriInfo.getPathParameters().getFirst("id"), uriInfo.getAbsolutePath().getPath(), request,
//                    response);
////            consentMgtDTO.setClientId(consentMgtDTO.getHeaders().get("client_id"));
//
////            consentMgtApiHandler.handleDeleteConsent(consentMgtDTO);
//            return sendResponse(consentMgtDTO);
//
//        } catch (Exception e) {
//            log.error("Error occurred while deleting consent data", e);
//            return Response.ok(e).build();
//        }
//    }
//
//
//    /**
//     * Method to send response using the payload and response status.
//     *
//     * @param consentAdminData Consent admin data
//     * @return Response
//     */
//    private Response sendResponse(ConsentMgtDTO consentAdminData) {
//        if (consentAdminData.getPayload() != null || consentAdminData.getResponseStatus() != null) {
//            return Response.status(consentAdminData.getResponseStatus().getStatusCode())
//                    .entity(consentAdminData.getResponsePayload().toString()).build();
//        } else {
//            log.debug("Response status or payload unavailable. Throwing exception");
//            throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, "Response data unavailable");
//        }
//    }
//
//    /**
//     * update status of consent
//     *
//     */
//    @PUT
//    @Path("/{id}")
//    @Produces({"application/json; charset=utf-8"})
//    public Response updateConsentStatus(@Context HttpServletRequest request, @Context HttpServletResponse response,
//                                        @Context UriInfo uriInfo) {
//
//        try {
//            ConsentMgtApiHandler consentMgtApiHandler = new ConsentMgtApiHandler();
//            ConsentMgtDTO consentMgtDTO = new ConsentMgtDTO(ConsentUtils.getHeaders(request).get("client_id"),
//            ConsentUtils.getHeaders(request),
//                    ConsentUtils.getPayload(request),   uriInfo.getQueryParameters(),
//                    uriInfo.getPathParameters().getFirst("id"), uriInfo.getAbsolutePath().getPath(), request,
//                    response);
////            consentMgtDTO.setClientId(consentMgtDTO.getHeaders().get("client_id"));
//
//            consentMgtApiHandler.handleUpdateConsentStatus(consentMgtDTO);
//            return sendResponse(consentMgtDTO);
//
//        } catch (Exception e) {
//            log.error("Error occurred while updating consent data", e);
//            return Response.ok(e).build();
//        }
//    }

//    /**
//     * update status of consent
//     */
//    @PUT
//    @Path("/{id}/revoke")
//    @Produces({"application/json; charset=utf-8"})
//    public Response revokeConsent(@Context HttpServletRequest request, @Context HttpServletResponse response,
//                                  @Context UriInfo uriInfo) {
//
//        try {
//            ConsentMgtApiHandler consentMgtApiHandler = new ConsentMgtApiHandler();
//            ConsentMgtDTO consentMgtDTO = new ConsentMgtDTO(ConsentUtils.getHeaders(request).get("client_id"),
//            ConsentUtils.getHeaders(request),
//                    ConsentUtils.getPayload(request), uriInfo.getQueryParameters(),
//                    uriInfo.getPathParameters().getFirst("id"), uriInfo.getAbsolutePath().getPath(), request,
//                    response);
//
//        }
//    }

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
