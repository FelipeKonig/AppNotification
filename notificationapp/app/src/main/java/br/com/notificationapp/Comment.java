package br.com.notificationapp;

import com.google.gson.annotations.SerializedName;

public class Comment {

    private int id, postId;
    private String body;

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    @SerializedName( "Body" )
    public String getBody() {
        return body;
    }
}
