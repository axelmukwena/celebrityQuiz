package com.example.celebrityquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> mArrayList;
    private boolean[] userAnswer;
    private Context mContext;

    private static final int num = 1;

    QuizAdapter(ArrayList<Object> mArrayList, Context context) {
        this.mArrayList = mArrayList;
        this.mContext = context;
        userAnswer = new boolean[getItemCount()];
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new QuizHolder(layoutInflater.inflate(R.layout.choice, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        Object object = mArrayList.get(position);
        int questionCount = position + 1;

        if (getQuiz(position) == num) {
            QuizHolder q = (QuizHolder) viewHolder;
            Quiz quiz = (Quiz) object;
            q.mViewQuestion.setText(String.format("%s. %s", questionCount, Quiz.mQuestion));
            q.createRadioButtons(quiz.mStringAnswer);
        }
    }

    private int getQuiz(int position) {
        Object object = mArrayList.get(position);
        if (object instanceof Quiz)
            return num;
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public static class QuizHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mViewQuestion;
        private RadioGroup mRadioGroup;

        QuizHolder(@NonNull View itemView) {
            super(itemView);
            mViewQuestion = itemView.findViewById(R.id.celebrityQuestion);
            mRadioGroup = itemView.findViewById(R.id.celebrityAnswer);
        }

        void createRadioButtons(String[] arrayAnswer) {
            if (mRadioGroup.getChildAt(0) != null)
                return;
            for (int i = 0; i < arrayAnswer.length; i++)
                mRadioGroup.addView(createRadioButtonAnswerAndSetOnClickListener(
                        Character.toString((char) (65 + i)) + ". " + arrayAnswer[i]));
        }

        RadioButton createRadioButtonAnswerAndSetOnClickListener(String string) {
            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setText(string);
            radioButton.setTextSize(22);
            radioButton.setOnClickListener(this);
            return radioButton;
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Object object = mArrayList.get(position);
            int positionOfCorrectAnswer = ((Quiz) object).mPositionCorrectAnswer;
            RadioButton radioButton = (RadioButton) mRadioGroup.getChildAt(positionOfCorrectAnswer);
            userAnswer[position] = radioButton.isChecked();
        }
    }
}