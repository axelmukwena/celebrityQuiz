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

import com.bumptech.glide.Glide;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Quiz> list;
    private Context context;

    // Constructor to initialize all arrayList
    QuizAdapter(List<Quiz> list, Context context) {
        this.list = list;
        this.context = context;
        getItemCount();
    }

    @NonNull
    @Override
    // Build view layout and call ViewHolder, QuizHolder class
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        return new QuizHolder(layoutInflater.inflate(R.layout.choice, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        // Get arguments from bridge class, Quiz
        Quiz quiz = (Quiz) list.get(position);
        QuizHolder quizHolder = (QuizHolder) viewHolder;
        quizHolder.viewQuestion.setText(String.format("%s. %s", position + 1, quiz.question));
        Glide.with(quizHolder.imageView.getContext()).load(quiz.imageUrl).into(quizHolder.imageView);
        quizHolder.createRadioButtons(quiz.answerOptions);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // Class to place items into view layout set by recyclerView
    public class QuizHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView viewQuestion;
        private RadioGroup radioGroup;
        private ImageView imageView;
        int correct = 0; // Initialize score value | if selected radioButton is correct

        // Interface instance to access and modify score value. See onClick function
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int scoreValue = sharedPreferences.getInt("score", 0);

        QuizHolder(@NonNull View itemView) {
            super(itemView);
            viewQuestion = itemView.findViewById(R.id.celebrityQuestion);
            imageView = itemView.findViewById(R.id.celebrityImage);
            radioGroup = itemView.findViewById(R.id.celebrityOption);
            itemView.findViewById(R.id.horizontalDivider);
        }

        // Create radioButtons and add view for each option answer in arrayAnswer, for each radioGroup
        void createRadioButtons(String[] arrayAnswer) {
            if (radioGroup.getChildAt(0) != null)
                radioGroup.removeAllViews();
            for (String s : arrayAnswer) {
                radioGroup.addView(createRadioButtonAnswerAndSetOnClickListener(s));
            }
        }

        // Set onClickListener for each radioButton
        RadioButton createRadioButtonAnswerAndSetOnClickListener(String string) {
            RadioButton radioButton = new RadioButton(context);
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
                    int radioButtonID = radioGroup.getCheckedRadioButtonId();
                    View radioButton = radioGroup.findViewById(radioButtonID);
                    int selectedAnswerIndex = radioGroup.indexOfChild(radioButton);
                    RadioButton r = (RadioButton) radioGroup.getChildAt(selectedAnswerIndex);
                    String selectedAnswer = r.getText().toString();

                    Quiz quiz = list.get(position);
                    String correctAnswer = quiz.correctAnswer;

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
