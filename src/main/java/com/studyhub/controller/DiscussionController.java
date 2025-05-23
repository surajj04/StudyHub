package com.studyhub.controller;

import com.studyhub.model.Discussion;
import com.studyhub.service.DiscussionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DiscussionController {

    @Autowired
    private DiscussionService discussionService;


    @GetMapping("/discussion")
    public String discussion(HttpSession session) {
        session.setAttribute("discussions", discussionService.getAllDiscussions());
        return "pages/discussion";
    }


    @PostMapping("/add/discussion")
    public String addDiscussion(@ModelAttribute Discussion discussion, HttpSession session) {
        Discussion result = discussionService.addDiscussion(discussion, session);
        if (result != null) {
            session.setAttribute("redirectUrl", "/discussion");
            session.setAttribute("error", null);
            session.setAttribute("success", "Your discussion question has been posted successfully!");
            return "pages/message";
        }
        session.setAttribute("success", null);
        session.setAttribute("redirectUrl", "/discussion");
        session.setAttribute("error", "Something went wrong while saving your discussion. Please try again.");
        return "pages/message";
    }

    @RequestMapping("/discussion/reply")
    public String addReply(@RequestParam String reply,@RequestParam int id, HttpSession session) {
        discussionService.addReply(reply,id, session);
        session.setAttribute("redirectUrl", "/discussion");
        session.setAttribute("error", null);
        session.setAttribute("success", "Your reply has been posted successfully!");
        return "pages/message";
    }


}
