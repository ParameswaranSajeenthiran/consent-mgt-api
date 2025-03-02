package org.wso2.financial.services.accelerator.consent.mgt.endpoint.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Objects;


@JsonTypeName("ResourcePermission")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen",
        date = "2025-03-02T11:02:13.567940238+05:30[Asia/Colombo]", comments = "Generator version: 7.12.0")
public class ResourcePermission implements Serializable {
    private String accountID;
    private String permission;
    private Object resourceAttributes;

    public ResourcePermission() {
    }

    /**
     *
     **/
    public ResourcePermission accountID(String accountID) {
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
    public ResourcePermission permission(String permission) {
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
    public ResourcePermission resourceAttributes(Object resourceAttributes) {
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
        ResourcePermission resourcePermission = (ResourcePermission) o;
        return Objects.equals(this.accountID,
                resourcePermission.accountID) &&
                Objects.equals(this.permission,
                        resourcePermission.permission) &&
                Objects.equals(this.resourceAttributes,
                        resourcePermission.resourceAttributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID,
                permission,
                resourceAttributes);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResourcePermission {\n");

        sb.append("    accountID: ").append(toIndentedString(accountID)).append("\n");
        sb.append("    permission: ").append(toIndentedString(permission)).append("\n");
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

