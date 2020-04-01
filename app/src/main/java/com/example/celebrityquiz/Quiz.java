package com.example.celebrityquiz;

import org.litepal.crud.LitePalSupport;
import java.util.List;

// Bridge class connecting MainActivity with QuizAdapter
class Quiz extends LitePalSupport {
    String question;
    String imageUrl;
    String[] answerOptions;
    String correctAnswer;

    Quiz(String question, String imageUrl, List<String> answerOptions, String correctAnswer) {
        this.question = question;
        this.imageUrl = imageUrl;
        this.answerOptions = answerOptions.toArray(new String[0]);
        this.correctAnswer = correctAnswer;
    }
}
