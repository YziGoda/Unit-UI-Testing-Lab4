package com.example.notev1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.notev1.databinding.ActivityMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NOTE_REQUEST_CODE = 1;
    public static final int DELETE_NOTE_REQUEST_CODE = 2;
    public ArrayList<String> listNotes = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNotes);
        binding.listView.setAdapter(adapter);

        loadNotes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        NotesUtils NU = new NotesUtils();
        return NU.handle_menu(item, this, listNotes);
    }


    private void loadNotes()
    {
        NotesUtils NU = new NotesUtils();
        String path = getFilesDir() + "/notes";
        NU.loadNotes(listNotes, path, adapter);
    }

    protected void onActivityResult(int request, int result, Intent data)
    {
        super.onActivityResult(request, result, data);
        if (request == ADD_NOTE_REQUEST_CODE && result == AddNoteActivity.RESULT_OK)
        {
            loadNotes();
        }
        else if (request == DELETE_NOTE_REQUEST_CODE && result == DeleteNoteActivity.RESULT_OK)
        {
            loadNotes();
        }
    }
}