package com.example.gamemaster;

public class Quiz {
    String question;
    String correct_answer;
    String incorrect_answers;

    @Override
    public String toString() {
        return "Quiz{" +
                "question=" + question + '\'' +
                ", correct_answer='" + correct_answer + '\'' +
                ", incorrect_answers=" + incorrect_answers + '}';
    }

}
