package org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.*;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@JsonTypeName("AuthResource")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-26T11:05:28.364708637+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class AuthResource   {
  private String authStatus;
  private String authType;
  private String userId;
  private @Valid List<@Valid ResourcePermission> resources = new ArrayList<>();

  public AuthResource() {
  }

  @JsonCreator
  public AuthResource(
    @JsonProperty(required = true, value = "authStatus") String authStatus,
    @JsonProperty(required = true, value = "authType") String authType,
    @JsonProperty(required = true, value = "userId") String userId
  ) {
    this.authStatus = authStatus;
    this.authType = authType;
    this.userId = userId;
  }

  /**
   **/
  public AuthResource authStatus(String authStatus) {
    this.authStatus = authStatus;
    return this;
  }

  
  @ApiModelProperty(example = "created", required = true, value = "")
  @JsonProperty(required = true, value = "authStatus")
  @NotNull public String getAuthStatus() {
    return authStatus;
  }

  @JsonProperty(required = true, value = "authStatus")
  public void setAuthStatus(String authStatus) {
    this.authStatus = authStatus;
  }

  /**
   **/
  public AuthResource authType(String authType) {
    this.authType = authType;
    return this;
  }

  
  @ApiModelProperty(example = "authorization", required = true, value = "")
  @JsonProperty(required = true, value = "authType")
  @NotNull public String getAuthType() {
    return authType;
  }

  @JsonProperty(required = true, value = "authType")
  public void setAuthType(String authType) {
    this.authType = authType;
  }

  /**
   **/
  public AuthResource userId(String userId) {
    this.userId = userId;
    return this;
  }

  
  @ApiModelProperty(example = "psu@wso2.com", required = true, value = "")
  @JsonProperty(required = true, value = "userId")
  @NotNull public String getUserId() {
    return userId;
  }

  @JsonProperty(required = true, value = "userId")
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   **/
  public AuthResource resources(List<@Valid ResourcePermission> resources) {
    this.resources = resources;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("resources")
  @Valid public List<@Valid ResourcePermission> getResources() {
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
    return Objects.equals(this.authStatus, authResource.authStatus) &&
        Objects.equals(this.authType, authResource.authType) &&
        Objects.equals(this.userId, authResource.userId) &&
        Objects.equals(this.resources, authResource.resources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authStatus, authType, userId, resources);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthResource {\n");
    
    sb.append("    authStatus: ").append(toIndentedString(authStatus)).append("\n");
    sb.append("    authType: ").append(toIndentedString(authType)).append("\n");
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
    return o.toString().replace("\n", "\n    ");
  }


}

