package com.fundoo.label.service;

import com.fundoo.label.dto.LabelDto;
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
        if (user.isPresent()) {
            labelRepository.findBylabelName(labelDto.getLabelName())
                    .ifPresent(value -> {
                        throw new LabelException("Label Already Exist", 409);
                    });
            Label newLabel = new Label();
            BeanUtils.copyProperties(labelDto, newLabel);
            newLabel.setUser(user.get());
            labelRepository.save(newLabel);
            return true;
        }
        return false;
    }

    @Override
    public boolean mapLabel(LabelDto labelDto, String email) {
        Optional<Label> label = labelRepository.findBylabelName(labelDto.getLabelName());
        if (label.isPresent()) {
            Optional<Note> note = noteRepository.findById(labelDto.getNote_Id());
            return note.map((value) -> {
                label.get().getNoteList().add(value);
                labelRepository.save(label.get());
                return true;
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
    public boolean deleteLabel(int note_id, String email) {
        return true;
    }
}

