package com.example.feedback;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource()
interface FeedbackRepository extends PagingAndSortingRepository<Feedback, Long> {

    List<Feedback> findByContactType(Feedback.ContactType contactType);

}
