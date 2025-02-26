package org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.*;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;



@JsonTypeName("ConsentFile")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-26T11:05:28.364708637+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class ConsentFile   {
  private String consentId;
  private String consentFile;
  private String clientId;
  private String userId;
  private String applicableStatus;

  public ConsentFile() {
  }

  /**
   **/
  public ConsentFile consentId(String consentId) {
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
  public ConsentFile consentFile(String consentFile) {
    this.consentFile = consentFile;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("consentFile")
  public String getConsentFile() {
    return consentFile;
  }

  @JsonProperty("consentFile")
  public void setConsentFile(String consentFile) {
    this.consentFile = consentFile;
  }

  /**
   **/
  public ConsentFile clientId(String clientId) {
    this.clientId = clientId;
    return this;
  }

  
  @ApiModelProperty(example = "TUwYBlObBMmu7zvDnnhs96rZHxka", value = "")
  @JsonProperty("clientId")
  public String getClientId() {
    return clientId;
  }

  @JsonProperty("clientId")
  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  /**
   **/
  public ConsentFile userId(String userId) {
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
  public ConsentFile applicableStatus(String applicableStatus) {
    this.applicableStatus = applicableStatus;
    return this;
  }

  
  @ApiModelProperty(example = "awaitingAuthorization", value = "")
  @JsonProperty("applicableStatus")
  public String getApplicableStatus() {
    return applicableStatus;
  }

  @JsonProperty("applicableStatus")
  public void setApplicableStatus(String applicableStatus) {
    this.applicableStatus = applicableStatus;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConsentFile consentFile = (ConsentFile) o;
    return Objects.equals(this.consentId, consentFile.consentId) &&
        Objects.equals(this.consentFile, consentFile.consentFile) &&
        Objects.equals(this.clientId, consentFile.clientId) &&
        Objects.equals(this.userId, consentFile.userId) &&
        Objects.equals(this.applicableStatus, consentFile.applicableStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(consentId, consentFile, clientId, userId, applicableStatus);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsentFile {\n");
    
    sb.append("    consentId: ").append(toIndentedString(consentId)).append("\n");
    sb.append("    consentFile: ").append(toIndentedString(consentFile)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    applicableStatus: ").append(toIndentedString(applicableStatus)).append("\n");
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

