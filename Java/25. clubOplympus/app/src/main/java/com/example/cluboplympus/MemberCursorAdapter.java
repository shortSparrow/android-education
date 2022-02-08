package com.example.cluboplympus;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.example.cluboplympus.data.ClubOlympusContract.MemberEntry;

public class MemberCursorAdapter extends CursorAdapter {
    public MemberCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.member_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView idMemberValue = view.findViewById(R.id.idMemberValue);
        TextView firstNameMemberValue = view.findViewById(R.id.firstNameMemberValue);
        TextView lastNameMemberValue = view.findViewById(R.id.lastNameMemberValue);
        TextView sexMemberValue = view.findViewById(R.id.sexMemberValue);
        TextView sportNameMemberValue = view.findViewById(R.id.sportMemberValue);

        String id = cursor.getString(cursor.getColumnIndex(MemberEntry._ID));
        String firstName = cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME));
        String lastName = cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_LAST_NAME));
        String sex = cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_SEX));
        String correctSexValue;
        if (sex.equals(String.valueOf(MemberEntry.SEX_MALE))) {
            correctSexValue = "Male";
        } else if (sex.equals(String.valueOf(MemberEntry.SEX_FEMALE))) {
            correctSexValue = "Female";
        } else {
            correctSexValue = "Unknown";
        }

        String sport = cursor.getString(cursor.getColumnIndex(MemberEntry.COLUMN_SPORT));

        idMemberValue.setText(id);
        firstNameMemberValue.setText(firstName);
        lastNameMemberValue.setText(lastName);
        sexMemberValue.setText(correctSexValue);
        sportNameMemberValue.setText(sport);
    }
}
