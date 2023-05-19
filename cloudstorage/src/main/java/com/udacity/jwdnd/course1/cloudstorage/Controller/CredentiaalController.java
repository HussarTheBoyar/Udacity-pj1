package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Controller
public class CredentiaalController {
    UserService userService;
    CredentialService credentialService;
    EncryptionService encryptionService;

    public CredentiaalController(UserService userService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/credentials")
    public String getAllCredential(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newCredential") CredentialForm newCredential,
            @ModelAttribute("newNote") NoteForm newNote, Model model) {
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("credentials", this.credentialService.getListCredential(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);

        return "home";
    }

    @GetMapping(value = "/credentials/{credentialId}")
    public Credentials getCredential(@PathVariable Integer credentialId) {
        return credentialService.getCredential(credentialId);
    }

    @PostMapping("insertCredential")
    public String newCredential(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newCredential") CredentialForm newCredential,
            @ModelAttribute("newNote") NoteForm newNote, Model model) {
        String userName = authentication.getName();
        String newUrl = newCredential.getUrl();
        String credentialIdStr = newCredential.getCredentialId();
        String password = newCredential.getPassword();

        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

        if (credentialIdStr.isEmpty()) {
            credentialService.insertCredential(userName, newUrl, newCredential.getUserName(), encodedKey, encryptedPassword);
        } else {
            Credentials existingCredential = getCredential(Integer.parseInt(credentialIdStr));
            credentialService.updateCredential(existingCredential.getCredentialid(), newCredential.getUserName(), newUrl, encodedKey, encryptedPassword);
        }
        User user = userService.getUser(userName);
        List<Credentials> data = credentialService.getListCredential(user.getUserId());
        //model.addAttribute("credentials", credentialService.getListCredential(user.getUserId()));
        //model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

        return "result";
    }

    @GetMapping(value = "/delete-credential/{credentialId}")
    public String deleteCredential(
            Authentication authentication, @PathVariable Integer credentialId,
            @ModelAttribute("newCredential") CredentialForm newCredential,
            @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote, Model model) {
        credentialService.deleteCredential(credentialId);
        String userName = authentication.getName();
        User user = userService.getUser(userName);
        model.addAttribute("credentials", credentialService.getListCredential(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("result", "success");

        return "result";
    }


}
