package com.example.bazarshodai;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDatabase noteDatabase = NoteDatabase.getDatabase(application);
        noteDao = noteDatabase.noteDao();
        allNotes = noteDao.getAllNote();
    }

    //For Insert operation in Database
    public void insert(Note note){
        new InsertAsyncTask(noteDao).execute(note);
    }

    private static class InsertAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        public InsertAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    //For Update operation in Database
    public void update(Note note){
        new UpdateAsyncTask(noteDao).execute(note);
    }

    private static class UpdateAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        public UpdateAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    //For Delete operation in Database
    public void delete(Note note){
        new DeleteAsyncTask(noteDao).execute(note);
    }

    private static class DeleteAsyncTask extends AsyncTask<Note,Void,Void>{

        private NoteDao noteDao;

        public DeleteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }


    public void deleteAllNotes(){
        new DeleteALLNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class DeleteALLNotesAsyncTask extends AsyncTask<Void,Void,Void>{

        private NoteDao noteDao;

        public DeleteALLNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

}
