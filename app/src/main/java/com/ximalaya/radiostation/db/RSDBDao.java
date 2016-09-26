package com.lanou.radiostation.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lanou.radiostation.bean.Bean_frag_dingyueting_history;
import com.lanou.radiostation.bean.Bean_frag_dingyueting_recom;
import com.lanou.radiostation.util.UpdateTime;

/**
 * Created by user on 2016/7/27.
 */
public class RSDBDao  {
    RSOpenHelper helper;
    SQLiteDatabase db;
    /**
     * 创建数据库时使用
     * @param context
     *
     */
    public RSDBDao(Context context) {
        super();
        this.helper = new RSOpenHelper(context);
    }

    /**
     * 更新数据库时使用
     * @param context
     * @param newVersion
     */
    public RSDBDao(Context context,int newVersion) {
        super();
        this.helper = new RSOpenHelper(context,newVersion);
    }
    /**
     * 通过api实现插入数据库数据
     *
     */
    //获取更新时间
    UpdateTime updateTime=new UpdateTime();
    public void insert(String tableName,Bean_frag_dingyueting_recom bean_frag_dingyueting_recom){
        db = helper.getWritableDatabase();
        //一个类似于Map集合的类
        ContentValues values = new ContentValues();
        //注意：put方法中的key值要和数据库表中的字段值一样
        /*
        "create table dingyueting_subscription_table " +
                "(id integer primary key autoincrement, " +"albumId integer"+
                "title varchar(20)"+
                "coverLarge varchar(40), " +
                "updatedAt varchar(40)" +"pageId integer"+"pageSize integer"+"maxPageId integer)";
         */

        values.put("albumId", bean_frag_dingyueting_recom.data.album.albumId);
        values.put("pageId", bean_frag_dingyueting_recom.data.tracks.pageId);
        values.put("pageSize", bean_frag_dingyueting_recom.data.tracks.pageSize);
        values.put("maxPageId",bean_frag_dingyueting_recom.data.tracks.maxPageId);
        values.put("title", bean_frag_dingyueting_recom.data.tracks.list.get(0).title);
        values.put("albumTitle", bean_frag_dingyueting_recom.data.album.title);
        values.put("coverLarge", bean_frag_dingyueting_recom.data.album.coverLarge);
       String time=String.valueOf(bean_frag_dingyueting_recom.data.album.updatedAt);
        values.put("updatedAt", time);
        values.put("image", bean_frag_dingyueting_recom.data.tracks.imageBytes);
        values.put("nickname", bean_frag_dingyueting_recom.data.album.nickname);
        values.put("playtimes", bean_frag_dingyueting_recom.data.album.playTimes);
        db.insert(tableName, null, values);
        db.close();
    }
//    public void insert(String tableName,Bean_frag_dingyueting_recom bean_frag_dingyueting_recom){
//        db = helper.getWritableDatabase();
//        //一个类似于Map集合的类
//        ContentValues values = new ContentValues();
//        //注意：put方法中的key值要和数据库表中的字段值一样
//        /*
//        "create table dingyueting_subscription_table " +
//                "(id integer primary key autoincrement, " +"albumId integer"+
//                "title varchar(20)"+
//                "coverLarge varchar(40), " +
//                "updatedAt varchar(40)" +"pageId integer"+"pageSize integer"+"maxPageId integer)";
//         */
//
//        values.put("albumId", bean_frag_dingyueting_recom.data.album.albumId);
//        values.put("pageId", bean_frag_dingyueting_recom.data.tracks.pageId);
//        values.put("pageSize", bean_frag_dingyueting_recom.data.tracks.pageSize);
//        values.put("maxPageId",bean_frag_dingyueting_recom.data.tracks.maxPageId);
//        values.put("title", bean_frag_dingyueting_recom.data.tracks.list.get(0).title);
//        values.put("coverLarge", bean_frag_dingyueting_recom.data.album.coverLarge);
//        String time=String.valueOf(bean_frag_dingyueting_recom.data.album.updatedAt);
//        values.put("updatedAt", time);
//        values.put("image", bean_frag_dingyueting_recom.data.tracks.imageBytes);
//        db.insert(tableName, null, values);
//        db.close();
//    }
//replace into test values(6, 'ee', '55', '2005-01-01 01:01:01');
    public void replace(String tableName,Bean_frag_dingyueting_recom bean_frag_dingyueting_recom){
       db=helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("albumId", bean_frag_dingyueting_recom.data.album.albumId);
        values.put("pageId", bean_frag_dingyueting_recom.data.tracks.pageId);
        values.put("pageSize", bean_frag_dingyueting_recom.data.tracks.pageSize);
        values.put("maxPageId",bean_frag_dingyueting_recom.data.tracks.maxPageId);
        values.put("title", bean_frag_dingyueting_recom.data.tracks.list.get(0).title);
        values.put("coverLarge", bean_frag_dingyueting_recom.data.album.coverLarge);
        String time=String.valueOf(bean_frag_dingyueting_recom.data.album.updatedAt);
        values.put("updatedAt", time);
        values.put("image", bean_frag_dingyueting_recom.data.tracks.imageBytes);
        db.replace(tableName,null,values);
        db.close();
    }

    public void insert(Bean_frag_dingyueting_history bean_frag_dingyueting_history){
        db = helper.getWritableDatabase();
        String tableName="dingyueting_history_table";
        //一个类似于Map集合的类
        ContentValues values = new ContentValues();
        values.put("albumId", bean_frag_dingyueting_history.albumId);
        values.put("title", bean_frag_dingyueting_history.title);
        values.put("author", bean_frag_dingyueting_history.author);
        values.put("image", bean_frag_dingyueting_history.imageBytes);
        values.put("play_time_last", bean_frag_dingyueting_history.play_time_last);
        values.put("playUr", bean_frag_dingyueting_history.playUrl32);
        values.put("tag", bean_frag_dingyueting_history.tag);
        values.put("coverSmall", bean_frag_dingyueting_history.coverSmall);
        db.insert(tableName, null, values);
        db.close();
    }
    public void update(Bean_frag_dingyueting_history bean_frag_dingyueting_history){
        db = helper.getWritableDatabase();
        String tableName="dingyueting_history_table";
        ContentValues values = new ContentValues();
        values.put("title", bean_frag_dingyueting_history.title);
        values.put("author", bean_frag_dingyueting_history.author);
        values.put("image", bean_frag_dingyueting_history.imageBytes);
        values.put("play_time_last", bean_frag_dingyueting_history.play_time_last);
        values.put("playUr", bean_frag_dingyueting_history.playUrl32);
        values.put("coverSmall", bean_frag_dingyueting_history.coverSmall);
        db.update(tableName, values, "albumId = ?", new String[]{bean_frag_dingyueting_history.albumId+""});
    }
    public void update(String value,int albumId){
        db = helper.getWritableDatabase();
        String tableName="dingyueting_history_table";
        ContentValues values = new ContentValues();
        Log.e("ggggggg","ggggggggg");
        values.put("play_time_last", value);
        db.update(tableName, values, "albumId = ?", new String[]{albumId+""});
    }
//    public void replace(String tableName,Bean_frag_dingyueting_history bean_frag_dingyueting_history){
//        db=helper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("albumId", bean_frag_dingyueting_history.albumId);
//        values.put("title", bean_frag_dingyueting_history.title);
//        values.put("author", bean_frag_dingyueting_history.author);
//        values.put("duration_play_last", bean_frag_dingyueting_history.duration_play_last);
//        values.put("image", bean_frag_dingyueting_history.imageBytes);
//        values.put("play_time_last", bean_frag_dingyueting_history.play_time_last);
//        values.put("playUr", bean_frag_dingyueting_history.playUrl32);
//        db.replace(tableName,"",values);
//        db.close();
//    }

    /**
     * 通过谷歌api实现的删除方法
     * @param
     */
    public void delete(String tableName,String albumId){
        db = helper.getWritableDatabase();
        db.delete(tableName, "albumId = ?", new String[]{albumId});
        db.close();
    }
    public void delete(String tableName){
        db = helper.getWritableDatabase();
        db.delete(tableName, null, null);
        db.close();
    }

    /**
     * 通过谷歌api实现的更新方法
     * @param
     */
//    public void update(String hobby, String num){
//        db = helper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("hobby", hobby);
//        db.update("student", values, "num = ?", new String[]{num});
//    }

    /**
     * 通过谷歌api实现的查询方法
     * @param
     * @return
     */
    public Cursor select(String tableName){
        db = helper.getReadableDatabase();
		/*
		 * 参数2：代表查询的列名，传null查询所有类，
		 * 		   查询指定列就传列名
		 * 参数5,6,7  是一些条件排序分组的属性 传null即可
		 */

        Cursor cursor=db.query(tableName,null,null,null,null,null,null);

        return cursor;
    }
    public Cursor selectColumn(String tableName,String coloumName){
        db = helper.getReadableDatabase();
		/*
		 * 参数2：代表查询的列名，传null查询所有类，
		 * 		   查询指定列就传列名
		 * 参数5,6,7  是一些条件排序分组的属性 传null即可
		 */
         db.query(tableName,new String[]{""+coloumName},null,null,null,null,null,null);
        Cursor cursor=db.query(tableName,null,null,null,null,null,null);
        return cursor;
    }
    public Cursor select(String tableName,String selectionAgs){
        db = helper.getReadableDatabase();
		/*
		 * 参数2：代表查询的列名，传null查询所有类，
		 * 		   查询指定列就传列名
		 * 参数5,6,7  是一些条件排序分组的属性 传null即可
		 */
        Cursor cursor=db.query(tableName,null,"albumId = ?",new String[]{selectionAgs},null,null,null);
        return cursor;
    }
    public Cursor select(String tableName,int id){
        db = helper.getReadableDatabase();
		/*
		 * 参数2：代表查询的列名，传null查询所有类，
		 * 		   查询指定列就传列名
		 * 参数5,6,7  是一些条件排序分组的属性 传null即可
		 */
        Cursor cursor=db.query(tableName,null,"id=?",new String[]{""+id},null,null,null);
        return cursor;
    }
    public Cursor getMaxId(String tableName){
        db = helper.getReadableDatabase();
        String strSql = "select max(id) as maxId from"+" "+ tableName;

        Cursor mCursor = db.rawQuery(strSql, null);

        return mCursor;
    }
    public Cursor selectByOder(String tableName,String cloumnWanted,boolean isDesc){

        db = helper.getReadableDatabase();

		/*
		 * 参数2：代表查询的列名，传null查询所有类，
		 * 		   查询指定列就传列名
		 * 参数5,6,7  是一些条件排序分组的属性 传null即可
		 */
         //asc 升序
        Cursor cursor;
        if (isDesc){
            cursor=db.query(tableName, null, null, null, null, null,cloumnWanted+" desc");
        }else {
            cursor=db.query(tableName, null, null, null, null, null,cloumnWanted+" asc");
        }
        return cursor;
    }
}
