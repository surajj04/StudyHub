package com.studyhub.service;

import com.studyhub.model.UserHelp;
import com.studyhub.repository.UserHelpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelpService {

    @Autowired
    private UserHelpRepository helpRepository;

    public UserHelp addHelp(UserHelp help) {
        return helpRepository.save(help);
    }

}
