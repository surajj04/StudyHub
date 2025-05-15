package com.preportal.service;

import com.preportal.model.CustomUserDetails;
import com.preportal.model.Discussion;
import com.preportal.model.Replies;
import com.preportal.repository.DiscussionRepository;
import com.preportal.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscussionService {

    @Autowired
    private DiscussionRepository discussionRepository;

    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findAllByOrderByDateDesc();
    }

    public Discussion addDiscussion(Discussion discussion, HttpSession session) {
        CustomUserDetails user = (CustomUserDetails) session.getAttribute("currentUser");
        if (user.getName() != null) {
            discussion.setDate(LocalDateTime.now());
            discussion.setUserID(user.getId());
            discussion.setPostBy(user.getName());
            discussion.setReplies(new ArrayList<>());
            return discussionRepository.save(discussion);
        }
        return null;
    }


    public void addReply(String reply,int id, HttpSession session) {
        CustomUserDetails user = (CustomUserDetails) session.getAttribute("currentUser");
        if (user.getName() != null) {
            Replies r = new Replies();
            r.setUserID(user.getId());
            r.setReply(reply);
            r.setName(user.getName());
            r.setDate(LocalDateTime.now());

            Discussion d = discussionRepository.findById(id).orElse(new Discussion());
            if (d.getTopic() != null) {
                List<Replies> list = d.getReplies();
                list.add(r);
                d.setReplies(list);
                discussionRepository.save(d);
            }
        }
    }
}
