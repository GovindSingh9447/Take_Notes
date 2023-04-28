package com.ranawattech.takenotes.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ranawattech.takenotes.R;
import com.ranawattech.takenotes.database.NotesDatabase;
import com.ranawattech.takenotes.entites.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText inputNoteTitle, inputeNoteSubtitle, inputeNoteText;
    private TextView textDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        ImageView back=findViewById(R.id.imageBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        inputNoteTitle=findViewById(R.id.inputNoteTitle);
        inputeNoteSubtitle=findViewById(R.id.inputNoteSubtitle);
        inputeNoteText=findViewById(R.id.typeNotes);
        textDateTime=findViewById(R.id.textDateTime);

        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd mm yy hh:mm:ss", Locale.getDefault())
                        .format(new Date())
        );

        ImageView imagesave =findViewById(R.id.imgsave);
        imagesave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotes();
            }
        });
    }


    private void saveNotes(){
        if (inputNoteTitle.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Note title can't be empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (inputeNoteSubtitle.getText().toString().trim().isEmpty()
        && inputeNoteText.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Note can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        final Note note =new Note();
        note.setTitle(inputNoteTitle.getText().toString());
        note.setSubtitle(inputeNoteSubtitle.getText().toString());
        note.setNote_text(inputeNoteText.getText().toString());
        note.setDate_time(textDateTime.getText().toString());


        class SaveNotesTask extends AsyncTask<Void, Void, Void > {

            protected  Void doInBackground(Void... voids){
                NotesDatabase.getNotesDatabase(getApplicationContext());
                return null;
            }

            protected void onPostExecute(Void avoid){
                super.onPostExecute(avoid);
                Intent intent =new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        }

        new SaveNotesTask().execute();

    }
}