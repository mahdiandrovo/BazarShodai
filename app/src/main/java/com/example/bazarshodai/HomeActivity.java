package com.example.bazarshodai;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();
    private HomeViewModel homeViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        homeViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                //Update view here

                //For obervation the Database
                Note note;
                for (int i=0;i<notes.size();i++){
                    note = notes.get(i);
                    Log.d("BAZAR_LIST", note.getTitle());
                }

            }
        });
    }
}
