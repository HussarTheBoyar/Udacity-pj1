package com.udacity.jwdnd.course1.cloudstorage.Mapper;

import com.udacity.jwdnd.course1.cloudstorage.Model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    List<Credentials> getCredentialListings(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{userName}")
    List<Credentials> getCredentialListingsByName(String userName);

    @Select("SELECT * FROM CREDENTIALS")
    List<Credentials> getAllCredential();

    @Insert("INSERT INTO CREDENTIALS (url, username, credent_key, password, userid) " +
            "VALUES(#{url}, #{username}, #{credentKey}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credentials credential);

    /*@Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Credentials getCredentialById(Integer credentialId);*/

    @Select("SELECT * FROM CREDENTIALS WHERE username = #{username}")
    Credentials getCredentialByUserName(String userName);

    /*@Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    void deleteCredential(Integer credentialId);*/

    @Update("UPDATE CREDENTIALS SET url = #{url}, credent_key = #{credent_key}, password = #{password} WHERE userName = #{userName}")
    void updateCredential(String userName, String url, String credent_key, String password);
}
