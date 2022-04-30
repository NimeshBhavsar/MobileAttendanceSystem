package com.krishna.attendance.Core.Enums;

public enum ResponseStatusEnum {
    /**
     * None: in case of user cancelling the request
     */
    None,
    Success,
    Fail,
    //Error // don't use error cause it's tedious to check for two or more than two values in Dialog callbacks
}
