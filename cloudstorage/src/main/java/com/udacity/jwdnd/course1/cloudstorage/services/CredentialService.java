package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {
    CredentialMapper credentialMapper;
    UserMapper userMapper;

    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
    }

    public void insertCredential(String userName, String url, String credentialUsername, String credentialKey, String password){
        int userId = userMapper.getUser(userName).getUserId();
        Credentials credentials = new Credentials(0, url, credentialUsername, password, userId, credentialKey);
        credentialMapper.insert(credentials);
    }

    public List<Credentials> getListCredential(Integer userId){
        return credentialMapper.getCredentialListings(userId);
    }

    public Credentials getCredential(int credentailId){
        return credentialMapper.getCredential(credentailId);
    }

    public void deleteCredential(int credentailId){
        credentialMapper.deleteCredential(credentailId);
    }

    public void updateCredential(Integer credentialId, String newUserName, String url, String key, String password){
        credentialMapper.updateCredential(credentialId, newUserName, url, key, password);
    }

}
