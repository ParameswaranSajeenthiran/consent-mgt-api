package org.wso2.financial.services.accelerator.consent.mgt.endpoint.impl.utils;

import org.wso2.financial.services.accelerator.consent.mgt.dao.models.AuthorizationResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.ConsentMappingResource;
import org.wso2.financial.services.accelerator.consent.mgt.dao.models.DetailedConsentResource;

import java.util.ArrayList;

public class ConsentAPITestData {

    public static String testConsentID = "testConsentID";
    public static String testClientID = "testClientID";
    public static String testUserID = "testUser";
    public static String testConsentType = "testType";
    public static String testConsentStatus = "testStatus";
    public static String testReceipt = "testReceipt";
    public static int testValidityPeriod = 10;

    // authorization resource
    public static String testAuthStatus = "testAuthStatus";
    public static String testAuthId = "testAuthId";
    public static String testAuthType = "testAuthType";

    // detailed consent mapping
    public static String testMappingId = "testMappingId";
    public static String testMappingStatus = "testMappingStatus";
    public static String testMappingAccountId = "testMappingAccountId";
    public static String testMappingPermission = "testMappingPermision";


    public static AuthorizationResource testStoredAuthorizationResource = getStoredAuthorizationResource();

    // mock DetailedConsentResource
    public static DetailedConsentResource getStoredDetailedConsentResource() {
        DetailedConsentResource detailedConsentResource = new DetailedConsentResource();
        detailedConsentResource.setClientID(testClientID);
        detailedConsentResource.setReceipt(testReceipt);
        detailedConsentResource.setConsentID(testConsentID);
        detailedConsentResource.setConsentType(testConsentType);
        detailedConsentResource.setCurrentStatus(testConsentStatus);
        detailedConsentResource.setValidityPeriod(testValidityPeriod);
        ArrayList<AuthorizationResource> authorizationResources = new ArrayList<>();
        authorizationResources.add(getStoredAuthorizationResource());
        detailedConsentResource.setAuthorizationResources(authorizationResources);
        return detailedConsentResource;
    }

    ;

    // mock AuthorizationResource
    public static AuthorizationResource getStoredAuthorizationResource() {
        AuthorizationResource authorizationResource = new AuthorizationResource();
        authorizationResource.setAuthorizationID(testAuthId);
        authorizationResource.setUserID(testUserID);
        authorizationResource.setAuthorizationType(testAuthType);
        authorizationResource.setAuthorizationStatus(testAuthStatus);
        ArrayList<ConsentMappingResource> consentMappingResources = new ArrayList<>();
        consentMappingResources.add(getStoredConsentMappingResource());
        authorizationResource.setConsentMappingResource(consentMappingResources);
        return authorizationResource;
    }

    // get stored consent mapping resource
    public static ConsentMappingResource getStoredConsentMappingResource() {
        ConsentMappingResource consentMappingResource = new ConsentMappingResource();
        consentMappingResource.setMappingID(testMappingId);
        consentMappingResource.setAccountID(testMappingAccountId);
        consentMappingResource.setPermission(testMappingPermission);

        return consentMappingResource;
    }

}
