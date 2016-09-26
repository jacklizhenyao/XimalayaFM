package com.lanou.radiostation.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 2016/7/27.
 */
public class RSOpenHelper extends SQLiteOpenHelper {
    /**
     * 创建两一个数据库时候使用
     * @param context 上下文
     * @param name	  要创建的数据库的名字
     * @param factory  游标工厂   通常在创建数据库时候直接赋值null
     * @param version  代表创建数据库时候的版本号
     */
    public RSOpenHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    /**
     * 更新数据库时候使用
     * @param context
     * @param version
     */
    public RSOpenHelper(Context context, int version) {
        super(context, "rs.db", null, version);

    }

    /**
     * 第一次创建数据时候使用
     * @param context
     */
    public RSOpenHelper(Context context) {
        super(context, "rs.db", null, 1);

    }



    public RSOpenHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version,
                        DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);

    }

    public boolean tabbleIsExist(String tableName,String dbName){
        boolean result = false;
        if(tableName == null){
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String sql = "select count(*) as c from "+dbName+" where type ='table' and name ='"+tableName.trim()+"' ";
            cursor = db.rawQuery(sql, null);
            if(cursor.moveToNext()){
                int count = cursor.getInt(0);
                if(count>0){
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }


    /**
     * 通常在该方法中创建 数据库表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
		/*
		 * create table student创建的表名叫student
		 * primary key 代表该字段是主键 autoincrement代表自增长
		 * varchar(10) 代表该字段是字符类型
		 * 注意：创建表名的后面   需要有一个括号，所有的列都在括号中写
		 *
		 *  public class Album {
        public int albumId;
        public String title;
        public String coverLarge;
        public long updatedAt;
    }

    public class Tracks {
        public int pageId;
        public int pageSize;
        public int maxPageId;
        public int totalCount;
    }
		 */
         if (tabbleIsExist("dingyueting_subscription_table","rs.db")){
             //把表数据导入新表 删掉旧表
              //  String sql="ALTER TABLE Teachers ADD COLUMN Sex text";//追加一列
            //先按照删除表创建一个新表，新表不包含要删除的列，删掉旧表，在重新命名新表，删除一列
         }else {
             String createTable = "create table dingyueting_subscription_table " +
                     "(id integer primary key autoincrement, " +"albumId integer,"+
                     "title varchar(20),"+
                     "coverLarge varchar(40), " +"nickname varchar(40),"+"playtimes integer,"+"albumTitle varchar(40),"+
                     "updatedAt varchar(40)," +"pageId integer,"+"pageSize integer,"+"maxPageId integer,"+"image BLOB)";
             String createHistoryTable = "create table dingyueting_history_table " +
                     "(id integer primary key autoincrement," +"albumId integer,"+
                     "title varchar(20),"+"nickname  varchar(20),"+"tag varchar(20),"+
                     "author varchar(40), " +"play_time_last varchar(40),"+"playUr varchar(40),"+"coverSmall varchar(40),"+"image BLOB)";
             db.execSQL(createTable);
             db.execSQL(createHistoryTable);
         }

        Log.e("onCreate", "=====onCreate====");
    }

    /**
     * 数据库版本号增加的时候调用
     * 数据库版本号不能降低
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("onUpgrade", "=====onUpgrade====");
        Log.e("oldVersion",oldVersion+ "=========");
//        if(oldVersion< newVersion){
//            String createTable = "create table dingyueting_history_table " +
//                    "(id integer primary key autoincrement, " +"albumId integer"+
//                    "title varchar(20)"+
//                    "albumImage varchar(40)," +"duration integer"+"trackId integer)";
//            db.execSQL(createTable);
//            Log.i("onUpgrade", "=====更新数据库成功！====");
//        }
//        if (oldVersion< newVersion){
//            String createTable = "create table dingyueting_subscription_table " +
//                    "(id integer primary key autoincrement, " +"albumId integer,"+"image BLOB"+
//                    "title varchar(20),"+
//                    "coverLarge varchar(40), " +
//                    "updatedAt varchar(40)," +"pageId integer,"+"pageSize integer,"+"maxPageId integer)";
//            db.execSQL(createTable);
//        }
        onCreate(db);//对旧版数据库表 做修改
    }
}
