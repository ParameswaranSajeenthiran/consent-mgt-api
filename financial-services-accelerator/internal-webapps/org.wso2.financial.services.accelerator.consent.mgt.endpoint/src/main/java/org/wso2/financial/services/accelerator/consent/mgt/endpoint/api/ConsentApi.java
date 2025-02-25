package org.wso2.financial.services.accelerator.consent.mgt.endpoint.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.DetailedConsentResource;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto.ConsentMgtDTO;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.exception.ConsentException;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.handler.ConsentMgtApiHandler;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ConsentUtils;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.utils.ResponseStatus;
import org.wso2.financial.services.accelerator.consent.mgt.service.impl.ConsentCoreServiceImpl;

import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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


    private static final Log log = LogFactory.getLog(ConsentApi.class);

    public ConsentApi() {
        ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();

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


    /**
     * ConsentCreateEndpoint.
     */
    @GET
    @Path("/create")
    @Produces({"application/json; charset=utf-8"})
    public Response createConsent(@Context HttpServletRequest request, @Context HttpServletResponse response,
                                  @Context UriInfo uriInfo) {

        try {
            ConsentCoreServiceImpl consentCoreService = new ConsentCoreServiceImpl();


//             ConsentMgtDTO consentMgtDTO = new ConsentMgtDTO(ConsentUtils.getHeaders(request),
//                    ConsentUtils.getPayload(request), uriInfo.getQueryParameters(),
//                uriInfo.getPath(), uriInfo.getPathParameters(),  request, response);
            ConsentResource consentResource = new ConsentResource(UUID.randomUUID().toString(), "sdfs",
                    new JSONObject(Objects.requireNonNull(ConsentUtils.getPayload(request)).toString()).toString(),
                    "sdfs", 2, 232, false, "test",
                    453, 34345);


            DetailedConsentResource detailedConsentResource = consentCoreService.createAuthorizableConsent(
                    consentResource, "sdf", "", "sdf",
                    false);
            log.info("Consent created successfully with id: " + detailedConsentResource.getConsentID());

        } catch (Exception e) {
            return Response.ok(e.toString()).build();
        }


        return Response.ok(request.getPathInfo()).build();
    }


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
            log.error("Error occurred while searching consent data", e);
            return Response.ok(e).build();
        }


    }
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

    /**
     * get Consent by Id
     **/
    @GET
    @Path("/{consentId}")
    @Produces({"application/json; charset=utf-8"})
    public Response getConsent(@Context HttpServletRequest request, @Context HttpServletResponse response,
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
     * UPDATE Status
     **/
    @PUT
    @Path("/{consentId}/status")
    public Response updateConsentStatus(@Context HttpServletRequest request, @Context HttpServletResponse response,
                                        @Context UriInfo uriInfo) {

        try {
            ConsentMgtApiHandler consentMgtApiHandler = new ConsentMgtApiHandler();
            ConsentMgtDTO consentMgtDTO = new ConsentMgtDTO(ConsentUtils.getHeaders(request),
                    ConsentUtils.getPayload(request), uriInfo.getQueryParameters(),
                    uriInfo.getPath(), uriInfo.getPathParameters(), request,
                    response);

            consentMgtApiHandler.handleUpdateConsentStatus(consentMgtDTO);
            return sendResponse(consentMgtDTO);

        } catch (Exception e) {
            log.error("Error occurred while updating consent data", e);
            return Response.ok(e).build();
        }
    }

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
            log.debug("Response status or payload unavailable. Throwing exception");
            throw new ConsentException(ResponseStatus.INTERNAL_SERVER_ERROR, "Response data unavailable");
        }
    }
}
