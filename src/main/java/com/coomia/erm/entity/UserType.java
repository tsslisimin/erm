package com.coomia.erm.entity;

public enum UserType {
  ADMIN((short) 1, "ROLE_ADMIN"), EB((short) 2, "ROLE_EB"), SCH((short) 3, "ROLE_SCH"), OPER(
      (short) 4,
      "ROLE_OPER"), STU((short) 5, "ROLE_STU"), PS((short) 6, "ROLE_PS"), AI((short) 7, "ROLE_AI");
  private short code;
  private String name;

  private UserType(short code, String name) {
    this.code = code;
    this.name = name;
  }

  public short getCode() {
    return code;
  }

  public void setCode(short code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
