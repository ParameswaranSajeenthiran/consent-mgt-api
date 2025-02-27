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



@JsonTypeName("AmendmentResponse")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-26T11:05:28.364708637+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class AmendmentResponse   {
  private String clientId;
  private String consentType;
  private String consentStatus;
  private Object receipt;
  private Integer validityTime;
  private Boolean recurringIndicator;
  private Object consentAttributes;
  private @Valid List<@Valid ReauthorizeResource> authorizations = new ArrayList<>();

  public AmendmentResponse() {
  }

  @JsonCreator
  public AmendmentResponse(
    @JsonProperty(required = true, value = "clientId") String clientId,
    @JsonProperty(required = true, value = "consentType") String consentType,
    @JsonProperty(required = true, value = "receipt") Object receipt,
    @JsonProperty(required = true, value = "validityTime") Integer validityTime,
    @JsonProperty(required = true, value = "recurringIndicator") Boolean recurringIndicator
  ) {
    this.clientId = clientId;
    this.consentType = consentType;
    this.receipt = receipt;
    this.validityTime = validityTime;
    this.recurringIndicator = recurringIndicator;
  }

  /**
   **/
  public AmendmentResponse clientId(String clientId) {
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
  public AmendmentResponse consentType(String consentType) {
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
  public AmendmentResponse consentStatus(String consentStatus) {
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

  /**
   **/
  public AmendmentResponse receipt(Object receipt) {
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
  public AmendmentResponse validityTime(Integer validityTime) {
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
  public AmendmentResponse recurringIndicator(Boolean recurringIndicator) {
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
  public AmendmentResponse consentAttributes(Object consentAttributes) {
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
  public AmendmentResponse authorizations(List<@Valid ReauthorizeResource> authorizations) {
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

  public AmendmentResponse addAuthorizationsItem(ReauthorizeResource authorizationsItem) {
    if (this.authorizations == null) {
      this.authorizations = new ArrayList<>();
    }

    this.authorizations.add(authorizationsItem);
    return this;
  }

  public AmendmentResponse removeAuthorizationsItem(ReauthorizeResource authorizationsItem) {
    if (authorizationsItem != null && this.authorizations != null) {
      this.authorizations.remove(authorizationsItem);
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
    AmendmentResponse amendmentResponse = (AmendmentResponse) o;
    return Objects.equals(this.clientId, amendmentResponse.clientId) &&
        Objects.equals(this.consentType, amendmentResponse.consentType) &&
        Objects.equals(this.consentStatus, amendmentResponse.consentStatus) &&
        Objects.equals(this.receipt, amendmentResponse.receipt) &&
        Objects.equals(this.validityTime, amendmentResponse.validityTime) &&
        Objects.equals(this.recurringIndicator, amendmentResponse.recurringIndicator) &&
        Objects.equals(this.consentAttributes, amendmentResponse.consentAttributes) &&
        Objects.equals(this.authorizations, amendmentResponse.authorizations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, consentType, consentStatus, receipt, validityTime, recurringIndicator, consentAttributes, authorizations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AmendmentResponse {\n");
    
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    consentType: ").append(toIndentedString(consentType)).append("\n");
    sb.append("    consentStatus: ").append(toIndentedString(consentStatus)).append("\n");
    sb.append("    receipt: ").append(toIndentedString(receipt)).append("\n");
    sb.append("    validityTime: ").append(toIndentedString(validityTime)).append("\n");
    sb.append("    recurringIndicator: ").append(toIndentedString(recurringIndicator)).append("\n");
    sb.append("    consentAttributes: ").append(toIndentedString(consentAttributes)).append("\n");
    sb.append("    authorizations: ").append(toIndentedString(authorizations)).append("\n");
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

