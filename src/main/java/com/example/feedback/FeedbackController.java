package com.example.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@Slf4j
@AllArgsConstructor
class FeedbackController {

    private final FeedbackRepository feedbackRepository;

    @PostMapping("/feedback/add")
    void add(@RequestBody AddRequest addRequest, HttpServletResponse response) throws IOException {

        // Feedback object coming from the client is supposed to be correct as it is already validated by the client.
        // Nevertheless this server check blocks tainted requests because such requests may if the client is compromised
        // (attack or bug).
        if (!addRequest.isValid()) {
            log.warn("Illegal request : " + addRequest);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        LocalDateTime now = LocalDateTime.now();
        Feedback feedback = new Feedback(now, addRequest.contactType, addRequest.message);
        feedback.setEmail(addRequest.email);
        feedback.setName(addRequest.name);
        feedbackRepository.save(feedback);
    }

    @Data
    static class AddRequest {

        private static final int NAME_MAX_LENGTH = 128;

        private static final int EMAIL_MAX_LENGTH = 128;

        private static final int MESSAGE_MAX_LENGTH = 1000;

        private String name;

        private String email;

        private Feedback.ContactType contactType;

        private String message;

        boolean isValid() {
            if (name != null && name.length() > NAME_MAX_LENGTH) {
                return false;
            }
            if (email != null && email.length() > EMAIL_MAX_LENGTH) {
                return false;
            }
            if (message == null || message.length() > MESSAGE_MAX_LENGTH ) {
                return false;
            }
            return contactType != null;
        }
    }


}
