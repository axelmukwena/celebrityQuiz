package com.example.celebrityquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter {
    private List<Quiz> mList;

    // Constructor to initialize all arrayList
    QuizAdapter(List<Quiz> list) {
        this.mList = list;
    }

    @NonNull
    @Override
    // Build view layout and call ViewHolder, QuizHolder class
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layoutInflater = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.choice, viewGroup, false);
        return new RecyclerView.ViewHolder(layoutInflater) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        TextView viewQuestion = viewHolder.itemView.findViewById(R.id.celebrityQuestion);
        ImageView imageView = viewHolder.itemView.findViewById(R.id.celebrityImage);
        RadioGroup radioGroup = viewHolder.itemView.findViewById(R.id.celebrityOption);
        RadioButton radioButtonOne = viewHolder.itemView.findViewById(R.id.radioButtonOne);
        RadioButton radioButtonTwo = viewHolder.itemView.findViewById(R.id.radioButtonTwo);
        RadioButton radioButtonThree = viewHolder.itemView.findViewById(R.id.radioButtonThree);
        RadioButton radioButtonFour = viewHolder.itemView.findViewById(R.id.radioButtonFour);
        View horizontalLine = viewHolder.itemView.findViewById(R.id.horizontalDivider);

        if(!mList.isEmpty()) {
            Quiz quiz = mList.get(position);

            viewQuestion.setText(String.format("%s. %s", position + 1, quiz.question));
            Glide.with(imageView.getContext()).load(quiz.imageUrl).into(imageView);
            radioButtonOne.setText(quiz.one);
            radioButtonTwo.setText(quiz.two);
            radioButtonThree.setText(quiz.three);
            radioButtonFour.setText(quiz.four);

            switch (quiz.userAnswer) {
                case 1:
                    radioButtonOne.setChecked(true);
                    break;
                case 2:
                    radioButtonTwo.setChecked(true);
                    break;
                case 3:
                    radioButtonThree.setChecked(true);
                    break;
                case 4:
                    radioButtonFour.setChecked(true);
                    break;
                default:
                    radioGroup.clearCheck();
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonOne:
                        mList.get(position).userAnswer = 1;
                        break;
                    case R.id.radioButtonTwo:
                        mList.get(position).userAnswer = 2;
                        break;
                    case R.id.radioButtonThree:
                        mList.get(position).userAnswer = 3;
                        break;
                    case R.id.radioButtonFour:
                        mList.get(position).userAnswer = 4;
                        break;
                    default:
                        mList.get(position).userAnswer = 0;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList == null) return 0;
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    int getScore() {
        int score = 0;
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).userAnswer == mList.get(i).correctAnswer) score++;
        }
        return score;
    }
}

