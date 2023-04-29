package com.ranawattech.takenotes.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ranawattech.takenotes.R;
import com.ranawattech.takenotes.activites.CreateNoteActivity;
import com.ranawattech.takenotes.adapter.NoteAdapter;
import com.ranawattech.takenotes.database.NotesDatabase;
import com.ranawattech.takenotes.entites.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView addNotes;

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    private RecyclerView notesRecycleview;
    private List<Note> noteList;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          addNotes= findViewById(R.id.addNotes);
          addNotes.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  startActivityForResult(
                          new Intent(getApplicationContext(), CreateNoteActivity.class),
                          REQUEST_CODE_ADD_NOTE
                  );
              }
          });
          notesRecycleview=findViewById(R.id.notesRecycleview);
          notesRecycleview.setLayoutManager( new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
          noteList = new ArrayList<>();
          noteAdapter=new NoteAdapter(noteList);
          notesRecycleview.setAdapter(noteAdapter);


          getNotes();
    }

    private void getNotes() {
        @SuppressLint("StaticFieldLeak")
        class GetNoteTask extends AsyncTask<Void, Void , List<Note>>{


            @Override
            protected List<Note> doInBackground(Void... voids){
                return NotesDatabase.getNotesDatabase(getApplicationContext()).noteDao().getAllNotes();


            }
            @Override
            protected void onPostExecute(List<Note> notes){
                super.onPostExecute(notes);
                System.out.println("My_Notes"+notes.toString());
                Log.d("My_Notes", notes.toString());
                if (noteList.size()==0){
                    noteList.addAll(notes);
                    noteAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "I am getting", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    noteList.add(0, notes.get(0));
                    noteAdapter.notifyItemChanged(0);
                    Toast.makeText(MainActivity.this, "I am not getting", Toast.LENGTH_SHORT).show();
                }
                notesRecycleview.smoothScrollToPosition(0);
            }

        }
        new GetNoteTask().execute();
    }
}