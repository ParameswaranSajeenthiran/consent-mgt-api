package org.wso2.financial.services.accelerator.consent.mgt.endpoint.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.wso2.financial.services.accelerator.consent.mgt.endpoint.models.AuthResponse;
import java.io.Serializable;
import javax.validation.constraints.*;
import javax.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonTypeName;



@JsonTypeName("ConsentUserMappingResponse")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2025-03-01T15:45:20.830641389+05:30[Asia/Colombo]", comments = "Generator version: 7.12.0")
public class ConsentUserMappingResponse  implements Serializable {
  private String consentId;
  private AuthResponse authResource;
  private Integer updatedTime;

  public ConsentUserMappingResponse() {
  }

  /**
   **/
  public ConsentUserMappingResponse consentId(String consentId) {
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
  public ConsentUserMappingResponse authResource(AuthResponse authResource) {
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
  public ConsentUserMappingResponse updatedTime(Integer updatedTime) {
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
    ConsentUserMappingResponse consentUserMappingResponse = (ConsentUserMappingResponse) o;
    return Objects.equals(this.consentId, consentUserMappingResponse.consentId) &&
        Objects.equals(this.authResource, consentUserMappingResponse.authResource) &&
        Objects.equals(this.updatedTime, consentUserMappingResponse.updatedTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(consentId, authResource, updatedTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConsentUserMappingResponse {\n");
    
    sb.append("    consentId: ").append(toIndentedString(consentId)).append("\n");
    sb.append("    authResource: ").append(toIndentedString(authResource)).append("\n");
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

