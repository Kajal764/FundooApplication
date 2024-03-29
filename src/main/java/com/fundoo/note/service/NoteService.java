package com.fundoo.note.service;

import com.fundoo.collaborator.dto.CollaborateNoteDto;
import com.fundoo.collaborator.service.CollaborateService;
import com.fundoo.label.exception.LabelException;
import com.fundoo.label.model.Label;
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

import javax.mail.MessagingException;
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
    LabelRepository labelRepository;

    @Autowired
    JwtUtil jwtUtil = new JwtUtil();

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisService redisService;

    @Autowired
    NoteService noteService;

    @Autowired
    CollaborateService collaborateService;

    @Autowired
    IElasticSearchService IElasticSearchService;

    @Override
    public ResponseDto createNote(NoteDto noteDto, String email) throws IOException, NoteException, MessagingException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Note newNote = new Note();
            BeanUtils.copyProperties(noteDto, newNote);
            user.get().getNoteList().add(newNote);
            noteRepository.save(newNote);

            Optional<Note> note = noteRepository.findById(newNote.getNote_Id());
            if (noteDto.getLabelList().size() > 0 && note.isPresent()) {
                for (int i = 0; i < noteDto.getLabelList().size(); i++) {
                    noteService.mapLabel(newNote.getNote_Id(), noteDto.getLabelList().get(i).getLabelName());
                }
            }
            System.out.println(noteDto.getCollaborateUserList());
            if (noteDto.getCollaborateUserList().size() > 0 && note.isPresent()) {
                CollaborateNoteDto collaborateNoteDto;
                for (int i = 0; i < noteDto.getCollaborateUserList().size(); i++) {
                    collaborateNoteDto = new CollaborateNoteDto(newNote.getNote_Id(), noteDto.getCollaborateUserList().get(i).getEmail());
                    collaborateService.addCollaborator(collaborateNoteDto, email);
                }
            }

//            if (noteDto.getCollaborateUser() != null) {
//                CollaborateNoteDto collaborateNoteDto;
//                for (int i=0 ;i<noteDto.getCollaborateUser().size(); i++){
//                    collaborateNoteDto = new CollaborateNoteDto(note.get().getNote_Id(),noteDto.getCollaborateUser().get(i).getEmail());
//                    collaborateService.addCollaborator(collaborateNoteDto,email);
//                }
//            }
//

//            if (noteDto.getCollaborateUser() != null) {
//                CollaborateNoteDto collaborateNoteDto = new CollaborateNoteDto(note.get().getNote_Id(), noteDto.getCollaborateUser());
//                collaborateService.addCollaborator(collaborateNoteDto,email);
//            }
            IElasticSearchService.saveNote(newNote);
            return new ResponseDto("Note created successfully", 200);
        }
        return new ResponseDto("User not present", 403);
    }

    private boolean mapLabel(Integer note_id, String labelName) {
        System.out.println(note_id + "   " + labelName);
        Optional<Label> label = labelRepository.findBylabelName(labelName);
        if (label.isPresent()) {
            Optional<Note> note = noteRepository.findById(note_id);
            return note.map((value) -> {
                if (label.get().getNoteList().contains(value))
                    return false;
                label.get().getNoteList().add(value);
                labelRepository.save(label.get());
                return true;
            }).orElseThrow(() -> new LabelException("Note Is Not Present", 404));
        }
        return false;
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
        System.out.println("date" + reminderDTO.getRemainder());
        if (note.isPresent()) {
            note.get().setRemainder(reminderDTO.getRemainder());
            noteRepository.save(note.get());
            return true;
        }
        throw new NoteException("Note Not Found", 400);
    }

    @Override
    public boolean deleteReminder(int note_id, String email) throws NoteException {
        Optional<Note> note = noteRepository.findById(note_id);
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

    @Override
    public List<Note> getMapNote(String email, Integer label_id) throws NoteException {
        Optional<Label> label = labelRepository.findById(label_id);
        List<Note> noteList;
        if (label.isPresent()) {
            noteList = label.get().getNoteList();
            return noteList;
        }
        throw new NoteException("Note Not Found", 400);
    }

    @Override
    public List<Note> getPinList(GetNote value, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            List<Note> noteList = user.get().getNoteList();
            noteList.removeIf(note -> note.isTrash());
            noteList.removeIf(note -> note.isArchive());
            noteList.removeIf(note -> !note.isPin());
            return noteList;
        }
        return null;
    }

}

