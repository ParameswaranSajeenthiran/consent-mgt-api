package org.wso2.financial.services.accelerator.consent.mgt.endpoint;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * JAX-RS application class.
 */
@ApplicationPath("/api")  // Base path for all REST services
public class RestApplication extends Application {
    // No need to override methods unless you want to configure specific classes
}

