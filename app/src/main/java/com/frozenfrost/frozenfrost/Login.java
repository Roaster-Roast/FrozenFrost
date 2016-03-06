package com.frozenfrost.frozenfrost;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

public class Login extends Fragment{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login, container, false);
    }
    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        final Button send =(Button)view.findViewById(R.id.login_button);
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Log.i("ContactUs", "Send Button Clicked.");
                SQLiteDatabase.CursorFactory factory=new SQLiteDatabase.CursorFactory() {
                    @Override
                    public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
                        return null;
                    }
                };
                //String fromEmail = getResources().getString(R.string.from_email_id);
                //String fromPassword = getResources().getString(R.string.from_email_password);
                String userName = ((TextView) view.findViewById(R.id.login_edittext1)).getText().toString();
                String password = ((TextView) view.findViewById(R.id.login_edittext2)).getText().toString();
                SQLiteDatabase mydatabase = SQLiteDatabase.openDatabase("/data/data/com.frozenfrost.frozenfrost/databases/FROZEN_FROST_DB123", factory, SQLiteDatabase.CREATE_IF_NECESSARY,null);
                //mydatabase.execSQL("SELECT * FROM USER WHERE USERNAME=? AND PASSWORD=?", new Object[]{userName, password});
                Cursor cur=mydatabase.rawQuery("SELECT USER_NAME,PASSWORD FROM USER WHERE USER_NAME=? AND PASSWORD=?", new String[]{userName, password});
                cur.moveToFirst();
                while (cur.isAfterLast() == false) {
                    Toast.makeText(getActivity(),cur.getString(cur.getColumnIndex("USER_NAME"))+"########"+cur.getString(cur.getColumnIndex("PASSWORD")), Toast.LENGTH_SHORT).show();
                    cur.moveToNext();
                }
                cur.close();



            }
        });
    }
}
