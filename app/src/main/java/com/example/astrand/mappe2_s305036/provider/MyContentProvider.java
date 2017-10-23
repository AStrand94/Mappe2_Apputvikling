package com.example.astrand.mappe2_s305036.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.astrand.mappe2_s305036.MyApp;
import com.example.astrand.mappe2_s305036.entities.Message;
import com.example.astrand.mappe2_s305036.entities.Student;


public class MyContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.astrand.mappe2_s305036.provider";

    public static final Uri URI_STUDENT = Uri.parse(
            "content://" + AUTHORITY + "/" + Student.TABLE_NAME);

    public static final Uri URI_MESSAGE = Uri.parse(
            "content://" + AUTHORITY + "/" + Message.TABLE_NAME);

    private static final int CODE_STUDENT_DIR = 1;

    private static final int CODE_STUDENT_ITEM = 2;

    private static final int CODE_MESSAGE_DIR = 3;

    private static final int CODE_MESSAGE_ITEM = 4;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        MATCHER.addURI(AUTHORITY, Student.TABLE_NAME, CODE_STUDENT_DIR);
        MATCHER.addURI(AUTHORITY, Student.TABLE_NAME + "/*", CODE_STUDENT_ITEM);
        MATCHER.addURI(AUTHORITY, Message.TABLE_NAME, CODE_MESSAGE_DIR);
        MATCHER.addURI(AUTHORITY, Message.TABLE_NAME + "/*", CODE_MESSAGE_ITEM);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final int code = MATCHER.match(uri);

        if (code == CODE_MESSAGE_DIR || code == CODE_MESSAGE_ITEM
                || code == CODE_STUDENT_DIR || code == CODE_STUDENT_ITEM){

            final Context context = getContext();
            final Cursor cursor;

            if (context == null) return null;

            if (code == CODE_STUDENT_DIR){
                cursor = MyApp.getDatabase().studentDao().selectAll();
            }else if (code == CODE_STUDENT_ITEM){
                cursor = MyApp.getDatabase().studentDao().selectById(ContentUris.parseId(uri));
            }else if (code == CODE_MESSAGE_DIR){
                cursor = MyApp.getDatabase().messageDao().selectAll();
            }else { //CODE_MESSAGE_ITEM
                cursor = MyApp.getDatabase().messageDao().selectById(ContentUris.parseId(uri));
            }

            cursor.setNotificationUri(context.getContentResolver(),uri);
            return cursor;
        }else{
            throw new IllegalArgumentException("No such uri. Uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (MATCHER.match(uri)){
            case CODE_STUDENT_ITEM:
                return "vnd.android.cursor.item" + AUTHORITY + "." + Student.TABLE_NAME;
            case CODE_STUDENT_DIR:
                return "vnd.android.cursor.dir" + AUTHORITY + "." + Student.TABLE_NAME;
            case CODE_MESSAGE_ITEM:
                return "vnd.android.cursor.item" + AUTHORITY + "." + Message.TABLE_NAME;
            case CODE_MESSAGE_DIR:
                return "vnd.android.cursor.dir" + AUTHORITY + "." + Message.TABLE_NAME;
            default:
                throw new IllegalArgumentException("No such uri. Uri: " + uri);

        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        if (getContext() == null) return null;
        long id;
        switch (MATCHER.match(uri)){
            case CODE_STUDENT_ITEM:
            case CODE_STUDENT_DIR:
                id = MyApp.getDatabase().studentDao().insertAndGetId(Student.fromContentValues(values));
                getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,id);
            case CODE_MESSAGE_ITEM:
            case CODE_MESSAGE_DIR:
                id = MyApp.getDatabase().messageDao().insertAndGetId(Message.fromContentValues(values));
                getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri,id);
            default:
                throw new IllegalArgumentException("No such uri. Uri: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final Context context = getContext();

        if (context == null) return 0;

        if (MATCHER.match(uri) == CODE_STUDENT_ITEM || MATCHER.match(uri) == CODE_STUDENT_DIR){
            final long id = ContentUris.parseId(uri);
            final int count = MyApp.getDatabase().studentDao().deleteById(id);
            context.getContentResolver().notifyChange(uri,null);
            return count;
        }else if (MATCHER.match(uri) == CODE_MESSAGE_ITEM || MATCHER.match(uri) == CODE_MESSAGE_DIR){
            final long id = ContentUris.parseId(uri);
            final int count = MyApp.getDatabase().messageDao().deleteById(id);
            context.getContentResolver().notifyChange(uri,null);
            return count;
        }else{
            throw new IllegalArgumentException("No such uri. Uri: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Context context = getContext();
        if (context == null) return 0;

        switch (MATCHER.match(uri)){
            case CODE_MESSAGE_DIR:
            case CODE_MESSAGE_ITEM:
                final Message message = Message.fromContentValues(values);
                MyApp.getDatabase().messageDao().updateMessage(message);
                return 1; //will always affect one
            case CODE_STUDENT_DIR:
            case CODE_STUDENT_ITEM:
                final Student student = Student.fromContentValues(values);
                MyApp.getDatabase().studentDao().updateStudents(student);
                return 1; //will always affect one
            default:
                throw new IllegalArgumentException("No such uri. Uri: " + uri);
        }
    }
}
