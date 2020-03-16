package com.example.celebrityquiz;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.DataObjectHolder> {

    private String[] mData;
    private static SingleClickListener sClickListener;
    private static int sSelected = -1;

    RecyclerViewAdapter(String[] mData) {
        this.mData = mData;
    }

    static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextView;
        RadioButton mRadioButton;

        DataObjectHolder(View itemView) {
            super(itemView);
            this.mTextView = itemView.findViewById(R.id.celebrityName);
            this.mRadioButton = itemView.findViewById(R.id.checkButton);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            sSelected = getAdapterPosition();
            sClickListener.onItemClickListener(getAdapterPosition(), view);
        }
    }

    void selectedItem() {
        notifyDataSetChanged();
    }

    void setOnItemClickListener(SingleClickListener clickListener) {
        sClickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_name, parent, false);

        return new RecyclerViewAdapter.DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.DataObjectHolder holder, int position) {
        holder.mTextView.setText(mData[position]);
        holder.mRadioButton.setChecked(sSelected == position);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    interface SingleClickListener {
        void onItemClickListener(int position, View view);
    }
}
