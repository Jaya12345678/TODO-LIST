package com.example.taskset

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDBHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME="notesapp.db"
        private const val DATABASE_VERSION=2
        private const val TABLE_NAME="allnotes"
        private const val COLUMN_ID="id"
        private const val COLUMN_TITLE="title"
        private const val COLUMN_CONTENT="content"
        private const val COLUMN_DUE_DATE = "due_date"
        private const val COLUMN_PRIORITY = "priority"
        private const val COLUMN_STATUS = "status"
        private const val COLUMN_CATEGORY = "category"



    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery="CREATE TABLE $TABLE_NAME(" +
                "$COLUMN_ID INTEGER PRIMARY KEY," +
                "$COLUMN_TITLE TEXT," +
                "$COLUMN_CONTENT TEXT," +
                "$COLUMN_DUE_DATE LONG," +
                "$COLUMN_PRIORITY TEXT," +
                "$COLUMN_STATUS TEXT," +
                "$COLUMN_CATEGORY TEXT"+
                ")"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNote(note: Note){
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_TITLE,note.title)
            put(COLUMN_CONTENT,note.content)
            put(COLUMN_DUE_DATE, note.dueDateMillis)
            put(COLUMN_PRIORITY, note.priority)
            put(COLUMN_STATUS, note.status)
            put(COLUMN_CATEGORY,note.category)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }

    fun getAllNotes(): List<Note>{
        val notesList= mutableListOf<Note>()
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME"
        val cursor=db.rawQuery(query,null)

        while(cursor.moveToNext()){
            val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val due_date=cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE))
            val priority=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
            val status=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
            val category=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))



            val note=Note(id,title,content,due_date,priority,status,category)
            notesList.add(note)

        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateNote(note: Note)
    {
        val db=writableDatabase
        val values=ContentValues().apply {
            put(COLUMN_TITLE,note.title)
            put(COLUMN_CONTENT,note.content)
            put(COLUMN_DUE_DATE, note.dueDateMillis)
            put(COLUMN_PRIORITY, note.priority)
            put(COLUMN_STATUS, note.status)
            put(COLUMN_CATEGORY,note.category)
        }
        val whereClause="$COLUMN_ID=?"
        val whereArgs= arrayOf(note.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }
    // retrieving the data
    fun getNoteById(noteId:Int):Note{
        val db=readableDatabase
        val query="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID=$noteId"
        val cursor=db.rawQuery(query,null)
        cursor.moveToFirst()

        val id=cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
        val due_date=cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DUE_DATE))
        val priority=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRIORITY))
        val status=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
        val category=cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))

        cursor.close()
        db.close()
        return Note(id,title,content,due_date,priority,status,category)

    }

    fun deleteNote(noteId: Int)
    {
        val db=writableDatabase
        val whereClause="$COLUMN_ID=?"
        val whereArgs= arrayOf(noteId.toString())
        db.delete(TABLE_NAME,whereClause,whereArgs)
        db.close()
    }
}