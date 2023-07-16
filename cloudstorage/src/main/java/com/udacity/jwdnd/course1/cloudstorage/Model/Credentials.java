package com.udacity.jwdnd.course1.cloudstorage.Model;

public class Credentials {
    private Integer credentialid;
    private String url;
    private String username;
    private String password;
    private int userId;
    private String credentKey;

    public Credentials(Integer credentialid, String url, String username, String password, int userId, String credentKey) {
        this.credentialid = credentialid;
        this.url = url;
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.credentKey = credentKey;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCredentKey() {
        return credentKey;
    }

    public void setCredentKey(String credentKey) {
        this.credentKey = credentKey;
    }
}
