package com.frozenfrost.frozenfrost;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {
    private String tableName="USER";
    private static final String dataBaseName="FROZEN_FROST_DB123";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EMAIL = "email";
    public static final String CONTACTS_COLUMN_STREET = "street";
    public static final String CONTACTS_COLUMN_CITY = "place";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    private HashMap hp;
    ArrayList<String> columnList=new ArrayList<String>();
    ArrayList<String> columnTypeList=new ArrayList<String>();
    ArrayList<String> columnValidationList=new ArrayList<String>();
    //dbHelper.setTableName("FROZEN_FROST");



    public DBHelper(Context context)
    {
        super(context, dataBaseName, null, 2);
    }
    public void addColumn(String columnName,String columnType,String columnValidation){
        this.columnList.add(columnName);
        this.columnTypeList.add(columnType);
        this.columnValidationList.add(columnValidation);
    }

    public  void setTableName(String tableName) {
        this.tableName = tableName;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        StringBuffer createQueryString;
        int columnCount=0;
        addColumn("USER_ID", "INTEGER", "Primary key");
        addColumn("USER_NAME", "TEXT", "");
        addColumn("PASSWORD", "TEXT", "");

        System.out.println("Table NAme ==========>"+tableName);
        if(true) {
            createQueryString = new StringBuffer(" CREATE TABLE ");
            createQueryString.append(tableName);
            createQueryString.append(" ( ");
            Iterator itr = columnList.iterator();
            while (itr.hasNext()){
                createQueryString.append(itr.next());
                createQueryString.append(" ");
                createQueryString.append(columnTypeList.get(columnCount));
                createQueryString.append(" ");
                if(this.isValidNew(columnValidationList.get(columnCount))){
                    createQueryString.append(columnValidationList.get(columnCount));
                }

                if(columnCount!=columnList.size()-1){
                    createQueryString.append(" , ");
                }
                columnCount++;
            }
            createQueryString.append(" ) ");
            System.out.println("\n\n Query ======>"+createQueryString);
            try {
                db.execSQL(
                        createQueryString.toString()
                );
            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + this.tableName);
        onCreate(db);
    }

    public boolean insertRow(HashMap hMap)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Iterator itr= hMap.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry entry=(Map.Entry)itr.next();
            String columnName=(String)entry.getKey();
            String columnValue=(String)entry.getValue();
            contentValues.put(columnName, columnValue);
        }

        db.insert(this.tableName, null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+this.tableName+" where CONTACT_ID=?", new String[] { Integer.toString(id) } );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, this.tableName);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }
    public Integer deleteAllRecords(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(this.tableName,
                "",
                new String[] {});
    }
    public Integer deleteRecord (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(this.tableName,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAll()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+this.tableName, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(this.tableName)));
            res.moveToNext();
        }
        return array_list;
    }
    private boolean isValidNew(String str){
        boolean flag=false;
        if(!"".equals(str) && str!=null  &&  "-1".equals(str)){
            flag=true;
        }
        return flag;
    }

}


