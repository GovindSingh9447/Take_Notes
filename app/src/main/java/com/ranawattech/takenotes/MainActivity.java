package com.ranawattech.takenotes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView addNotes;

    public static final int REQUEST_CODE_ADD_NOTE = 1;

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
    }
}