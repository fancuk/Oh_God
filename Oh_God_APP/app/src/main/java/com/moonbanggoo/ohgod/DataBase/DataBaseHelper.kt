package com.moonbanggoo.ohgod.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper : SQLiteOpenHelper {
    constructor(
        context: Context, name: String,
        factory: SQLiteDatabase.CursorFactory?, version: Int)
            : super(context,name,factory,version) {
    }
    override fun onCreate(db: SQLiteDatabase?) {
        print("onCreate 호출 됨.")
        var sql = ("CREATE TABLE IF NOT EXISTS schedule( date varchar(20), plan varchar(50), UNIQUE(date, plan) )")
        db?.execSQL(sql)
        print("schedule 테이블 생성")
        sql = "CREATE TABLE IF NOT EXISTS alarm( regular varchar(20) )"
        db?.execSQL(sql)
        print("alarm 테이블 생성")
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}