package com.epam.edp.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;

/**
 * @author Pavlo_Yemelianov
 */
@RestController
public class HelloEdpController {

    private final S3Client s3Client;
    private static final String BUCKET = "kuberocketci-applications-data";
    private static final String FILE = "cmtr-37419ec7/data.txt";

    @Autowired
    public HelloEdpController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping(value = "/")
    public String hello() throws IOException {

            GetObjectRequest getObjReq = GetObjectRequest.builder().bucket(BUCKET).key(FILE).build();
            ResponseInputStream<GetObjectResponse> objResp = s3Client.getObject(getObjReq);
            String contentString = new String(objResp.readAllBytes());
            Content content = new Content(contentString);
            return new ObjectMapper().writeValueAsString(content);

    }

    protected static class Content {
        private String content;

        public Content(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }


    }
}