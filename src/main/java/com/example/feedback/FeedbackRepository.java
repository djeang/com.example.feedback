package com.example.feedback;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
interface FeedbackRepository extends PagingAndSortingRepository<Feedback, Long> {

    List<Feedback> findByContactType(Feedback.ContactType contactType, Sort sort);

}
