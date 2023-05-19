package com.udacity.jwdnd.course1.cloudstorage.Controller;

import com.udacity.jwdnd.course1.cloudstorage.Model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    UserService userService;
    FileService fileService;
    NoteService noteService;
    CredentialService credentialService;
    EncryptionService encryptionService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping("/home")
    public String getHomePage(Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
                              @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCredential") CredentialForm newCredential,
                              Model model){



        int userId = userService.getUserIdByAuthen(authentication);

        if (userId == -1){
            return "login";
        }

        List<Credentials> data = credentialService.getListCredential(userId);

        model.addAttribute("files", this.fileService.getFileListings(userId));
        model.addAttribute("credentials", credentialService.getListCredential(userId));
        return  "home";
    }

    @PostMapping("/home")
    public String addFile(
            Authentication authentication, @ModelAttribute("newFile") FileForm newFile,
            @ModelAttribute("newNote") NoteForm newNote, @ModelAttribute("newCredential") CredentialForm newCredential, Model model) throws IOException {
        int userId = userService.getUserIdByAuthen(authentication);
        int checkUpdate = 0;
        String[] fileListings = fileService.getFileListings(userId);
        MultipartFile multipartFile = newFile.getFile();
        String fileName = multipartFile.getOriginalFilename();
        boolean fileIsDuplicate = false;
        for (String fileListing : fileListings) {
            if (fileListing.equals(fileName)) {
                fileIsDuplicate = true;

                break;
            }
        }
        if (!fileIsDuplicate) {
            checkUpdate = fileService.addFile(multipartFile, authentication.getName());;
            if(checkUpdate == 0){
                model.addAttribute("result", "not-saved");
            } else {
                model.addAttribute("result", "success");
            }
        } else {
            model.addAttribute("result", "error");
            model.addAttribute("message", "You have tried to add a duplicate file.");
        }
        model.addAttribute("files", fileService.getFileListings(userId));

        return "result";
    }

    @GetMapping(
            value = "/view-file/{fileName}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public @ResponseBody
    byte[] getFile(@PathVariable String fileName) {
        return fileService.getFile(fileName).getFileData();
    }

    @GetMapping("/delete-file/{fileName}")
    public String deleteFile(Authentication authentication, @PathVariable String fileName, @ModelAttribute("newFile") FileForm newFile,
                             Model model){

        fileService.deleteFile(fileName);
        Integer userId = userService.getUserIdByAuthen(authentication);
        model.addAttribute("files", fileService.getFileListings(userId));
        model.addAttribute("result", "success");

        return "result";
    }




}
