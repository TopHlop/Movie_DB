package com.example.themoviedb.main.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteSessionResponseWrap {
    @SerializedName("success")
    @Expose
    private boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
