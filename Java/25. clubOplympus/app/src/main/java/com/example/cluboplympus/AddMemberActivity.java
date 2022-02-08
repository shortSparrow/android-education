package com.example.cluboplympus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cluboplympus.data.ClubOlympusContract.MemberEntry;

public class AddMemberActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText sportNameEditText;
    private Spinner sexSpinner;
    private int sex = 0; // 0 - undefined, 1 - male, 2 - female
    private ArrayAdapter spinnerAdapter;

    Uri currentMemberUri;
    private static final int EDIT_MEMBER_LOADER = 2; // any number
    MemberCursorAdapter memberCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Intent intent = getIntent();
        currentMemberUri = intent.getData();

        if (currentMemberUri == null) {
            setTitle("Add new member");
            invalidateOptionsMenu();
        } else {
            setTitle("Edit the member");
            LoaderManager.getInstance(this).initLoader(EDIT_MEMBER_LOADER, null, this);

        }


        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        sportNameEditText = findViewById(R.id.sportNameEditText);
        sexSpinner = findViewById(R.id.sexSpinner);


        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sex_spinner,  android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(spinnerAdapter);
        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSex = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selectedSex)) {
                    if (selectedSex.equals("Male")) {
                        sex = MemberEntry.SEX_MALE;
                    } else if (selectedSex.equals("Female")) {
                        sex = MemberEntry.SEX_FEMALE;
                    } else {
                        sex = MemberEntry.SEX_UNKNOWN;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sex = MemberEntry.SEX_UNKNOWN;
            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (currentMemberUri == null) {
            MenuItem menuItem = menu.findItem(R.id.deleteMember);
            menuItem.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.saveMember:
                saveMember();
                return true;
            case R.id.deleteMember:
                showDeleteMemberDialog();
                return true;

            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteMemberDialog() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setMessage("Do ypu want delete this member?");
        build.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteMember();
            }
        });
        build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = build.create();
        alertDialog.show();
    }

    private void deleteMember() {
        if (currentMemberUri != null) {
            int rowsDeleted = getContentResolver().delete(currentMemberUri, null, null);

            String toastMessage;
            if (rowsDeleted == 0) {
                toastMessage = "Deleting member is failed";
            } else {
                toastMessage = "Deleting member successfully";
            }

            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public void saveMember() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String sport = sportNameEditText.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(MemberEntry.COLUMN_FIRST_NAME, firstName);
        values.put(MemberEntry.COLUMN_LAST_NAME, lastName);
        values.put(MemberEntry.COLUMN_SPORT, sport);
        values.put(MemberEntry.COLUMN_SEX, sex);

        String toastMessage = "";
        if (TextUtils.isEmpty(firstName)) {
            toastMessage = "first name is required";
        } else if (TextUtils.isEmpty(lastName)) {
            toastMessage = "last name is required";
        } else if (TextUtils.isEmpty(lastName)) {
            toastMessage = "sport name is required";
        }

        if (!TextUtils.isEmpty(toastMessage)) {
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
            return;
        }

        if (currentMemberUri == null) {
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(MemberEntry.CONTENT_URI, values);
            if (uri == null) {
                Toast.makeText(this, "Insertion of data in the table failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
            }
        } else {
           int rowsChanged = getContentResolver().update(currentMemberUri, values, null, null);
           if (rowsChanged == 0) {
               Toast.makeText(this, "Updated data filed", Toast.LENGTH_LONG).show();
           } else {
               Toast.makeText(this, "Member successfully updated", Toast.LENGTH_LONG).show();
           }
        }



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
                currentMemberUri,
                projection,
                null,
                null,
                null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            int idIndex = data.getColumnIndex(MemberEntry._ID);
            int firstNameIndex = data.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME);
            int lastNameIndex = data.getColumnIndex(MemberEntry.COLUMN_LAST_NAME);
            int sexIndex = data.getColumnIndex(MemberEntry.COLUMN_SEX);
            int sportIndex = data.getColumnIndex(MemberEntry.COLUMN_SPORT);

            String id = data.getString(idIndex);
            String firstName = data.getString(firstNameIndex);
            String lastName = data.getString(lastNameIndex);
            int sex = data.getInt(sexIndex);
            String sport = data.getString(sportIndex);

            firstNameEditText.setText(firstName);
            lastNameEditText.setText(lastName);
            sexSpinner.setSelection(sex);
            sportNameEditText.setText(sport);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        memberCursorAdapter.swapCursor(null);
    }
}