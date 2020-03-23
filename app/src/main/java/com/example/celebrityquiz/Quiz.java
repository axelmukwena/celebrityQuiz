package com.example.celebrityquiz;
import android.graphics.drawable.Drawable;
import java.util.ArrayList;

// Bridge class connecting MainActivity with QuizAdapter
class Quiz {
    String mQuestion;
    Drawable mImage;
    String[] mStringAnswer;
    String mCorrectAnswer;

    Quiz(String mQuestion, Drawable mImage, ArrayList<String> mStringAnswer, String mCorrectAnswer) {
        this.mQuestion = mQuestion;
        this.mImage = mImage;
        this.mStringAnswer = mStringAnswer.toArray(new String[0]);
        this.mCorrectAnswer = mCorrectAnswer;
    }
}
