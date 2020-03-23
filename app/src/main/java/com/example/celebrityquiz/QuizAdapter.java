package com.example.celebrityquiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class QuizAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Object> mArrayList;
    private Context mContext;

    // Constructor to initialize all arrayList
    QuizAdapter(ArrayList<Object> mArrayList, Context context) {
        this.mArrayList = mArrayList;
        this.mContext = context;
        getItemCount();
    }

    @NonNull
    @Override
    // Build view layout and call ViewHolder, QuizHolder class
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        return new QuizHolder(layoutInflater.inflate(R.layout.choice, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        // Get variable/arguments from bridge class, Quiz
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

    // Class to place items into view layout set by recyclerView
    public class QuizHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mViewQuestion;
        private RadioGroup mRadioGroup;
        private ImageView mViewImage;
        int correct = 0; // Initialize score value | if selected radioButton is correct

        // Interface instance to access and modify score value. See onClick function
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int scoreValue = sharedPreferences.getInt("score", 0);

        QuizHolder(@NonNull View itemView) {
            super(itemView);
            mViewQuestion = itemView.findViewById(R.id.celebrityQuestion);
            mViewImage = itemView.findViewById(R.id.celebrityImage);
            mRadioGroup = itemView.findViewById(R.id.celebrityOption);
            itemView.findViewById(R.id.horizontalDivider);
        }

        // Create radioButtons and add view for each option answer in arrayAnswer, for each radioGroup
        void createRadioButtons(String[] arrayAnswer) {
            if (mRadioGroup.getChildAt(0) != null)
                mRadioGroup.removeAllViews();
            for (String s : arrayAnswer) {
                mRadioGroup.addView(createRadioButtonAnswerAndSetOnClickListener(s));
            }
        }

        // Set onClickListener for each radioButton
        RadioButton createRadioButtonAnswerAndSetOnClickListener(String string) {
            RadioButton radioButton = new RadioButton(mContext);
            radioButton.setText(string);
            radioButton.setOnClickListener(this);
            return radioButton;
        }

        @Override
        public void onClick(View view) {
            // If radioButton is checked, set view checked
            boolean checked = ((RadioButton) view).isChecked();

            // Since there are more than 1 question set, get position of current set
            int position = getAdapterPosition();

            // For loop for each position where radioButton is checked, get string
            // of radioButton checked, compare it to mCorrectAnswer, if correct,
            // increment correct/score value
            for (int i = 0; i <= position; i++) {
                if (checked) {
                    int radioButtonID = mRadioGroup.getCheckedRadioButtonId();
                    View radioButton = mRadioGroup.findViewById(radioButtonID);
                    int selectedAnswerIndex = mRadioGroup.indexOfChild(radioButton);
                    RadioButton r = (RadioButton) mRadioGroup.getChildAt(selectedAnswerIndex);
                    String selectedAnswer = r.getText().toString();

                    Object object = mArrayList.get(position);
                    String correctAnswer = ((Quiz) object).mCorrectAnswer;

                    if (selectedAnswer.equals(correctAnswer)) {
                        correct++;
                        editor.putInt("score", correct); // Update score interface
                        editor.apply();
                    }
                }
            }
            correct = 0; // Always set the score value to zero to reset score interface
        }
    }
}
