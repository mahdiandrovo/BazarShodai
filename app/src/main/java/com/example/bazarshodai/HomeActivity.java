package com.example.bazarshodai;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int ADD_NOTE_REQUEST = 100;
    public static final int EDIT_NOTE_REQUEST = 101;

    private String TAG = this.getClass().getSimpleName();
    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView_Note;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView_Note = (RecyclerView) findViewById(R.id.note_RecyclerView);
        recyclerView_Note.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_Note.setHasFixedSize(true);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.addNote_Button);
        floatingActionButton.setOnClickListener(this);

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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            //For drag and drop
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            //For swipe
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                homeViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(HomeActivity.this,"NOTE DELETED",Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView_Note);

        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {

                Log.d("NOTE_INFO",note.getTitle());

                Intent intent = new Intent(HomeActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID,note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE,note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_AMOUNT,note.getAmount());
                intent.putExtra(AddEditNoteActivity.EXTRA_AMOUNT_TYPE,note.getAmountType());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION,note.getDescription());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == floatingActionButton){
            Intent intent = new Intent(this, AddEditNoteActivity.class);
            startActivityForResult(intent,ADD_NOTE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            int amount = data.getIntExtra(AddEditNoteActivity.EXTRA_AMOUNT,1);
            String amount_type = data.getStringExtra(AddEditNoteActivity.EXTRA_AMOUNT_TYPE);
            String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);

            //Priority will be depended on amount
            Note note = new Note(title,description,amount,amount_type,amount);
            homeViewModel.insert(note);

            Toast.makeText(this,"NOTE SAVED",Toast.LENGTH_LONG).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID,-1);
            //ID sohould not be -1 because it is in resultCode == RESULT_OK
            //But i should check it
            //It is not necessary
            if (id == -1){
                Toast.makeText(this,"NOTE CAN'T BE UPDATED",Toast.LENGTH_LONG).show();
                return;
            } else {
                String title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
                int amount = data.getIntExtra(AddEditNoteActivity.EXTRA_AMOUNT,1);
                String amount_type = data.getStringExtra(AddEditNoteActivity.EXTRA_AMOUNT_TYPE);
                String description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);

                //Priority will be depended on amount
                Note note = new Note(title,description,amount,amount_type,amount);
                //To update id is also needed
                //Because id is also a property of Note class
                note.setId(id);

                homeViewModel.update(note);

                Toast.makeText(this,"NOTE IS UPDATED",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this,"NOTE NOT SAVED",Toast.LENGTH_LONG).show();
        }
    }
}
