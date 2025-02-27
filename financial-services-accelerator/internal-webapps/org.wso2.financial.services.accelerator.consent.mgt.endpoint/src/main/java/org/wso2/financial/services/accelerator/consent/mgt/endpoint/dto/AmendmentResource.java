package org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;



@JsonTypeName("AmendmentResource")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-27T12:52:14.361760449+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class AmendmentResource   {
  private String receipt;
  private Integer validityPeriod;
  private Map<String,String> consentAttributes;
  private @Valid List<@Valid ReauthorizeResource> authorizationResources = new ArrayList<>();
  private String currentStatus;

  public AmendmentResource() {
  }

  /**
   **/
  public AmendmentResource receipt(String receipt) {
    this.receipt = receipt;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("receipt")
  public String getReceipt() {
    return receipt;
  }

  @JsonProperty("receipt")
  public void setReceipt(String receipt) {
    this.receipt = receipt;
  }

  /**
   **/
  public AmendmentResource validityPeriod(Integer validityPeriod) {
    this.validityPeriod = validityPeriod;
    return this;
  }

  
  @ApiModelProperty(example = "3600", value = "")
  @JsonProperty("validityPeriod")
  public Integer getValidityPeriod() {
    return validityPeriod;
  }

  @JsonProperty("validityPeriod")
  public void setValidityPeriod(Integer validityPeriod) {
    this.validityPeriod = validityPeriod;
  }

  /**
   **/
  public AmendmentResource consentAttributes(Map<String,String> consentAttributes) {
    this.consentAttributes = consentAttributes;
    return this;
  }

  
  @ApiModelProperty(example = "{\"key1\":\"value1\"}", value = "")
  @JsonProperty("consentAttributes")
  public Map<String, String> getConsentAttributes() {
    return consentAttributes;
  }

  @JsonProperty("consentAttributes")
  public void setConsentAttributes(Map<String,String> consentAttributes) {
    this.consentAttributes = consentAttributes;
  }

  /**
   **/
  public AmendmentResource authorizationResources(List<@Valid ReauthorizeResource> authorizationResources) {
    this.authorizationResources = authorizationResources;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("authorizationResources")
  @Valid public List<@Valid ReauthorizeResource> getAuthorizationResources() {
    return authorizationResources;
  }

  @JsonProperty("authorizationResources")
  public void setAuthorizationResources(List<@Valid ReauthorizeResource> authorizationResources) {
    this.authorizationResources = authorizationResources;
  }

  public AmendmentResource addAuthorizationResourcesItem(ReauthorizeResource authorizationResourcesItem) {
    if (this.authorizationResources == null) {
      this.authorizationResources = new ArrayList<>();
    }

    this.authorizationResources.add(authorizationResourcesItem);
    return this;
  }

  public AmendmentResource removeAuthorizationResourcesItem(ReauthorizeResource authorizationResourcesItem) {
    if (authorizationResourcesItem != null && this.authorizationResources != null) {
      this.authorizationResources.remove(authorizationResourcesItem);
    }

    return this;
  }
  /**
   **/
  public AmendmentResource currentStatus(String currentStatus) {
    this.currentStatus = currentStatus;
    return this;
  }

  
  @ApiModelProperty(example = "awaitingAuthorization", value = "")
  @JsonProperty("currentStatus")
  public String getCurrentStatus() {
    return currentStatus;
  }

  @JsonProperty("currentStatus")
  public void setCurrentStatus(String currentStatus) {
    this.currentStatus = currentStatus;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AmendmentResource amendmentResource = (AmendmentResource) o;
    return Objects.equals(this.receipt, amendmentResource.receipt) &&
        Objects.equals(this.validityPeriod, amendmentResource.validityPeriod) &&
        Objects.equals(this.consentAttributes, amendmentResource.consentAttributes) &&
        Objects.equals(this.authorizationResources, amendmentResource.authorizationResources) &&
        Objects.equals(this.currentStatus, amendmentResource.currentStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(receipt, validityPeriod, consentAttributes, authorizationResources, currentStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AmendmentResource {\n");
    
    sb.append("    receipt: ").append(toIndentedString(receipt)).append("\n");
    sb.append("    validityPeriod: ").append(toIndentedString(validityPeriod)).append("\n");
    sb.append("    consentAttributes: ").append(toIndentedString(consentAttributes)).append("\n");
    sb.append("    authorizationResources: ").append(toIndentedString(authorizationResources)).append("\n");
    sb.append("    currentStatus: ").append(toIndentedString(currentStatus)).append("\n");
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

