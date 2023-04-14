package com.seeksolution.joy.Model;

import java.util.ArrayList;

public class CartoonResponse{
    public String code;
    public String message;
    public ArrayList<Cartoon> data;

    public CartoonResponse(String code, String message, ArrayList<Cartoon> data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Cartoon> getData() {
        return data;
    }
}
