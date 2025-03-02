package org.wso2.financial.services.accelerator.consent.mgt.endpoint.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@JsonTypeName("AuthResource")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen",
        date = "2025-03-02T11:02:13.567940238+05:30[Asia/Colombo]", comments = "Generator version: 7.12.0")
public class AuthResource implements Serializable {
    private String authorizationStatus;
    private String authorizationType;
    private String userId;
    private @Valid List<@Valid ResourcePermission> resources = new ArrayList<>();

    public AuthResource() {
    }

    @JsonCreator
    public AuthResource(
            @JsonProperty(required = true, value = "authorizationStatus") String authorizationStatus,
            @JsonProperty(required = true, value = "authorizationType") String authorizationType,
            @JsonProperty(required = true, value = "userId") String userId
                       ) {
        this.authorizationStatus = authorizationStatus;
        this.authorizationType = authorizationType;
        this.userId = userId;
    }

    /**
     *
     **/
    public AuthResource authorizationStatus(String authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
        return this;
    }


    @ApiModelProperty(example = "created", required = true, value = "")
    @JsonProperty(required = true, value = "authorizationStatus")
    @NotNull
    public String getAuthorizationStatus() {
        return authorizationStatus;
    }

    @JsonProperty(required = true, value = "authorizationStatus")
    public void setAuthorizationStatus(String authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }

    /**
     *
     **/
    public AuthResource authorizationType(String authorizationType) {
        this.authorizationType = authorizationType;
        return this;
    }


    @ApiModelProperty(example = "authorization", required = true, value = "")
    @JsonProperty(required = true, value = "authorizationType")
    @NotNull
    public String getAuthorizationType() {
        return authorizationType;
    }

    @JsonProperty(required = true, value = "authorizationType")
    public void setAuthorizationType(String authorizationType) {
        this.authorizationType = authorizationType;
    }

    /**
     *
     **/
    public AuthResource userId(String userId) {
        this.userId = userId;
        return this;
    }


    @ApiModelProperty(example = "psu@wso2.com", required = true, value = "")
    @JsonProperty(required = true, value = "userId")
    @NotNull
    public String getUserId() {
        return userId;
    }

    @JsonProperty(required = true, value = "userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     *
     **/
    public AuthResource resources(List<@Valid ResourcePermission> resources) {
        this.resources = resources;
        return this;
    }


    @ApiModelProperty(value = "")
    @JsonProperty("resources")
    @Valid
    public List<@Valid ResourcePermission> getResources() {
        return resources;
    }

    @JsonProperty("resources")
    public void setResources(List<@Valid ResourcePermission> resources) {
        this.resources = resources;
    }

    public AuthResource addResourcesItem(ResourcePermission resourcesItem) {
        if (this.resources == null) {
            this.resources = new ArrayList<>();
        }

        this.resources.add(resourcesItem);
        return this;
    }

    public AuthResource removeResourcesItem(ResourcePermission resourcesItem) {
        if (resourcesItem != null && this.resources != null) {
            this.resources.remove(resourcesItem);
        }

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthResource authResource = (AuthResource) o;
        return Objects.equals(this.authorizationStatus,
                authResource.authorizationStatus) &&
                Objects.equals(this.authorizationType,
                        authResource.authorizationType) &&
                Objects.equals(this.userId,
                        authResource.userId) &&
                Objects.equals(this.resources,
                        authResource.resources);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorizationStatus,
                authorizationType,
                userId,
                resources);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AuthResource {\n");

        sb.append("    authorizationStatus: ").append(toIndentedString(authorizationStatus)).append("\n");
        sb.append("    authorizationType: ").append(toIndentedString(authorizationType)).append("\n");
        sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
        sb.append("    resources: ").append(toIndentedString(resources)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n",
                "\n    ");
    }


}

