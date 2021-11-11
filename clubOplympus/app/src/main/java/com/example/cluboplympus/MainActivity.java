package com.example.cluboplympus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cluboplympus.data.ClubOlympusContract.MemberEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    TextView memberInfo;
    private static final int MEMBER_LOADER = 1; // any number
    MemberCursorAdapter memberCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memberInfo = findViewById(R.id.memberSavedInfo);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });

        memberCursorAdapter = new MemberCursorAdapter(this, null);
        ListView memberList = findViewById(R.id.memberList);
        memberList.setAdapter(memberCursorAdapter);
        memberList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                Uri currentMemberUri = ContentUris.withAppendedId(MemberEntry.CONTENT_URI, id);
                intent.setData(currentMemberUri);
                startActivity(intent);
            }
        });
        LoaderManager.getInstance(this).initLoader(MEMBER_LOADER, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                MemberEntry._ID,
                MemberEntry.COLUMN_FIRST_NAME,
                MemberEntry.COLUMN_LAST_NAME,
                MemberEntry.COLUMN_SPORT,
                MemberEntry.COLUMN_SEX,
        };
        CursorLoader cursorLoader = new CursorLoader(
                this,
                MemberEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        memberCursorAdapter.swapCursor(data);
    }

    // удаляем не нужные строки после удаления или изменения, что бы не было утечки памяти
    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        memberCursorAdapter.swapCursor(null);
    }
}