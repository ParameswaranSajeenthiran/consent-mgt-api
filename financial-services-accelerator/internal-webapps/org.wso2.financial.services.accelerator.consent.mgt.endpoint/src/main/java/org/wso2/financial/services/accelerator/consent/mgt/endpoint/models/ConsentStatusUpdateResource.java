package org.wso2.financial.services.accelerator.consent.mgt.endpoint.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.*;
import javax.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("ConsentStatusUpdateResource")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-03-01T15:45:20.830641389+05:30[Asia/Colombo]", comments = "Generator version: 7.12.0")
public class ConsentStatusUpdateResource  implements Serializable {
  private String userId;
  private String status;
  private String reason;

  public ConsentStatusUpdateResource() {
  }

  /**
   **/
  public ConsentStatusUpdateResource userId(String userId) {
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
  public ConsentStatusUpdateResource status(String status) {
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
  public ConsentStatusUpdateResource reason(String reason) {
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
    ConsentStatusUpdateResource consentStatusUpdateResource = (ConsentStatusUpdateResource) o;
    return Objects.equals(this.userId, consentStatusUpdateResource.userId) &&
        Objects.equals(this.status, consentStatusUpdateResource.status) &&
        Objects.equals(this.reason, consentStatusUpdateResource.reason);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, status, reason);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsentStatusUpdateResource {\n");
    
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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

