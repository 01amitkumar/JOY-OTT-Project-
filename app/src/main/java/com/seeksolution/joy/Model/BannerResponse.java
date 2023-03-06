package com.seeksolution.joy.Model;

import java.util.ArrayList;

public class BannerResponse {

    public String code;
    public String message;
    public ArrayList<BannerModel> data;
    public boolean error;
    public boolean status;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<BannerModel> getData() {
        return data;
    }

    public boolean isError() {
        return error;
    }

    public boolean isStatus() {
        return status;
    }

    public BannerResponse(String code, String message, ArrayList<BannerModel> data, boolean error, boolean status) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.error = error;
        this.status = status;


    }
}

