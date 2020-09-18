package com.fundoo.label.service;

import com.fundoo.label.dto.LabelDto;
import com.fundoo.label.exception.LabelException;
import com.fundoo.label.model.Label;
import com.fundoo.label.repository.LabelRepository;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LabelService implements ILabelService {

    @Autowired
    LabelRepository labelRepository;

    @Autowired
    UserRepository userRepository;

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
}

