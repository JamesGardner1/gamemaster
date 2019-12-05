package com.example.gamemaster;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import java.util.Collections;

import java.util.List;



public class MainActivity extends AppCompatActivity {

    TextView questionText;
    Button choice1;
    Button choice2;
    Button nextButton;
    TextView timer;
    TextView scoreText;

    public int counter;
    public int score;

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
        timer = findViewById(R.id.timer_text);
        scoreText = findViewById(R.id.score_text);

        new CountDownTimer(50000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(counter));
                counter++;
            }
            @Override
            public void onFinish() {
                timer.setText("Finished");

            }
        }.start();



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRandomQuestion();
            }
        });

        choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer1();
            }
        });

        choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer2();
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


    public void checkAnswer1() {
        if (choice1.getText() == "True") {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            updateScore();
        } else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();

        }

        getRandomQuestion();
    }

    public void checkAnswer2() {
        if (choice2.getText() == "False") {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            updateScore();

        }

        getRandomQuestion();
    }

    public void updateScore() {
        score = score + 10;
        scoreText.setText(String.valueOf(score));
    }
}




