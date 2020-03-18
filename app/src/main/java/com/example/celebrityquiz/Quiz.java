package com.example.celebrityquiz;

class Quiz {
    static String mQuestion;
    String[] mStringAnswer;
    int mPositionCorrectAnswer;

    Quiz(String mQuestion, String[] mStringAnswer, int mPositionCorrectAnswer) {
        Quiz.mQuestion = mQuestion;
        this.mStringAnswer = mStringAnswer;
        this.mPositionCorrectAnswer = mPositionCorrectAnswer;
    }
}
