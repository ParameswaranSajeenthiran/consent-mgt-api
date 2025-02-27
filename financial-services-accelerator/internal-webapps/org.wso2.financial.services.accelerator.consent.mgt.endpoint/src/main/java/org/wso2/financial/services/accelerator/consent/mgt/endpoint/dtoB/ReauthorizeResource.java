package org.wso2.financial.services.accelerator.consent.mgt.endpoint.dtoB;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@JsonTypeName("ReauthorizeResource")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-26T11:05:28.364708637+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class ReauthorizeResource   {
  private String authId;
  private String userId;
  private String authStatus;
  private String authType;
  private @Valid List<@Valid Resource> resources = new ArrayList<>();

  public ReauthorizeResource() {
  }

  /**
   **/
  public ReauthorizeResource authId(String authId) {
    this.authId = authId;
    return this;
  }

  
  @ApiModelProperty(example = "5162ab67-b4e1-4e88-b429-f0bf34fae343", value = "")
  @JsonProperty("authId")
  public String getAuthId() {
    return authId;
  }

  @JsonProperty("authId")
  public void setAuthId(String authId) {
    this.authId = authId;
  }

  /**
   **/
  public ReauthorizeResource userId(String userId) {
    this.userId = userId;
    return this;
  }

  
  @ApiModelProperty(example = "psu@wso2.com", value = "")
  @JsonProperty("userId")
  public String getUserId() {
    return userId;
  }

  @JsonProperty("userId")
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   **/
  public ReauthorizeResource authStatus(String authStatus) {
    this.authStatus = authStatus;
    return this;
  }

  
  @ApiModelProperty(example = "created", value = "")
  @JsonProperty("authStatus")
  public String getAuthStatus() {
    return authStatus;
  }

  @JsonProperty("authStatus")
  public void setAuthStatus(String authStatus) {
    this.authStatus = authStatus;
  }

  /**
   **/
  public ReauthorizeResource authType(String authType) {
    this.authType = authType;
    return this;
  }

  
  @ApiModelProperty(example = "authorization", value = "")
  @JsonProperty("authType")
  public String getAuthType() {
    return authType;
  }

  @JsonProperty("authType")
  public void setAuthType(String authType) {
    this.authType = authType;
  }

  /**
   **/
  public ReauthorizeResource resources(List<@Valid Resource> resources) {
    this.resources = resources;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("resources")
  @Valid public List<@Valid Resource> getResources() {
    return resources;
  }

  @JsonProperty("resources")
  public void setResources(List<@Valid Resource> resources) {
    this.resources = resources;
  }

  public ReauthorizeResource addResourcesItem(Resource resourcesItem) {
    if (this.resources == null) {
      this.resources = new ArrayList<>();
    }

    this.resources.add(resourcesItem);
    return this;
  }

  public ReauthorizeResource removeResourcesItem(Resource resourcesItem) {
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
    ReauthorizeResource reauthorizeResource = (ReauthorizeResource) o;
    return Objects.equals(this.authId, reauthorizeResource.authId) &&
        Objects.equals(this.userId, reauthorizeResource.userId) &&
        Objects.equals(this.authStatus, reauthorizeResource.authStatus) &&
        Objects.equals(this.authType, reauthorizeResource.authType) &&
        Objects.equals(this.resources, reauthorizeResource.resources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authId, userId, authStatus, authType, resources);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ReauthorizeResource {\n");
    
    sb.append("    authId: ").append(toIndentedString(authId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    authStatus: ").append(toIndentedString(authStatus)).append("\n");
    sb.append("    authType: ").append(toIndentedString(authType)).append("\n");
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

