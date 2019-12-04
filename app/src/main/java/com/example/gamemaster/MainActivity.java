package com.example.gamemaster;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity {

    TextView questionText;
    Button choice1;
    Button choice2;
    Button nextButton;

    private static final String url = "https://opentdb.com/api.php?amount=1&category=15&difficulty=easy&type=boolean";
    private static final String TAG = "RANDOM_QUESTION";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        questionText = findViewById(R.id.question_text);
        choice1 = findViewById(R.id.choice_1);
        choice2 = findViewById(R.id.choice_2);
        nextButton = findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomQuestion();
            }
        });


    }

    public void getRandomQuestion() {

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest questionRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            JSONObject trivia = jsonArray.getJSONObject(0);


                            String question = trivia.getString("question");
                            String correctAnswer = trivia.getString("correct_answer");
                            String incorrectAnswer = trivia.getString("incorrect_answers");

                            List<String> changeUp = new ArrayList<>();

                            changeUp.add(incorrectAnswer);
                            changeUp.add(correctAnswer);

                            Collections.shuffle(changeUp);

                            choice2.setText(incorrectAnswer);
                            questionText.setText(question);
                            choice1.setText(correctAnswer);




                        } catch (JSONException e) {
                            Log.e(TAG, "Error processing JSON response", e);
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error fetching data from compliment server", error);
                    }
                }




        );




        queue.add(questionRequest);

    }



}




