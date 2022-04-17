package com.du.pr15.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.du.pr15.model.Word;
import com.du.pr15.params.Params;
import java.util.Optional;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE "+ Params.TABLE_NAME + " ("
                + Params.KEY_ID + " INTEGER PRIMARY KEY, "
                + Params.KEY_WORD + " TEXT) ;";

        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addWord(Word word){
        Optional<Word> optionalWord = readWord();
        if(!optionalWord.isPresent()){
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues  contentValues = new ContentValues();
            contentValues.put(Params.KEY_WORD,word.getWord());
            sqLiteDatabase.insert(Params.TABLE_NAME,null, contentValues);
            sqLiteDatabase.close();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Optional<Word> readWord(){
        Optional<Word> optionalWord = Optional.empty();
        Word word = new Word();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String readWordQuery = "SELECT * FROM " + Params.TABLE_NAME + " ;";
        Cursor cursor = sqLiteDatabase.rawQuery(readWordQuery, null);
        if(cursor.moveToFirst()){
            word.setWord(cursor.getString(1));
            optionalWord = Optional.of(word);
        }
        sqLiteDatabase.close();
        cursor.close();
        return optionalWord;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateWord(Word word) {
        Optional<Word> optionalWord = readWord();
        if (optionalWord.isPresent()) {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(Params.KEY_WORD, word.getWord());
            sqLiteDatabase.update(Params.TABLE_NAME, contentValues, null, null);
            sqLiteDatabase.close();
        }
    }

    public void deleteWord(Word word){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Params.TABLE_NAME, null, null);
        sqLiteDatabase.close();
    }
}
