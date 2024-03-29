package com.fundoo.label.service;

import com.fundoo.label.dto.LabelDto;
import com.fundoo.label.dto.MapDto;
import com.fundoo.label.exception.LabelException;
import com.fundoo.label.model.Label;
import com.fundoo.label.repository.LabelRepository;
import com.fundoo.note.model.Note;
import com.fundoo.note.repository.INoteRepository;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LabelService implements ILabelService {

    @Autowired
    LabelRepository labelRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    INoteRepository noteRepository;

    @Override
    public boolean createLabel(LabelDto labelDto, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        for (int i = 0; i < user.get().getLabelList().size(); i++) {
            if (user.get().getLabelList().get(i).getLabelName().equals(labelDto.getLabelName())) {
                throw new LabelException("Label Already Exist", 409);
            }
        }
        Label newLabel = new Label();
        BeanUtils.copyProperties(labelDto, newLabel);
        newLabel.setUser(user.get());
        labelRepository.save(newLabel);
        return true;
    }

    public boolean mapLabel(LabelDto labelDto, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        List<Label> label = user.get().getLabelList();
        if (label.size() > 0) {
            Optional<Note> note = noteRepository.findById(labelDto.getNote_Id());
            return note.map((value) -> {
                for (int i = 0; i < label.size(); i++) {
                    if (label.get(i).getLabel_Id() == labelDto.getLabel_Id()) {
                        if (label.get(i).getNoteList().contains(value))
                            return false;
                        label.get(i).getNoteList().add(value);
                        userRepository.save(user.get());
                        return true;
                    }
                }
                return false;
            }).orElseThrow(() -> new LabelException("Note Is Not Present", 404));
        }
        return false;
    }

    @Override
    public boolean editLabel(LabelDto labelDto, String email) {
        Optional<Label> label = labelRepository.findById(labelDto.getLabel_Id());
        return label.map((value) -> {
            value.setLabelName(labelDto.getLabelName());
            value.setModifiedDate(LocalDateTime.now());
            labelRepository.save(value);
            return true;
        }).orElseThrow(() -> new LabelException("Label Not Present", 404));
    }

    @Override
    public boolean deleteLabel(int label_id, String email) {
        Optional<Label> label = labelRepository.findById(label_id);
        return label.map((value) -> {
            labelRepository.delete(value);
            return true;
        }).orElseThrow(() -> new LabelException("Label Not Present", 404));
    }

    @Override
    public boolean removeNoteLabel(MapDto mapDto) {
        Optional<Label> label = labelRepository.findById(mapDto.getLabel_Id());
        if (label.isPresent()) {
            Optional<Note> note = noteRepository.findById(mapDto.getNote_Id());
            return note.map((value) -> {
                label.get().getNoteList().remove(value);
                labelRepository.save(label.get());
                return true;
            }).orElseThrow(() -> new LabelException("Note Is Not Present", 404));
        }
        throw new LabelException("Label Note Present", 404);
    }

    @Override
    public List<Label> getLabelList(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        List<Label> labelList = user.get().getLabelList();
        return labelList;
    }
}

