package org.wso2.financial.services.accelerator.consent.mgt.endpoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;


@JsonTypeName("Resource")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen",
        date = "2025-03-02T11:02:13.567940238+05:30[Asia/Colombo]", comments = "Generator version: 7.12.0")
public class Resource implements Serializable {
    private String accountID;
    private String permission;
    private String resourceMappingId;
    private String resourceMappingStatus;
    private Object resourceAttributes;

    public Resource() {
    }

    /**
     *
     **/
    public Resource accountID(String accountID) {
        this.accountID = accountID;
        return this;
    }


    @ApiModelProperty(example = "account1234", value = "")
    @JsonProperty("accountID")
    public String getAccountID() {
        return accountID;
    }

    @JsonProperty("accountID")
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    /**
     *
     **/
    public Resource permission(String permission) {
        this.permission = permission;
        return this;
    }


    @ApiModelProperty(example = "read", value = "")
    @JsonProperty("permission")
    public String getPermission() {
        return permission;
    }

    @JsonProperty("permission")
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     *
     **/
    public Resource resourceMappingId(String resourceMappingId) {
        this.resourceMappingId = resourceMappingId;
        return this;
    }


    @ApiModelProperty(example = "1242334", value = "")
    @JsonProperty("resourceMappingId")
    public String getResourceMappingId() {
        return resourceMappingId;
    }

    @JsonProperty("resourceMappingId")
    public void setResourceMappingId(String resourceMappingId) {
        this.resourceMappingId = resourceMappingId;
    }

    /**
     *
     **/
    public Resource resourceMappingStatus(String resourceMappingStatus) {
        this.resourceMappingStatus = resourceMappingStatus;
        return this;
    }


    @ApiModelProperty(example = "active", value = "")
    @JsonProperty("resourceMappingStatus")
    public String getResourceMappingStatus() {
        return resourceMappingStatus;
    }

    @JsonProperty("resourceMappingStatus")
    public void setResourceMappingStatus(String resourceMappingStatus) {
        this.resourceMappingStatus = resourceMappingStatus;
    }

    /**
     *
     **/
    public Resource resourceAttributes(Object resourceAttributes) {
        this.resourceAttributes = resourceAttributes;
        return this;
    }


    @ApiModelProperty(value = "")
    @JsonProperty("resourceAttributes")
    public Object getResourceAttributes() {
        return resourceAttributes;
    }

    @JsonProperty("resourceAttributes")
    public void setResourceAttributes(Object resourceAttributes) {
        this.resourceAttributes = resourceAttributes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resource resource = (Resource) o;
        return Objects.equals(this.accountID,
                resource.accountID) &&
                Objects.equals(this.permission,
                        resource.permission) &&
                Objects.equals(this.resourceMappingId,
                        resource.resourceMappingId) &&
                Objects.equals(this.resourceMappingStatus,
                        resource.resourceMappingStatus) &&
                Objects.equals(this.resourceAttributes,
                        resource.resourceAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID,
                permission,
                resourceMappingId,
                resourceMappingStatus,
                resourceAttributes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Resource {\n");

        sb.append("    accountID: ").append(toIndentedString(accountID)).append("\n");
        sb.append("    permission: ").append(toIndentedString(permission)).append("\n");
        sb.append("    resourceMappingId: ").append(toIndentedString(resourceMappingId)).append("\n");
        sb.append("    resourceMappingStatus: ").append(toIndentedString(resourceMappingStatus)).append("\n");
        sb.append("    resourceAttributes: ").append(toIndentedString(resourceAttributes)).append("\n");
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
        return o.toString().replace("\n",
                "\n    ");
    }


}

