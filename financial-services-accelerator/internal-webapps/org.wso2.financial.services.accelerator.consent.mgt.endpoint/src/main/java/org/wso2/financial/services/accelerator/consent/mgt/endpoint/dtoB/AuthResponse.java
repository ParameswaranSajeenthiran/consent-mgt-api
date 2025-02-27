package org.wso2.financial.services.accelerator.consent.mgt.endpoint.dtoB;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@JsonTypeName("AuthResponse")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-26T11:05:28.364708637+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class AuthResponse   {
  private String authId;
  private String authStatus;
  private String authType;
  private String userId;
  private @Valid List<@Valid Resource> resources = new ArrayList<>();

  public AuthResponse() {
  }

  @JsonCreator
  public AuthResponse(
    @JsonProperty(required = true, value = "authId") String authId,
    @JsonProperty(required = true, value = "authStatus") String authStatus,
    @JsonProperty(required = true, value = "authType") String authType,
    @JsonProperty(required = true, value = "userId") String userId
  ) {
    this.authId = authId;
    this.authStatus = authStatus;
    this.authType = authType;
    this.userId = userId;
  }

  /**
   **/
  public AuthResponse authId(String authId) {
    this.authId = authId;
    return this;
  }

  
  @ApiModelProperty(example = "5162ab67-b4e1-4e88-b429-f0bf34fae343", required = true, value = "")
  @JsonProperty(required = true, value = "authId")
  @NotNull public String getAuthId() {
    return authId;
  }

  @JsonProperty(required = true, value = "authId")
  public void setAuthId(String authId) {
    this.authId = authId;
  }

  /**
   **/
  public AuthResponse authStatus(String authStatus) {
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
  public AuthResponse authType(String authType) {
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
  public AuthResponse userId(String userId) {
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
  public AuthResponse resources(List<@Valid Resource> resources) {
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

  public AuthResponse addResourcesItem(Resource resourcesItem) {
    if (this.resources == null) {
      this.resources = new ArrayList<>();
    }

    this.resources.add(resourcesItem);
    return this;
  }

  public AuthResponse removeResourcesItem(Resource resourcesItem) {
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
    AuthResponse authResponse = (AuthResponse) o;
    return Objects.equals(this.authId, authResponse.authId) &&
        Objects.equals(this.authStatus, authResponse.authStatus) &&
        Objects.equals(this.authType, authResponse.authType) &&
        Objects.equals(this.userId, authResponse.userId) &&
        Objects.equals(this.resources, authResponse.resources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(authId, authStatus, authType, userId, resources);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthResponse {\n");
    
    sb.append("    authId: ").append(toIndentedString(authId)).append("\n");
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

