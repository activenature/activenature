package com.active.chdating.database;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

import com.active.chdating.net.Api;
import com.active.chdating.net.VolleySingleton;
import com.active.chdating.net.ZhihuDailyStory;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

public class CacheService extends Service {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private static final String TAG=CacheService.class.getSimpleName();
    public static final int TYPE_ZHIHU=0X00;
    private LocalReceiver receiver=new LocalReceiver();


    @Override
    public void onCreate(){
        super.onCreate();
        databaseHelper=new DatabaseHelper(this,"History.db",null,1);
        database=databaseHelper.getWritableDatabase();

        IntentFilter filter=new IntentFilter();
        filter.addAction("com.active.zhihudaily.LOCAL_BROADCAST");
        LocalBroadcastManager manager=LocalBroadcastManager.getInstance(this);
        manager.registerReceiver(receiver,filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public boolean onUnbind(Intent intent){
        return super.onUnbind(intent);
    }

    private void startZAhihuCache(final long id){
        Cursor cursor=database.query("Zhihu",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                if((cursor.getLong(cursor.getColumnIndex("zhihu_id"))==id)&&(cursor.getString(cursor.getColumnIndex("zhihu_content")).equals(""))){
                    final StringRequest request=new StringRequest(Request.Method.GET,Api.ZHIHU_NEWS + id, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Gson gson = new Gson();
                            ZhihuDailyStory story = gson.fromJson(response, ZhihuDailyStory.class);
                            if(story.getType()==1){
                                StringRequest request=new StringRequest(Request.Method.GET, story.getShareUrl(), new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        ContentValues values=new ContentValues();
                                        values.put("zhihu_content",response);
                                        database.update("Zhihu",values,"zhihu_id=?",new String[]{String.valueOf(id)});
                                        values.clear();

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });
                                request.setTag(TAG);
                                VolleySingleton.getVolleySingleton(CacheService.this).addToRequestQueue(request);
                            }else{
                                ContentValues values=new ContentValues();
                                values.put("zhihu_content",response);
                                database.update("Zhihu",values,"zhihu_id=?",new String[]{String.valueOf(id)});
                                values.clear();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {

                        }
                    });
                    request.setTag(TAG);
                    VolleySingleton.getVolleySingleton(CacheService.this).addToRequestQueue(request);
                }
            }while(cursor.moveToNext());
        }cursor.close();
    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            final Long id=intent.getLongExtra("id",0);
            switch (intent.getIntExtra("type",-1)){
                case TYPE_ZHIHU:
                {
                    new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startZAhihuCache(id);
                    }
                }).start();
                break;
                }
                default:
                case -1:
                    break;
            }
        }
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(receiver);
        VolleySingleton.getVolleySingleton(CacheService.this).getRequestQueue().cancelAll(TAG);
    }




}
