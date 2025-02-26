package org.wso2.financial.services.accelerator.consent.mgt.endpoint.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.*;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.util.Objects;



@JsonTypeName("ConsentHistory")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-02-26T11:05:28.364708637+05:30[Asia/Colombo]", comments = "Generator version: 7.11.0")
public class ConsentHistory   {
  private String historyId;
  private String consentId;
  private Integer timestamp;
  private String reason;
  private DetailedConsentResource detailedConsent;
  private Object changedMetadata;

  public ConsentHistory() {
  }

  /**
   **/
  public ConsentHistory historyId(String historyId) {
    this.historyId = historyId;
    return this;
  }

  
  @ApiModelProperty(example = "604d9278-4c3b-45d5-b3bb-1e428acdf1ec", value = "")
  @JsonProperty("historyId")
  public String getHistoryId() {
    return historyId;
  }

  @JsonProperty("historyId")
  public void setHistoryId(String historyId) {
    this.historyId = historyId;
  }

  /**
   **/
  public ConsentHistory consentId(String consentId) {
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
  public ConsentHistory timestamp(Integer timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("timestamp")
  public Integer getTimestamp() {
    return timestamp;
  }

  @JsonProperty("timestamp")
  public void setTimestamp(Integer timestamp) {
    this.timestamp = timestamp;
  }

  /**
   **/
  public ConsentHistory reason(String reason) {
    this.reason = reason;
    return this;
  }

  
  @ApiModelProperty(example = "amended due to ...", value = "")
  @JsonProperty("reason")
  public String getReason() {
    return reason;
  }

  @JsonProperty("reason")
  public void setReason(String reason) {
    this.reason = reason;
  }

  /**
   **/
  public ConsentHistory detailedConsent(DetailedConsentResource detailedConsent) {
    this.detailedConsent = detailedConsent;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("detailedConsent")
  @Valid public DetailedConsentResource getDetailedConsent() {
    return detailedConsent;
  }

  @JsonProperty("detailedConsent")
  public void setDetailedConsent(DetailedConsentResource detailedConsent) {
    this.detailedConsent = detailedConsent;
  }

  /**
   **/
  public ConsentHistory changedMetadata(Object changedMetadata) {
    this.changedMetadata = changedMetadata;
    return this;
  }

  
  @ApiModelProperty(value = "")
  @JsonProperty("changedMetadata")
  public Object getChangedMetadata() {
    return changedMetadata;
  }

  @JsonProperty("changedMetadata")
  public void setChangedMetadata(Object changedMetadata) {
    this.changedMetadata = changedMetadata;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConsentHistory consentHistory = (ConsentHistory) o;
    return Objects.equals(this.historyId, consentHistory.historyId) &&
        Objects.equals(this.consentId, consentHistory.consentId) &&
        Objects.equals(this.timestamp, consentHistory.timestamp) &&
        Objects.equals(this.reason, consentHistory.reason) &&
        Objects.equals(this.detailedConsent, consentHistory.detailedConsent) &&
        Objects.equals(this.changedMetadata, consentHistory.changedMetadata);
  }

  @Override
  public int hashCode() {
    return Objects.hash(historyId, consentId, timestamp, reason, detailedConsent, changedMetadata);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsentHistory {\n");
    
    sb.append("    historyId: ").append(toIndentedString(historyId)).append("\n");
    sb.append("    consentId: ").append(toIndentedString(consentId)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    reason: ").append(toIndentedString(reason)).append("\n");
    sb.append("    detailedConsent: ").append(toIndentedString(detailedConsent)).append("\n");
    sb.append("    changedMetadata: ").append(toIndentedString(changedMetadata)).append("\n");
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

