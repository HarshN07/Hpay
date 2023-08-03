package com.example.hpay;

public class ApiResponse {

    private boolean success;
    private String message;
    private String splitId;

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    boolean isSuccess(){
        return success;
    }
    private void setSuccess(boolean success){
        this.success=success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSplitId() {
        return splitId;
    }

    public void setSplitId(String splitId) {
        this.splitId = splitId;
    }
}
