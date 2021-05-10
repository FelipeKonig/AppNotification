package br.com.notificationapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    @GET("comments")
    Call<List<Comment>> getComments();
}
