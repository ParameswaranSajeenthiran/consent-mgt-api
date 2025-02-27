package org.wso2.financial.services.accelerator.consent.mgt.endpoint.dtoB;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;



@JsonTypeName("ConsentResourceDTO")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-26T11:05:28.364708637+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class ConsentResourceDTO   {

  private String clientId;
  private String consentType;
  private String consentStatus;
  private Object receipt;
  private Integer validityTime;
  private Boolean recurringIndicator;
  private Map<String,String> consentAttributes;
  private @Valid List<@Valid AuthResource> authorizations = new ArrayList<>();

  public ConsentResourceDTO() {
  }

  @JsonCreator
  public ConsentResourceDTO(
    @JsonProperty(required = true, value = "clientId" ) String clientId,
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
  public ConsentResourceDTO clientId(String clientId) {
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
  public ConsentResourceDTO consentType(String consentType) {
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
  public ConsentResourceDTO consentStatus(String consentStatus) {
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
  public ConsentResourceDTO receipt(Object receipt) {
    this.receipt = receipt;
    return this;
  }

  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty(required = true, value = "receipt")
  public Object getReceipt() {
    return receipt;
  }

  @JsonProperty(required = true, value = "receipt")
  public void setReceipt(Object receipt) {
    this.receipt = receipt;
  }

  /**
   **/
  public ConsentResourceDTO validityTime(Integer validityTime) {
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
  public ConsentResourceDTO recurringIndicator(Boolean recurringIndicator) {
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
  public ConsentResourceDTO consentAttributes(Map<String,String> consentAttributes) {
    this.consentAttributes = consentAttributes;
    return this;
  }

  
  @ApiModelProperty(value = "")
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
  public ConsentResourceDTO authorizations(List<@Valid AuthResource> authorizations) {
    this.authorizations = authorizations;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("authorizations")
  @Valid public List<@Valid AuthResource> getAuthorizations() {
    return authorizations;
  }

  @JsonProperty("authorizations")
  public void setAuthorizations(List<@Valid AuthResource> authorizations) {
    this.authorizations = authorizations;
  }

  public ConsentResourceDTO addAuthorizationsItem(AuthResource authorizationsItem) {
    if (this.authorizations == null) {
      this.authorizations = new ArrayList<>();
    }

    this.authorizations.add(authorizationsItem);
    return this;
  }

  public ConsentResourceDTO removeAuthorizationsItem(AuthResource authorizationsItem) {
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
    ConsentResourceDTO ConsentResourceDTO = (ConsentResourceDTO) o;
    return Objects.equals(this.clientId, ConsentResourceDTO.clientId) &&
        Objects.equals(this.consentType, ConsentResourceDTO.consentType) &&
        Objects.equals(this.consentStatus, ConsentResourceDTO.consentStatus) &&
        Objects.equals(this.receipt, ConsentResourceDTO.receipt) &&
        Objects.equals(this.validityTime, ConsentResourceDTO.validityTime) &&
        Objects.equals(this.recurringIndicator, ConsentResourceDTO.recurringIndicator) &&
        Objects.equals(this.consentAttributes, ConsentResourceDTO.consentAttributes) &&
        Objects.equals(this.authorizations, ConsentResourceDTO.authorizations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientId, consentType, consentStatus, receipt, validityTime, recurringIndicator, consentAttributes, authorizations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsentResourceDTO {\n");
    
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

