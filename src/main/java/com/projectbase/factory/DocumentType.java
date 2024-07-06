package com.projectbase.factory;

public enum DocumentType{

    PRODUCT_IMAGE("no-auth");

    private final String authentication;

    DocumentType(String authentication){
        this.authentication = authentication;
    }

    public String getAuthentication(){
        return authentication;
    }
}

