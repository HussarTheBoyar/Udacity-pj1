package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

  private final CredentialMapper credentialMapper;
  private final UserMapper userMapper;
  private EncryptionService encryptionService;

  public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper,
      EncryptionService encryptionService) {
    this.credentialMapper = credentialMapper;
    this.userMapper = userMapper;
    this.encryptionService = encryptionService;
  }

  //Add
  public void addAndUpdateCredential(CredentialForm credentialForm, Integer userId) {

    byte[] key = new byte[16];
    SecureRandom random = new SecureRandom();
    random.nextBytes(key);
    String encodedKey = Base64.getEncoder().encodeToString(key);
    String encryptedPassword = encryptionService.encryptValue(credentialForm.getPassword(),
        encodedKey);
    Credentials credentials = credentialMapper.getCredentialByUserName(credentialForm.getUserName());

    if (credentials == null){

      Credentials credential = new Credentials(0,
          credentialForm.getUrl(), credentialForm.getUserName(), encryptedPassword, userId,
          encodedKey);
      credentialMapper.insert(credential);


    } else {
      credentialMapper.updateCredential(credentialForm.getUserName(), credentialForm.getUrl(), encodedKey, encryptedPassword);
    }





  }

  //Get
  public List<Credentials> getListCredential(Integer userId) {
    return credentialMapper.getCredentialListings(userId);
  }

  public List<Credentials> getAllCredential() {
    return credentialMapper.getAllCredential();
  }

  public List<Credentials> getCredentialListingsByName(String userName) {
    return credentialMapper.getCredentialListingsByName(userName);
  }

  //delete
  public void deleteCredential() {

  }

  //Update
  public void updateCredential() {

  }

  //Check exist
  public boolean checkCredentialExist() {
    return false;
  }
}
