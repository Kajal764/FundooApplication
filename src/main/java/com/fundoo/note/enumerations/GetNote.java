package com.fundoo.note.enumerations;

import com.fundoo.note.model.Note;

import java.util.List;

public enum GetNote {

    TRASH {
        @Override
        public List<Note> getNote(List<Note> noteList) {
            noteList.removeIf(note -> !note.isTrash());
            return noteList;
        }
    },
    ARCHIVE {
        @Override
        public List<Note> getNote(List<Note> noteList) {
            noteList.removeIf(note -> !note.isArchive());
            return noteList;
        }
    },
    PIN {
        @Override
        public List<Note> getNote(List<Note> noteList) {
            noteList.removeIf(note -> !note.isPin());
            return noteList;
        }
    };

    public abstract List<Note> getNote(List<Note> noteList);

}

