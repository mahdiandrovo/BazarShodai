package com.example.bazarshodai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView_Note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView_Note = (RecyclerView) findViewById(R.id.note_RecyclerView);
        recyclerView_Note.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_Note.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView_Note.setAdapter(adapter);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        homeViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //Update view here
                adapter.setNotes(notes);
            }
        });
    }
}
