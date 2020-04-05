package com.example.celebrityquiz;

import org.litepal.crud.LitePalSupport;
import java.util.List;

// Bridge class connecting MainActivity with QuizAdapter
class Quiz extends LitePalSupport {
    String question;
    String imageUrl;
    String [] answerOptions;
    String correctAnswer;

    Quiz(String question, String imageUrl, String [] answerOptions, String correctAnswer) {
        this.question = question;
        this.imageUrl = imageUrl;
        this.answerOptions = answerOptions;
        this.correctAnswer = correctAnswer;
    }
}
/*
class SubClass {
    String one;
    String two;
    String three;
    String four;
}*/