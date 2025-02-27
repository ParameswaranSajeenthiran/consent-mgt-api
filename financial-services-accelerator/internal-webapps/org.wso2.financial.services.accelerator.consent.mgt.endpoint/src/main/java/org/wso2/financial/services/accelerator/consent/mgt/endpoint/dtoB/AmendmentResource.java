package org.wso2.financial.services.accelerator.consent.mgt.endpoint.dtoB;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;



@JsonTypeName("AmendmentResource")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-26T11:05:28.364708637+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class AmendmentResource   {
  private Object receipt;
  private Integer validityTime;
  private Map<String,String> consentAttributes;
  private @Valid List<@Valid ReauthorizeResource> authorizations = new ArrayList<>();
  private String consentStatus;

  public AmendmentResource() {
  }

  /**
   **/
  public AmendmentResource receipt(Object receipt) {
    this.receipt = receipt;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("receipt")
  public Object getReceipt() {
    return receipt;
  }

  @JsonProperty("receipt")
  public void setReceipt(Object receipt) {
    this.receipt = receipt;
  }

  /**
   **/
  public AmendmentResource validityTime(Integer validityTime) {
    this.validityTime = validityTime;
    return this;
  }

  
  @ApiModelProperty(example = "3600", value = "")
  @JsonProperty("validityTime")
  public Integer getValidityTime() {
    return validityTime;
  }

  @JsonProperty("validityTime")
  public void setValidityTime(Integer validityTime) {
    this.validityTime = validityTime;
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
  public AmendmentResource authorizations(List<@Valid ReauthorizeResource> authorizations) {
    this.authorizations = authorizations;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("authorizations")
  @Valid public List<@Valid ReauthorizeResource> getAuthorizations() {
    return authorizations;
  }

  @JsonProperty("authorizations")
  public void setAuthorizations(List<@Valid ReauthorizeResource> authorizations) {
    this.authorizations = authorizations;
  }

  public AmendmentResource addAuthorizationsItem(ReauthorizeResource authorizationsItem) {
    if (this.authorizations == null) {
      this.authorizations = new ArrayList<>();
    }

    this.authorizations.add(authorizationsItem);
    return this;
  }

  public AmendmentResource removeAuthorizationsItem(ReauthorizeResource authorizationsItem) {
    if (authorizationsItem != null && this.authorizations != null) {
      this.authorizations.remove(authorizationsItem);
    }

    return this;
  }
  /**
   **/
  public AmendmentResource consentStatus(String consentStatus) {
    this.consentStatus = consentStatus;
    return this;
  }

  
  @ApiModelProperty(example = "awaitingAuthorization", value = "")
  @JsonProperty("consentStatus")
  public String getConsentStatus() {
    return consentStatus;
  }

  @JsonProperty("consentStatus")
  public void setConsentStatus(String consentStatus) {
    this.consentStatus = consentStatus;
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
        Objects.equals(this.validityTime, amendmentResource.validityTime) &&
        Objects.equals(this.consentAttributes, amendmentResource.consentAttributes) &&
        Objects.equals(this.authorizations, amendmentResource.authorizations) &&
        Objects.equals(this.consentStatus, amendmentResource.consentStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(receipt, validityTime, consentAttributes, authorizations, consentStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AmendmentResource {\n");
    
    sb.append("    receipt: ").append(toIndentedString(receipt)).append("\n");
    sb.append("    validityTime: ").append(toIndentedString(validityTime)).append("\n");
    sb.append("    consentAttributes: ").append(toIndentedString(consentAttributes)).append("\n");
    sb.append("    authorizations: ").append(toIndentedString(authorizations)).append("\n");
    sb.append("    consentStatus: ").append(toIndentedString(consentStatus)).append("\n");
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

