package com.studyhub.service;

import com.studyhub.model.CustomUserDetails;
import com.studyhub.model.Discussion;
import com.studyhub.model.Replies;
import com.studyhub.repository.DiscussionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
            List<Replies> replies = d.getReplies();
            if (d.getTopic() != null) {
                if (replies == null) {
                    d.setReplies(new ArrayList<>());
                }
                replies.add(r);
                d.setReplies(replies);
                discussionRepository.save(d);
            }
        }
    }
}
