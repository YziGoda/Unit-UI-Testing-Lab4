package com.example.notev1;

import static com.example.notev1.MainActivity.ADD_NOTE_REQUEST_CODE;
import static com.example.notev1.MainActivity.DELETE_NOTE_REQUEST_CODE;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.io.File;

import java.util.ArrayList;


public class NotesUtils
{
    //Cant do anything else but this. :D Easier testing
    public IntentHelper intentHelper;

    public NotesUtils()
    {
        this.intentHelper = new IntentHelper();
    }

    public void loadNotes(ArrayList<String> listNotes, String path, ArrayAdapter<String> adapter) {
        listNotes.clear();

        File dir = new File(path);
        if (dir.exists() && dir.isDirectory())
        {
            File[] files = dir.listFiles((directory, name) -> name.toLowerCase().endsWith(".txt"));
            if (files != null)
            {
                for (File file : files)
                {
                    String fileName = file.getName().replaceFirst("[.][^.]+$", "");
                    listNotes.add(fileName);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    public boolean handle_menu(MenuItem item, MainActivity mainActivity, ArrayList<String> listNotes)
    {
        final int id = item.getItemId();
        if (id == R.id.add_note)
        {
            mainActivity.startActivityForResult(intentHelper.getIntent(mainActivity, AddNoteActivity.class), ADD_NOTE_REQUEST_CODE);
            return true;
        }
        if (id == R.id.delete_note)
        {
            Intent intent = intentHelper.getIntent(mainActivity, DeleteNoteActivity.class);
            intent.putStringArrayListExtra("notesList", listNotes); //TODO perduodam sarasa
            mainActivity.startActivityForResult(intent, DELETE_NOTE_REQUEST_CODE);
            return true;
        }
        return mainActivity.onOptionsItemSelected(item);
    }
}
