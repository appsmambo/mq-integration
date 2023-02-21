package com.fulfillment.mqintegration.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "fulfillment-seller")
public class Seller {
    @DynamoDBHashKey(attributeName = "id_seller")
    private String id_seller;
    @DynamoDBAttribute
    private Boolean is_active_eom;

    @DynamoDBHashKey
    public String getId() {
        return id_seller;
    }

    @DynamoDBAttribute
    public Boolean getIsActiveEom() {
        return is_active_eom;
    }

    public void setId (String id_seller) {
        this.id_seller = id_seller;
    }

    public void setIsActiveEom (Boolean is_active_eom) {
        this.is_active_eom = is_active_eom;
    }
}
