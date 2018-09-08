package com.knoxpo.rajivsonawala.easytow;

import java.util.UUID;

public class Todo {

    private String mString;


    private UUID Id;


    public Todo(){

        Id=UUID.randomUUID();
        mString=String.valueOf(Math.random());
    }

    public String getString() {
        return mString;
    }


    public UUID getId() {
        return Id;
    }

    public void setString(String string) {
        mString = string;
    }

    public void setId(UUID id) {
        Id = id;
    }



}
