package com.sms.dto.response;

public class LoginResponse {
    private String token;
    private String tokenType;
    private String adminId;
    private String username;
    private String role;

    public LoginResponse(){

    }
    public LoginResponse(String token, String tokenType, String adminId, String username, String role){
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
    public String getAdminId(){
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
    public void setAdminId(String adminId){
        this.adminId = adminId;
    }
    public void getUsername(String username){
        this.username = username;
    }
    public void setRole(String role){
        this.role = role;
    }

}
