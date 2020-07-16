package com.example.feedback;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Feedback {

    enum ContactType {
        GENERAL, MARKETING, SUPPORT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;  // There is no usable business key for feedback so we use a surrogate one.

    private LocalDateTime submissionDate;  // Store time, using server timezone implicitly

    @Setter
    private String name;

    @Setter
    private String email;

    @Enumerated(EnumType.STRING)
    private ContactType contactType;

    private String message;

    Feedback(LocalDateTime submissionDate, ContactType contactType, String message) {
        this.submissionDate = submissionDate;
        this.contactType = contactType;
        this.message = message;
    }
}
