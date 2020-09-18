package com.fundoo.label.service;

import com.fundoo.label.dto.LabelDto;
import com.fundoo.label.exception.LabelException;
import com.fundoo.label.model.Label;
import com.fundoo.label.repository.LabelRepository;
import com.fundoo.user.model.User;
import com.fundoo.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LabelServiceTest {

    @InjectMocks
    LabelService labelService;

    @Mock
    UserRepository userRepository;

    @Mock
    LabelRepository labelRepository;

    @Mock
    User user;

    LabelDto labelDto;
    String email;
    Label label;

    @BeforeEach
    void setUp() {
        email = "kajaldw666@gmail.com";
        labelDto = new LabelDto("java");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        label = new Label();
        BeanUtils.copyProperties(labelDto, label);
    }

    @Test
    void givenLabelName_whenCreatingLabel_ItShouldReturnTrue() {
        when(labelRepository.findBylabelName(any())).thenReturn(Optional.empty());
        Assert.assertTrue(labelService.createLabel(labelDto, email));
    }

    @Test
    void givenLabelNameWhichPresent_whenCreatingLabel_ItShouldReturnThrowException() {
        when(labelRepository.findBylabelName(any())).thenReturn(Optional.of(label));
        assertThrows(LabelException.class, () -> labelService.createLabel(labelDto, email));
    }

}