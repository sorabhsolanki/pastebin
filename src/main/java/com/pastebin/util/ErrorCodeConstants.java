package com.pastebin.util;

public enum ErrorCodeConstants {

  UNAUTHORIZED(401),
  FORBIDDEN(403),
  NOT_FOUND(404),

  CONFLICT_01(409_01),
  CONFLICT_02(409_02),
  UNPROCESSABLE_ENTITY(422),
  INTERNAL_SERVER_ERROR(500),
  // from 999 on-wards teh
  TOKEN_EXPIRED(999),
  INVALID_TOKEN(1000),
  INVALID_APP_TOKEN(1001),
  INVALID_OTP(1001),
  OTP_LIMIT_REACHED(1002),
  UNSUPPORTED_BACKEND_OPERATION(1003);

  private int errorCode;

  ErrorCodeConstants(int errorCode) {
    this.errorCode = errorCode;
  }

  public int getErrorCode() {
    return errorCode;
  }
}
