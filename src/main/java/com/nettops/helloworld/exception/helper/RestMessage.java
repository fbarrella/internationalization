package com.nettops.helloworld.exception.helper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class RestMessage {

    @JsonProperty("error_code")
    private Long errorCode;

    @JsonProperty("error_codes")
    private List<Long> errorCodes;

    private String message;

    private List<String> messages;

    public RestMessage(String message) {
        this.message = message;
    }

    public RestMessage(List<String> messages) {
        this.messages = messages;
    }

    public RestMessage(Long errorCode, String message) {
        this.message = message;
    }

    public RestMessage(List<Long> errorCodes, List<String> messages) {
        this.messages = messages;
    }

}
