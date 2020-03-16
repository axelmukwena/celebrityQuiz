package com.example.celebrityquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerFragment extends Fragment implements RecyclerViewAdapter.SingleClickListener {

    private RecyclerViewAdapter mAdapter;

    public RecyclerFragment() {
        // Required empty public constructor
    }

    public static RecyclerFragment newInstance() {
        return new RecyclerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        String[] list = new String[]{"Jaimie Hendrix", "David Bowie", "Jim Morrison", "Elvis Presley",
                "Mick Jagger", "Kurt Cobain", "Bob Dylan", "John Lennon", "Freddie Mercury", "Elton John", "Eric Clapton"};

        mAdapter = new RecyclerViewAdapter(list);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClickListener(int position, View view) {
        mAdapter.selectedItem();
    }
}