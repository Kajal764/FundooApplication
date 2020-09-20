package com.fundoo.note.enumerations;

import com.fundoo.note.model.Note;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum SortBaseOn {
    NAME {
        @Override
        public List<Note> sortedNotes(Note[] notes) {
            Arrays.parallelSort(notes, Comparator.comparing(note -> note.getTitle()));
            List<Note> collect = Arrays.stream(notes).collect(Collectors.toList());
            return collect;
        }
    },
    DATE {
        @Override
        public List<Note> sortedNotes(Note[] notes) {
            Arrays.parallelSort(notes, Comparator.comparing(note -> note.getCreatedDate()));
            List<Note> collect = Arrays.stream(notes).collect(Collectors.toList());
            return collect;
        }
    },
    DESCRIPTION {
        @Override
        public List<Note> sortedNotes(Note[] notes) {
            Arrays.parallelSort(notes, Comparator.comparing(note -> note.getDescription()));
            List<Note> collect = Arrays.stream(notes).collect(Collectors.toList());
            return collect;
        }
    };

    public abstract List<Note> sortedNotes(Note[] notes);

}
