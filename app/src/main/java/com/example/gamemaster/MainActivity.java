package com.example.gamemaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QUESTION";


    TextView questionText;
    Button trueButton;
    Button falseButton;
    Button nextButton;

    QuestionService mQuestionService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://opentdb.com/api.php?amount=10&category=15&difficulty=easy&type=boolean")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mQuestionService = retrofit.create(QuestionService.class);

        questionText = findViewById(R.id.question_text);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);


    }

    private void getQuestionData(final String quiz) {

        mQuestionService.getQuestion(quiz, BuildConfig.QUIZ_TOKEN).enqueue(new CallBack<Quiz>() {
            @Override
            public void onResponse(@NonNull Call<Quiz> call, @NonNull Response<Quiz> response) {

                Quiz quizResponse = response.body();
                Log.d(TAG, "Quiz Response: " + quizResponse);

                if (quizResponse != null) {
                    questionText.setText(quizResponse.question);

                }


            }
        }
    }



}
