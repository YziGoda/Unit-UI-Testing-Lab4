package com.example.notev1;

import static com.example.notev1.MainActivity.ADD_NOTE_REQUEST_CODE;
import static com.example.notev1.MainActivity.DELETE_NOTE_REQUEST_CODE;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import android.content.ComponentName;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class NotesUtilsTest
{
    @Mock
    public ArrayAdapter<String> adapter;

    @Mock
    public MainActivity mainActivityMock;
    @Mock
    public IntentHelper intentHelperMock;
    @Mock
    public Intent intentMock;
    @Captor
    ArgumentCaptor<Intent> intentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void loadNotes()
    {
        //given
        NotesUtils NU = new NotesUtils();

        ArrayList<String> noob = new ArrayList<>();
        noob.add("KEKW");

        String path = "src\\test\\resources";

        //when
        NU.loadNotes(noob, path, adapter);
        //then
        verify(adapter, times(1)).notifyDataSetChanged();
        assertEquals(1, noob.size());
    }

    @Test
    public void test_Add_Note_Button()
    {
        //given
        NotesUtils NU = new NotesUtils();
        NU.intentHelper = intentHelperMock;
        when(intentHelperMock.getIntent(any(),any())).thenReturn(intentMock);
        ComponentName componentName = mock(ComponentName.class);
        when(intentMock.getComponent()).thenReturn(componentName);
        when(componentName.getClassName()).thenReturn("com.example.notev1.AddNoteActivity");

        MenuItem addNoteMenuItem = mock(MenuItem.class);
        when(addNoteMenuItem.getItemId()).thenReturn(R.id.add_note);
        ArrayList<String> listNotes = new ArrayList<>();
        listNotes.add("ALO");
        //when
        boolean result = NU.handle_menu(addNoteMenuItem, mainActivityMock, listNotes);
        //then
        verify(mainActivityMock).startActivityForResult(intentCaptor.capture(), eq(ADD_NOTE_REQUEST_CODE));
        Intent capturedIntent = intentCaptor.getValue();
        assertEquals(AddNoteActivity.class.getName(), capturedIntent.getComponent().getClassName());
        assertEquals(true, result);
    }

    @Test
    public void test_Delete_Note_Button()
    {
        //given
        NotesUtils NU = new NotesUtils();
        NU.intentHelper = intentHelperMock;
        when(intentHelperMock.getIntent(any(),any())).thenReturn(intentMock);
        ComponentName componentName = mock(ComponentName.class);
        when(intentMock.getComponent()).thenReturn(componentName);
        when(componentName.getClassName()).thenReturn("com.example.notev1.DeleteNoteActivity");

        MenuItem delMenuItem = mock(MenuItem.class);
        when(delMenuItem.getItemId()).thenReturn(R.id.delete_note);
        ArrayList<String> listNotes = new ArrayList<>();
        listNotes.add("ATIA");
        //when
        boolean result = NU.handle_menu(delMenuItem, mainActivityMock, listNotes);
        //then
        verify(mainActivityMock).startActivityForResult(intentCaptor.capture(), eq(DELETE_NOTE_REQUEST_CODE));
        Intent capturedIntent = intentCaptor.getValue();
        assertEquals(DeleteNoteActivity.class.getName(), capturedIntent.getComponent().getClassName());
        assertEquals(true, result);
    }

}