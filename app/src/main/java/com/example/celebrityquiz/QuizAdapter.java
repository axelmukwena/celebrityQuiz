package com.example.celebrityquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> mArrayList;
    private Context mContext;
    private CurrentScore mCurrentScore;

    QuizAdapter(ArrayList<Object> mArrayList, Context context) {
        this.mArrayList = mArrayList;
        this.mContext = context;
        getItemCount();
    }

    int correct = 0;
    /*QuizAdapter(CurrentScore mCurrentScore, Context context, int correct){
        this.mCurrentScore = mCurrentScore;
        this.mContext = context;
        this.correct = correct;
    }*/

    public interface CurrentScore {
        void CurrentScoreMethod(QuizHolder quizHolder, int score);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new QuizHolder(layoutInflater.inflate(R.layout.choice, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        Quiz object = (Quiz) mArrayList.get(position);
        QuizHolder q = (QuizHolder) viewHolder;
        q.mViewQuestion.setText(String.format("%s. %s", position + 1, object.mQuestion));
        q.mViewImage.setImageDrawable(object.mImage);
        q.createRadioButtons(object.mStringAnswer);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class QuizHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mViewQuestion;
        private RadioGroup mRadioGroup;
        private ImageView mViewImage;

        QuizHolder(@NonNull View itemView) {
            super(itemView);
            mViewQuestion = itemView.findViewById(R.id.celebrityQuestion);
            mViewImage = itemView.findViewById(R.id.celebrityImage);
            mRadioGroup = itemView.findViewById(R.id.celebrityOption);
            itemView.findViewById(R.id.horizontalDivider);
        }

        void createRadioButtons(String[] arrayAnswer) {
            if (mRadioGroup.getChildAt(0) != null)
                mRadioGroup.removeAllViews();
            for (String s : arrayAnswer) {
                mRadioGroup.addView(createRadioButtonAnswerAndSetOnClickListener(s));
            }
        }

        RadioButton createRadioButtonAnswerAndSetOnClickListener(String string) {
            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setText(string);
            radioButton.setOnClickListener(this);
            return radioButton;
        }

        @Override
        public void onClick(View view) {
            boolean checked = ((RadioButton) view).isChecked();

            if (checked) {
                int radioButtonID = mRadioGroup.getCheckedRadioButtonId();
                View radioButton = mRadioGroup.findViewById(radioButtonID);
                int selectedAnswerIndex = mRadioGroup.indexOfChild(radioButton);
                RadioButton r = (RadioButton) mRadioGroup.getChildAt(selectedAnswerIndex);
                String  selectedAnswer = r.getText().toString();

                int position = getAdapterPosition();
                Object object = mArrayList.get(position);
                String correctAnswer = ((Quiz) object).mCorrectAnswer;

                if (selectedAnswer.equals(correctAnswer)) {
                    correct++;
                    mCurrentScore.CurrentScoreMethod(this, correct);
                }
            }
        }
    }
}
