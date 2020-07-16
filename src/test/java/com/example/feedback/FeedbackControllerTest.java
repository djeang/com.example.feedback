package com.example.feedback;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedbackControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // Does not test the correctness of database insert, just the whole springboot setup
    @Test
    public void addingRegularFeedbackShouldSucceed() throws Exception {
        FeedbackController.AddRequest addRequest = new FeedbackController.AddRequest();
        addRequest.setContactType(Feedback.ContactType.GENERAL);
        addRequest.setMessage("This is my message");
        addRequest.setEmail("my@email.com");
        addRequest.setName("");
        assertEquals(200,
                this.restTemplate.postForEntity(url("/feedback/add"), addRequest, Void.class).getStatusCode().value());
    }

    @Test
    public void addingInvalidFeedbackShouldFail() throws Exception {
        FeedbackController.AddRequest addRequest = new FeedbackController.AddRequest();
        addRequest.setContactType(null);
        addRequest.setMessage("This is my message");
        addRequest.setEmail("my@email.com");
        assertEquals(400,
                this.restTemplate.postForEntity(url("/feedback/add"), addRequest, Void.class).getStatusCode().value());
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }

}