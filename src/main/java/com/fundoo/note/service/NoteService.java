package com.fundoo.note.service;

import com.fundoo.label.repository.LabelRepository;
import com.fundoo.note.dto.NoteColorDto;
import com.fundoo.note.dto.NoteDto;
import com.fundoo.note.dto.ReminderDto;
import com.fundoo.note.dto.SortDto;
import com.fundoo.note.enumerations.GetNote;
import com.fundoo.note.enumerations.SortBaseOn;
import com.fundoo.note.exception.NoteException;
import com.fundoo.note.model.Note;
import com.fundoo.note.repository.INoteRepository;
import com.fundoo.user.dto.ResponseDto;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import com.fundoo.user.service.RedisService;
import com.fundoo.user.utility.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
class NoteService implements INoteService {

    @Autowired
    INoteRepository noteRepository;

    @Autowired
    JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisService redisService;

    @Autowired
    LabelRepository labelRepository;

    @Autowired
    IElasticSearchService IElasticSearchService;

    @Override
    public ResponseDto createNote(NoteDto noteDto, String email) throws IOException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Note newNote = new Note();
            BeanUtils.copyProperties(noteDto, newNote);
            user.get().getNoteList().add(newNote);
            noteRepository.save(newNote);
            IElasticSearchService.saveNote(newNote);
            return new ResponseDto("Note created successfully", 200);
        }
        return new ResponseDto("User not present", 403);
    }

    @Override
    public ResponseDto deleteNote(int note_id, String email) throws NoteException, IOException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user != null) {
            Optional<Note> note = noteRepository.findById(note_id);
            if (note.isPresent()) {
                note.get().setTrash(true);
                noteRepository.save(note.get());
                IElasticSearchService.deleteNote(note.get());
                return new ResponseDto("Note trashed", 200);
            }
            throw new NoteException("Note is not present", 404);
        }
        return new ResponseDto("User not present", 403);
    }

    @Override
    public ResponseDto trashNoteDelete(int note_id, String email) throws NoteException, IOException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Optional<Note> note = noteRepository.findById(note_id);

            if (note.isPresent() && note.get().isTrash() == true) {
                noteRepository.delete(note.get());
                return new ResponseDto("Note Deleted Successfully", 200);
            }
            throw new NoteException("Note is not in trash", 404);
        }
        return new ResponseDto("User not present", 403);
    }

    @Override
    public boolean updateNote(NoteDto noteDto, String email) throws NoteException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Optional<Note> note = noteRepository.findById(noteDto.note_id);
            return note.map((value) -> {
                value.setTitle(noteDto.title);
                value.setDescription(noteDto.description);
                value.setEditDate(LocalDateTime.now());
                noteRepository.save(value);
                try {
                    IElasticSearchService.updateNote(note.get());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }).orElseThrow(() -> new NoteException("Note Is Not Present", 404));
        }
        return false;
    }

    @Override
    public List<Note> getNoteList(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            List<Note> noteList = user.get().getNoteList();
            noteList.removeIf(note -> note.isTrash());
            noteList.removeIf(note -> note.isArchive());
            noteList.removeIf(note -> note.isPin());
            return noteList;
        }
        return null;
    }

    @Override
    public List<Note> sort(SortDto sortDto, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        List<Note> noteList = user.get().getNoteList();
        Note[] notes = noteList.toArray(new Note[noteList.size()]);
        SortBaseOn sortBaseOn = sortDto.getSortBaseOn();
        List<Note> collect = sortBaseOn.sortedNotes(notes);
        if (sortDto.getType().equals("desc"))
            Collections.reverse(collect);
        return collect;
    }

    @Override
    public boolean pinUnpinNote(int note_id, String email) throws NoteException {
        Optional<Note> note = noteRepository.findById(note_id);
        if (note.isPresent()) {
            if (note.get().isPin()) {
                note.get().setPin(false);
                noteRepository.save(note.get());
                return false;
            }
            note.get().setPin(true);
            noteRepository.save(note.get());
            return true;
        }
        throw new NoteException("Note Is Not Present", 404);
    }

    @Override
    public boolean archive(int note_id, String email) throws NoteException {
        Optional<Note> note = noteRepository.findById(note_id);
        if (note.isPresent()) {
            if (note.get().isArchive()) {
                note.get().setArchive(false);
                noteRepository.save(note.get());
                return false;
            } else {
                note.get().setArchive(true);
                noteRepository.save(note.get());
                return true;
            }
        }
        throw new NoteException("Note Is Not Present", 404);
    }

    @Override
    public List<Note> getNotes(GetNote value, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        List<Note> noteList = user.get().getNoteList();
        List<Note> notes = value.getNote(noteList);
        return notes;
    }

    @Override
    public boolean restoreTrashNote(int note_id) throws NoteException {
        Optional<Note> note = noteRepository.findById(note_id);
        if (note.get().isTrash() == true) {
            note.get().setTrash(false);
            noteRepository.save(note.get());
            return true;
        }
        throw new NoteException("Note Is Not Is Trash", 404);
    }

    @Override
    public boolean setReminder(ReminderDto reminderDTO, String email) throws NoteException {
        Optional<Note> note = noteRepository.findById(reminderDTO.getNote_Id());
        if (note.isPresent()) {
            note.get().setRemainder(reminderDTO.getRemainder());
            noteRepository.save(note.get());
            return true;
        }
        throw new NoteException("Note Not Found", 400);
    }

    @Override
    public boolean deleteReminder(ReminderDto reminderDTO, String email) throws NoteException {
        Optional<Note> note = noteRepository.findById(reminderDTO.getNote_Id());
        if (note.isPresent()) {
            note.get().setRemainder(null);
            noteRepository.save(note.get());
            return true;
        }
        throw new NoteException("Note Not Found", 400);
    }

    @Override
    public List<Note> getReminderSetNotes() {
        List<Note> allNotes = noteRepository.findAll();
        allNotes.removeIf(note -> note.getRemainder() == null);
        return allNotes;
    }

    @Override
    public boolean setNoteColor(NoteColorDto noteColorDto, String email) throws NoteException {
        Optional<Note> note = noteRepository.findById(noteColorDto.note_id);
        if (note.isPresent()) {
            note.get().setColor(noteColorDto.color);
            noteRepository.save(note.get());
            try {
                IElasticSearchService.updateNote(note.get());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        throw new NoteException("Note Not Found", 400);
    }
}

