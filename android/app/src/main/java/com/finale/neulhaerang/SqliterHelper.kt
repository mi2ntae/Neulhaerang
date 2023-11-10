package com.finale.neulhaerang

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SqliteHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table memo (id integer primary key autoincrement, date text)"
        Log.i("SQLITE", "create database")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertMemo(memo: Memo){
        val values = ContentValues()
        //넘겨줄 컬럼의 매개변수 지정
        values.put("date",memo.date)
        Log.i("SQLITE INSERT", memo.date)
        //쓰기나 수정이 가능한 데이터베이스 변수
        val wd = writableDatabase
        wd.insert("memo",null,values)
        Log.i("SQLITE INSERT", "INSERT SUCCESS")
        //사용이 끝나면 반드시 close()를 사용하여 메모리누수 가 되지않도록 합시다.
        wd.close()
    }

    fun deleteMemo(date: String){
        Log.i("SQLITE DELETE", date)
        //쓰기나 수정이 가능한 데이터베이스 변수
        val wd = writableDatabase
        wd.delete("memo","date = "+date, null)
        Log.i("SQLITE INSERT", "INSERT SUCCESS")
        //사용이 끝나면 반드시 close()를 사용하여 메모리누수 가 되지않도록 합시다.
        wd.close()
    }

    @SuppressLint("Range")
    fun selectMemo(nowDate: String):MutableList<Memo>{
        val list = mutableListOf<Memo>()
        //전체조회
        val selectAll = "select * from memo where date = '$nowDate'"
        Log.i("SQLITE SELECT", selectAll)
        //읽기전용 데이터베이스 변수
        val rd = readableDatabase
        //데이터를 받아 줍니다.
        val cursor = rd.rawQuery(selectAll,null)

        //반복문을 사용하여 list 에 데이터를 넘겨 줍시다.
        while(cursor.moveToNext()){
            val date = cursor.getString(cursor.getColumnIndex("date"))
            list.add(Memo(date))
        }
        Log.i("SQLITE SELECT", list.size.toString())
        cursor.close()
        rd.close()
        return list
    }
}

data class Memo(var date: String) {}
