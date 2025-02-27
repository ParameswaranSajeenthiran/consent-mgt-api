package org.wso2.financial.services.accelerator.consent.mgt.endpoint.dtoB;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@JsonTypeName("ConsentUserMapping")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-26T11:05:28.364708637+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class ConsentUserMapping   {
  private String consentId;
  private String newConsentStatus;
  private AuthResponse authResource;
  private @Valid List<@Valid ResourcePermission> resources = new ArrayList<>();

  public ConsentUserMapping() {
  }

  /**
   **/
  public ConsentUserMapping consentId(String consentId) {
    this.consentId = consentId;
    return this;
  }

  
  @ApiModelProperty(example = "604d9278-4c3b-45d5-b3bb-1e428acdf1ec", value = "")
  @JsonProperty("consentId")
  public String getConsentId() {
    return consentId;
  }

  @JsonProperty("consentId")
  public void setConsentId(String consentId) {
    this.consentId = consentId;
  }

  /**
   **/
  public ConsentUserMapping newConsentStatus(String newConsentStatus) {
    this.newConsentStatus = newConsentStatus;
    return this;
  }

  
  @ApiModelProperty(example = "created", value = "")
  @JsonProperty("newConsentStatus")
  public String getNewConsentStatus() {
    return newConsentStatus;
  }

  @JsonProperty("newConsentStatus")
  public void setNewConsentStatus(String newConsentStatus) {
    this.newConsentStatus = newConsentStatus;
  }

  /**
   **/
  public ConsentUserMapping authResource(AuthResponse authResource) {
    this.authResource = authResource;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("authResource")
  @Valid public AuthResponse getAuthResource() {
    return authResource;
  }

  @JsonProperty("authResource")
  public void setAuthResource(AuthResponse authResource) {
    this.authResource = authResource;
  }

  /**
   **/
  public ConsentUserMapping resources(List<@Valid ResourcePermission> resources) {
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

  public ConsentUserMapping addResourcesItem(ResourcePermission resourcesItem) {
    if (this.resources == null) {
      this.resources = new ArrayList<>();
    }

    this.resources.add(resourcesItem);
    return this;
  }

  public ConsentUserMapping removeResourcesItem(ResourcePermission resourcesItem) {
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
    ConsentUserMapping consentUserMapping = (ConsentUserMapping) o;
    return Objects.equals(this.consentId, consentUserMapping.consentId) &&
        Objects.equals(this.newConsentStatus, consentUserMapping.newConsentStatus) &&
        Objects.equals(this.authResource, consentUserMapping.authResource) &&
        Objects.equals(this.resources, consentUserMapping.resources);
  }

  @Override
  public int hashCode() {
    return Objects.hash(consentId, newConsentStatus, authResource, resources);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsentUserMapping {\n");
    
    sb.append("    consentId: ").append(toIndentedString(consentId)).append("\n");
    sb.append("    newConsentStatus: ").append(toIndentedString(newConsentStatus)).append("\n");
    sb.append("    authResource: ").append(toIndentedString(authResource)).append("\n");
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

