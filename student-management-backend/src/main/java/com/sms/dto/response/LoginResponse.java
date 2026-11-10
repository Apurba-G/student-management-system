package com.sms.dto.response;

public class LoginResponse {
    private String token;
    private String tokenType;
    private long adminId;
    private String username;
    private String role;

    public LoginResponse(){

    }
    public LoginResponse(String token, String tokenType, Long adminId, String username, String role){
        this.token = token;
        this.tokenType = tokenType;
        this.adminId = adminId;
        this.username = username;
        this.role = role;
    }
    public String getToken(){
        return token;
    }
    public String getTokenType(){
        return tokenType;
    }
    public Long getAdminId(){
        return adminId;
    }
    public String getUsername(){
        return username;
    }
    public String getRole(){
        return role;
    }


    public void setToken(String token){
        this.token = token;
    }
    public void setTokenType(String tokenType){
        this.tokenType=tokenType;
    }
    public void setAdminId(Long adminId){
        this.adminId = adminId;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setRole(String role){
        this.role = role;
    }

}
