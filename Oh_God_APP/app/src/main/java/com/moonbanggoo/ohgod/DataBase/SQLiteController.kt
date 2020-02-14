package com.moonbanggoo.ohgod.DataBase
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.moonbanggoo.ohgod.Model.PlanModel
import com.moonbanggoo.ohgod.App
import java.lang.Exception

object SQLiteController {
    var dbName ="Oh_God"
    lateinit var database : SQLiteDatabase

    fun openDatabase(){
        Log.d("Damin Log","openDatabase 호출 됨")
        /*database =
            SQLiteDatabase.openOrCreateDatabase("Oh_God",null)
        if(database != null) print("database open")*/
        var helper = DatabaseHelper(App.applicationContext(),
            "Oh_God",null,3)
        database = helper.writableDatabase
        print("데이터 베이스 오픈!")
    }
    fun createTable(){
        Log.d("Damin Log","createTable 호출")
        if(database!=null){
            var query = ("CREATE TABLE IF NOT EXISTS schedule( date varchar(20), plan varchar(50), UNIQUE(date, plan) )")
            database.execSQL(query)
            Log.d("Damin Log","스케줄 테이블 생성 됨.")
            query = "CREATE TABLE IF NOT EXISTS alarm( regular varchar(20) )"
            database.execSQL(query)
            Log.d("Damin Log","알람 테이블 생성됨.")
            query = "CREATE TABLE IF NOT EXISTS city ( cityName varchar(50) )"
            database.execSQL(query)
        }
        else{
            Log.d("Damin Log","데이터 베이스 부터 오픈 해주세요.")
        }
    }
    fun insertData(planModel : PlanModel?){
        Log.d("Damin Log","insertData 호출 됨")
        var date : String? = planModel?.plan_date
        var plan : String? = planModel?.plan_name
        if(database != null){
            try {
                var sql = ("INSERT INTO schedule VALUES(\"" + date + "\",\"" + plan + "\")")
                database.execSQL(sql)
                Log.d("Damin Log","데이터 추가 함")
            }
            catch(e : Exception){
                Log.d("Damin Log","중복 데이터 입니다.")
            }
        }
        else{
            Log.d("Damin Log","데이터 베이스 부터 오픈 해주세요.")
        }
    }
    fun DeleteWantScheduleData(planModel : PlanModel?){
        Log.d("Database_Log","DeleteWantScheduleData 호출 됨")
        var date : String? = planModel?.plan_date
        Log.d("Database_Log","date : " + planModel!!.plan_date)

        var plan : String? = planModel?.plan_name
        Log.d("Database_Log","date : " + planModel!!.plan_name)


        if(database != null){
            var sql = ("DELETE FROM schedule WHERE date = \"" + date + "\" AND plan = \""+ plan + "\"")
            database.execSQL(sql)
        }
        else{
            Log.d("Damin Log","데이터 베이스 부터 오픈 해주세요.")
        }
    }
    fun insertData(time : String){
        if(database != null){
            if(selectAlarmData().equals("")){
                val sql = ("INSERT INTO alarm VALUES(\""+time+"\")")
                database.execSQL(sql)
                Log.d("Damin Log","데이터 추가 함")
            }
            else{
                val sql = ("UPDATE alarm SET regular = \""+ time+"\"")
                database.execSQL(sql)
                Log.d("Damin Log","데이터 업데이트 함")
            }
        }
        else{
            Log.d("Damin Log","데이터 베이스 부터 오픈 해주세요.")
        }
    }
    fun insertCity(cityName : String){
        if(database!=null){
            if(selectCity().equals("")){
                val sql = ("INSERT INTO city VALUES(\""+ cityName + "\" )")
                database.execSQL(sql)
                Log.d("Damin Log", "City 추가 함")
            }
            else{
                val sql = ("UPDATE city SET cityName = \"" + cityName + "\"")
                database.execSQL(sql)
                Log.d("Damin Log","City Update")
            }
        }
        else{
            Log.d("Damin Log","데이터 베이스 부터 오픈 해주세요.")
        }
    }

    fun selectTodayScheduleData(date : String) : ArrayList<PlanModel>{
        var scheduleList = ArrayList<PlanModel>()
        if(database != null){
            var sql = ("SELECT * FROM schedule WHERE date = \""+ date + "\"  ORDER BY date ASC")
            var cursor : Cursor = database.rawQuery(sql,null)
            for(i in 1..cursor.count){
                cursor.moveToNext()
                var date = cursor.getString(0)
                var plan = cursor.getString(1)
                Log.d("Database Log Log","Date : " + date + " Plan : " + plan)
                var planModel = PlanModel(date,plan)
                scheduleList.add(planModel)
            }
            cursor.close()
        }
        else {
            Log.d("Database Log","데이터 베이스 부터 오픈 해주세요")
        }
        return scheduleList
    }
    fun selectScheduleData(date : String) : ArrayList<PlanModel>{
        var scheduleList = ArrayList<PlanModel>()
        if(database != null){
            var sql = ("SELECT * FROM schedule WHERE date >= \"" + date + "\" ORDER BY date ASC")
            var cursor : Cursor = database.rawQuery(sql,null)
            Log.d("Damin Log","조회된 데이터 개수 : " + cursor.count)
            for(i in 1..cursor.count){
                cursor.moveToNext()
                var date = cursor.getString(0)
                var plan = cursor.getString(1)
                Log.d("Damin Log","Date : " + date + " Plan : " + plan)
                var planModel = PlanModel(date,plan)
                scheduleList.add(planModel)
            }
            cursor.close()
        }
        else {
            Log.d("Damin Log","데이터 베이스 부터 오픈 해주세요")
        }
        return scheduleList
    }
//    fun selectScheduleData() : ArrayList<PlanModel>{
//        var scheduleList = ArrayList<PlanModel>()
//        if(database != null){
//            var sql = ("SELECT * FROM schedule ORDER BY date ASC")
//            var cursor : Cursor = database.rawQuery(sql,null)
//            Log.d("Damin Log","조회된 데이터 개수 : " + cursor.count)
//            for(i in 1..cursor.count){
//                cursor.moveToNext()
//                var date = cursor.getString(0)
//                var plan = cursor.getString(1)
//                Log.d("Damin Log","Date : " + date + " Plan : " + plan)
//                var planModel = PlanModel(plan,date)
//                scheduleList.add(planModel)
//            }
//            cursor.close()
//        }
//        else {
//            Log.d("Damin Log","데이터 베이스 부터 오픈 해주세요")
//        }
//        return scheduleList
//    }
    fun selectAlarmData() : String{
        var scheduleList = ""
        var time : String = ""
        if(database != null){
            var sql = ("SELECT * FROM alarm")
            var cursor : Cursor = database.rawQuery(sql,null)
            for(i in 1..cursor.count){
                cursor.moveToNext()
                time = cursor.getString(0)
                Log.d("Damin Log","Time : " + time)
            }
            cursor.close()
        }
        else {
            Log.d("Damin Log","데이터 베이스 부터 오픈 해주세요")
        }
        return time
    }
    fun selectCity() : String{
        var cityName = ""
        if(database!=null){
            var sql =("SELECT * FROM city")
            var cursor : Cursor = database.rawQuery(sql,null)
            for(i in 1..cursor.count){
                cursor.moveToNext()
                cityName = cursor.getString(0)
                Log.d("Damin Log", "cityName : " + cityName)
            }
            cursor.close()
        }
        else{
            Log.d("Damin Log","데이터 베이스 부터 오픈 해주세요")
        }
        return cityName
    }
    fun DeleteScheduleData(time : String){
        if(database!= null){
            var sql = ("DELETE FROM schedule WHERE date < \"" + time + "\"")
            database.execSQL(sql)
        }
        else{
            Log.d("Damin Log","데이터 베이스 부터 오픈 해주세요")
        }
    }
}