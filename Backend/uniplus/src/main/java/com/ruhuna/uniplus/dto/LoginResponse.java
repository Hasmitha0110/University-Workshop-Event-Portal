package com.ruhuna.uniplus.dto;

public class LoginResponse {

    private String token;
    private Long adminId;
    private String name;

    public LoginResponse() {}

    public LoginResponse(String token, Long adminId, String name) {
        this.token = token;
        this.adminId = adminId;
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
