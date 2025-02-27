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



@JsonTypeName("DetailedConsentResource")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-26T11:05:28.364708637+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class DetailedConsentResource   {
  private String consentId;
  private String clientId;
  private String consentType;
  private String consentStatus;
  private Object receipt;
  private Integer validityTime;
  private Boolean recurringIndicator;
  private Object consentAttributes;
  private @Valid List<@Valid AuthResponse> authorizations = new ArrayList<>();
  private Integer createdTime;
  private Integer updatedTime;

  public DetailedConsentResource() {
  }

  @JsonCreator
  public DetailedConsentResource(
    @JsonProperty(required = true, value = "clientId") String clientId,
    @JsonProperty(required = true, value = "consentType") String consentType,
    @JsonProperty(required = true, value = "consentStatus") String consentStatus,
    @JsonProperty(required = true, value = "receipt") Object receipt,
    @JsonProperty(required = true, value = "validityTime") Integer validityTime,
    @JsonProperty(required = true, value = "recurringIndicator") Boolean recurringIndicator
  ) {
    this.clientId = clientId;
    this.consentType = consentType;
    this.consentStatus = consentStatus;
    this.receipt = receipt;
    this.validityTime = validityTime;
    this.recurringIndicator = recurringIndicator;
  }

  /**
   **/
  public DetailedConsentResource consentId(String consentId) {
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
  public DetailedConsentResource clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  
  @ApiModelProperty(example = "TUwYBlObBMmu7zvDnnhs96rZHxka", required = true, value = "")
  @JsonProperty(required = true, value = "clientId")
  @NotNull public String getClientId() {
    return clientId;
  }

  @JsonProperty(required = true, value = "clientId")
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  /**
   **/
  public DetailedConsentResource consentType(String consentType) {
    this.consentType = consentType;
    return this;
  }

  
  @ApiModelProperty(example = "Accounts", required = true, value = "")
  @JsonProperty(required = true, value = "consentType")
  @NotNull public String getConsentType() {
    return consentType;
  }

  @JsonProperty(required = true, value = "consentType")
  public void setConsentType(String consentType) {
    this.consentType = consentType;
  }

  /**
   **/
  public DetailedConsentResource consentStatus(String consentStatus) {
    this.consentStatus = consentStatus;
    return this;
  }

  
  @ApiModelProperty(example = "awaitingAuthorisation", required = true, value = "")
  @JsonProperty(required = true, value = "consentStatus")
  @NotNull public String getConsentStatus() {
    return consentStatus;
  }

  @JsonProperty(required = true, value = "consentStatus")
  public void setConsentStatus(String consentStatus) {
    this.consentStatus = consentStatus;
  }

  /**
   **/
  public DetailedConsentResource receipt(Object receipt) {
    this.receipt = receipt;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(required = true, value = "receipt")
  @NotNull public Object getReceipt() {
    return receipt;
  }

  @JsonProperty(required = true, value = "receipt")
  public void setReceipt(Object receipt) {
    this.receipt = receipt;
  }

  /**
   **/
  public DetailedConsentResource validityTime(Integer validityTime) {
    this.validityTime = validityTime;
    return this;
  }

  
  @ApiModelProperty(example = "3600", required = true, value = "")
  @JsonProperty(required = true, value = "validityTime")
  @NotNull public Integer getValidityTime() {
    return validityTime;
  }

  @JsonProperty(required = true, value = "validityTime")
  public void setValidityTime(Integer validityTime) {
    this.validityTime = validityTime;
  }

  /**
   **/
  public DetailedConsentResource recurringIndicator(Boolean recurringIndicator) {
    this.recurringIndicator = recurringIndicator;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(required = true, value = "recurringIndicator")
  @NotNull public Boolean getRecurringIndicator() {
    return recurringIndicator;
  }

  @JsonProperty(required = true, value = "recurringIndicator")
  public void setRecurringIndicator(Boolean recurringIndicator) {
    this.recurringIndicator = recurringIndicator;
  }

  /**
   **/
  public DetailedConsentResource consentAttributes(Object consentAttributes) {
    this.consentAttributes = consentAttributes;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("consentAttributes")
  public Object getConsentAttributes() {
    return consentAttributes;
  }

  @JsonProperty("consentAttributes")
  public void setConsentAttributes(Object consentAttributes) {
    this.consentAttributes = consentAttributes;
  }

  /**
   **/
  public DetailedConsentResource authorizations(List<@Valid AuthResponse> authorizations) {
    this.authorizations = authorizations;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("authorizations")
  @Valid public List<@Valid AuthResponse> getAuthorizations() {
    return authorizations;
  }

  @JsonProperty("authorizations")
  public void setAuthorizations(List<@Valid AuthResponse> authorizations) {
    this.authorizations = authorizations;
  }

  public DetailedConsentResource addAuthorizationsItem(AuthResponse authorizationsItem) {
    if (this.authorizations == null) {
      this.authorizations = new ArrayList<>();
    }

    this.authorizations.add(authorizationsItem);
    return this;
  }

  public DetailedConsentResource removeAuthorizationsItem(AuthResponse authorizationsItem) {
    if (authorizationsItem != null && this.authorizations != null) {
      this.authorizations.remove(authorizationsItem);
    }

    return this;
  }
  /**
   **/
  public DetailedConsentResource createdTime(Integer createdTime) {
    this.createdTime = createdTime;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("createdTime")
  public Integer getCreatedTime() {
    return createdTime;
  }

  @JsonProperty("createdTime")
  public void setCreatedTime(Integer createdTime) {
    this.createdTime = createdTime;
  }

  /**
   **/
  public DetailedConsentResource updatedTime(Integer updatedTime) {
    this.updatedTime = updatedTime;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("updatedTime")
  public Integer getUpdatedTime() {
    return updatedTime;
  }

  @JsonProperty("updatedTime")
  public void setUpdatedTime(Integer updatedTime) {
    this.updatedTime = updatedTime;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DetailedConsentResource detailedConsentResource = (DetailedConsentResource) o;
    return Objects.equals(this.consentId, detailedConsentResource.consentId) &&
        Objects.equals(this.clientId, detailedConsentResource.clientId) &&
        Objects.equals(this.consentType, detailedConsentResource.consentType) &&
        Objects.equals(this.consentStatus, detailedConsentResource.consentStatus) &&
        Objects.equals(this.receipt, detailedConsentResource.receipt) &&
        Objects.equals(this.validityTime, detailedConsentResource.validityTime) &&
        Objects.equals(this.recurringIndicator, detailedConsentResource.recurringIndicator) &&
        Objects.equals(this.consentAttributes, detailedConsentResource.consentAttributes) &&
        Objects.equals(this.authorizations, detailedConsentResource.authorizations) &&
        Objects.equals(this.createdTime, detailedConsentResource.createdTime) &&
        Objects.equals(this.updatedTime, detailedConsentResource.updatedTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(consentId, clientId, consentType, consentStatus, receipt, validityTime, recurringIndicator, consentAttributes, authorizations, createdTime, updatedTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DetailedConsentResource {\n");
    
    sb.append("    consentId: ").append(toIndentedString(consentId)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    consentType: ").append(toIndentedString(consentType)).append("\n");
    sb.append("    consentStatus: ").append(toIndentedString(consentStatus)).append("\n");
    sb.append("    receipt: ").append(toIndentedString(receipt)).append("\n");
    sb.append("    validityTime: ").append(toIndentedString(validityTime)).append("\n");
    sb.append("    recurringIndicator: ").append(toIndentedString(recurringIndicator)).append("\n");
    sb.append("    consentAttributes: ").append(toIndentedString(consentAttributes)).append("\n");
    sb.append("    authorizations: ").append(toIndentedString(authorizations)).append("\n");
    sb.append("    createdTime: ").append(toIndentedString(createdTime)).append("\n");
    sb.append("    updatedTime: ").append(toIndentedString(updatedTime)).append("\n");
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

