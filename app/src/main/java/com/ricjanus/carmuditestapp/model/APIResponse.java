package com.ricjanus.carmuditestapp.model;

import com.google.gson.annotations.SerializedName;

public class APIResponse {

    private boolean success;

    @SerializedName("metadata")
    private Result result;

    public boolean isSuccess() {
        return success;
    }

    public Result getResult() {
        return result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
