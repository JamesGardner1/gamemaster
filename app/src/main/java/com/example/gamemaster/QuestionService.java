package com.example.gamemaster;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public class QuestionService {

    @GET("{question}")
    Call<Quiz> getQuestion(@Path("question") String quiz, @Header("Authorization") String token);

