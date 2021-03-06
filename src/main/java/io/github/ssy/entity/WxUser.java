package io.github.ssy.entity;

public class WxUser {

  private String openId;

  private String msg;

  private Integer state;

  private String response;

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public String getResponse() {
    return response;
  }

  public void setResponse(String response) {
    this.response = response;
  }

  @Override
  public String toString() {
    return "WxUser{" +
        "openId='" + openId + '\'' +
        ", msg='" + msg + '\'' +
        ", state=" + state +
        ", response='" + response + '\'' +
        '}';
  }
}
