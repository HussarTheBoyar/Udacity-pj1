package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.Mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.Mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.Model.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    private final UserMapper userMapper;
    private final NoteMapper noteMapper;

    public NoteService(UserMapper userMapper, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }

    public void addNote(String title, String description, String userName) {
        Integer userId = userMapper.getUser(userName).getUserId();
        Note note = new Note(0, title, description, userId);
        noteMapper.insert(note);
    }

    public Note[] getNoteListings(Integer userId) {
        return noteMapper.getNotesForUser(userId);
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public void deleteNote(Integer noteId) {
        noteMapper.deleteNote(noteId);
    }

    public void updateNote(Integer noteId, String title, String description) {
        noteMapper.updateNote(noteId, title, description);
    }
}
