package org.wso2.financial.services.accelerator.consent.mgt.endpoint.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;
import javax.validation.constraints.*;
import javax.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("BulkConsentStatusUpdateResource")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-03-01T15:45:20.830641389+05:30[Asia/Colombo]", comments = "Generator version: 7.12.0")
public class BulkConsentStatusUpdateResource  implements Serializable {
  private String clientID;
  private String userId;
  private String consentType;
  private @Valid List<String> applicableStatusesForStateChange = new ArrayList<>();
  private String status;
  private String reason;

  public BulkConsentStatusUpdateResource() {
  }

  /**
   **/
  public BulkConsentStatusUpdateResource clientID(String clientID) {
    this.clientID = clientID;
    return this;
  }

  
  @ApiModelProperty(example = "TUwYBlObBMmu7zvDnnhs96rZHxka", value = "")
  @JsonProperty("clientID")
  public String getClientID() {
    return clientID;
  }

  @JsonProperty("clientID")
  public void setClientID(String clientID) {
    this.clientID = clientID;
  }

  /**
   **/
  public BulkConsentStatusUpdateResource userId(String userId) {
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
  public BulkConsentStatusUpdateResource consentType(String consentType) {
    this.consentType = consentType;
    return this;
  }

  
  @ApiModelProperty(example = "Accounts", value = "")
  @JsonProperty("consentType")
  public String getConsentType() {
    return consentType;
  }

  @JsonProperty("consentType")
  public void setConsentType(String consentType) {
    this.consentType = consentType;
  }

  /**
   **/
  public BulkConsentStatusUpdateResource applicableStatusesForStateChange(List<String> applicableStatusesForStateChange) {
    this.applicableStatusesForStateChange = applicableStatusesForStateChange;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("applicableStatusesForStateChange")
  public List<String> getApplicableStatusesForStateChange() {
    return applicableStatusesForStateChange;
  }

  @JsonProperty("applicableStatusesForStateChange")
  public void setApplicableStatusesForStateChange(List<String> applicableStatusesForStateChange) {
    this.applicableStatusesForStateChange = applicableStatusesForStateChange;
  }

  public BulkConsentStatusUpdateResource addApplicableStatusesForStateChangeItem(String applicableStatusesForStateChangeItem) {
    if (this.applicableStatusesForStateChange == null) {
      this.applicableStatusesForStateChange = new ArrayList<>();
    }

    this.applicableStatusesForStateChange.add(applicableStatusesForStateChangeItem);
    return this;
  }

  public BulkConsentStatusUpdateResource removeApplicableStatusesForStateChangeItem(String applicableStatusesForStateChangeItem) {
    if (applicableStatusesForStateChangeItem != null && this.applicableStatusesForStateChange != null) {
      this.applicableStatusesForStateChange.remove(applicableStatusesForStateChangeItem);
    }

    return this;
  }
  /**
   **/
  public BulkConsentStatusUpdateResource status(String status) {
    this.status = status;
    return this;
  }

  
  @ApiModelProperty(example = "revoked", value = "")
  @JsonProperty("status")
  public String getStatus() {
    return status;
  }

  @JsonProperty("status")
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   **/
  public BulkConsentStatusUpdateResource reason(String reason) {
    this.reason = reason;
    return this;
  }

  
  @ApiModelProperty(example = "Revoked by ...", value = "")
  @JsonProperty("reason")
  public String getReason() {
    return reason;
  }

  @JsonProperty("reason")
  public void setReason(String reason) {
    this.reason = reason;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BulkConsentStatusUpdateResource bulkConsentStatusUpdateResource = (BulkConsentStatusUpdateResource) o;
    return Objects.equals(this.clientID, bulkConsentStatusUpdateResource.clientID) &&
        Objects.equals(this.userId, bulkConsentStatusUpdateResource.userId) &&
        Objects.equals(this.consentType, bulkConsentStatusUpdateResource.consentType) &&
        Objects.equals(this.applicableStatusesForStateChange, bulkConsentStatusUpdateResource.applicableStatusesForStateChange) &&
        Objects.equals(this.status, bulkConsentStatusUpdateResource.status) &&
        Objects.equals(this.reason, bulkConsentStatusUpdateResource.reason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientID, userId, consentType, applicableStatusesForStateChange, status, reason);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BulkConsentStatusUpdateResource {\n");
    
    sb.append("    clientID: ").append(toIndentedString(clientID)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    consentType: ").append(toIndentedString(consentType)).append("\n");
    sb.append("    applicableStatusesForStateChange: ").append(toIndentedString(applicableStatusesForStateChange)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
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

