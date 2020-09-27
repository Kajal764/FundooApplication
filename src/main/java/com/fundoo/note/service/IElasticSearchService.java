package com.fundoo.note.service;

import com.fundoo.note.model.Note;

import java.io.IOException;
import java.util.List;

public interface IElasticSearchService {
    String saveNote(Note newNote) throws IOException;

    String updateNote(Note note) throws IOException;

    String deleteNote(Note note) throws IOException;

    List<Note> searchByTitle(String title, String email) throws IOException;
}
