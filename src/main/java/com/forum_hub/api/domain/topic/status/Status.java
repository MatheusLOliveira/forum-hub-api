package com.forum_hub.api.domain.topic.status;

public enum Status {
    NOTSOLVED("Not solved"),
    SOLVED("Solved");

    private String value;

    private Status(String pValue) {
        this.value = pValue;
    }   
}
