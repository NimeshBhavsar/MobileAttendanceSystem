package com.krishna.attendance.Core.Models;

import com.krishna.attendance.Core.Enums.ResponseStatusEnum;

public class ResponseModel<T> {
    public ResponseStatusEnum status;
    public String message;
    public T data;

    public ResponseModel() {
    }


    public ResponseModel(ResponseStatusEnum status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseModel(ResponseStatusEnum status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResponseModel getNone() {
        return new ResponseModel(ResponseStatusEnum.None, "");
    }

    public static ResponseModel getSuccess() {
        return new ResponseModel(ResponseStatusEnum.Success, "");
    }

    public static ResponseModel getFail(String message) {
        return new ResponseModel(ResponseStatusEnum.Fail, message);
    }

    public static ResponseModel getSaveSuccess() {
        return getSuccess("Save success!");
    }

    public static ResponseModel getSuccess(String message) {
        return new ResponseModel(ResponseStatusEnum.Success, message);
    }

    public static ResponseModel getSaveSuccess(Object model) {
        return getSuccess("Save success!", model);
    }

    public static ResponseModel<Object> getSuccess(String message, Object model) {
        return new ResponseModel<>(ResponseStatusEnum.Success, message, model);
    }

    public static ResponseModel getDeleteSuccess() {
        return getSuccess("Delete success!");
    }

    public static ResponseModel getDeleteSuccess(Object model) {
        return getSuccess("Delete success!", model);
    }

    public static boolean shouldShowMessage(ResponseModel response) {
        if (response != null && !response.message.equals("")) {
            return true;
        }
        return false;
    }
}
