package com.active.chdating.net;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebView;

import com.active.chdating.database.DatabaseHelper;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class DetailPresenter implements DetailContract.Presenter {

    private Context context;
    private DetailContract.View view;
    private Gson gson;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private BeanType type;
    private Long id;
    private String title;
    private String coverUrl;
    private ZhihuDailyStory zhihuDailyStory;

    public DetailPresenter(Context context, DetailContract.View view){
        this.context=context;
        this.view=view;
        this.databaseHelper=new DatabaseHelper(context,"History.db",null,1);
        this.database=databaseHelper.getWritableDatabase();
    }

    @Override
    public void openInBrowser() {

    }

    @Override
    public void shareAsText() {

    }

    @Override
    public void openUrl(WebView webView, String url) {

    }

    @Override
    public void copyText() {

    }

    @Override
    public void copyLink() {

    }

    @Override
    public void addToOrDeleteFromBookmarks() {

    }

    @Override
    public boolean queryIfIsBookmarked() {
        return false;
    }

    @Override
    public void requestData() {

        if(NetworkState.networkConnected(context)){
            StringModelImpl model = new StringModelImpl(context);
            model.load(Api.ZHIHU_NEWS + id, new OnStringListener() {
                @Override
                public void onSuccess(String result) {
                    gson=new Gson();
                    try{
                        zhihuDailyStory=gson.fromJson(result,ZhihuDailyStory.class);
                        String st=zhihuDailyStory.getImages().get(0);
                        if(zhihuDailyStory.getBody()==null){
                            view.showResultWithoutBody(zhihuDailyStory.getShareUrl(),st);
                        }else{
                            view.showResult(convertZhihuContent(zhihuDailyStory.getBody()),st);
                        }
                    }catch (JsonSyntaxException e){
                        view.showLoadingError();
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showLoadingError();

                }
            });
        }else{
            Cursor cursor=databaseHelper.getReadableDatabase().query("Zhihu",null,null,null,null,null,null);
            gson=new Gson();
            if(cursor.moveToFirst()){
                do{
                   if(cursor.getLong(cursor.getColumnIndex("zhihu_id"))==id) {
                       String content = cursor.getString(cursor.getColumnIndex("zhihu_content"));
                       String st = null;
                       try {
                           zhihuDailyStory = gson.fromJson(content, ZhihuDailyStory.class);
                           st = zhihuDailyStory.getImages().get(0);
                       } catch (JsonSyntaxException e) {
                           view.showResult(content, st);
                       }
                       view.showResult(convertZhihuContent(zhihuDailyStory.getBody()), st);
                   }
                }while(cursor.moveToNext());
            }
            cursor.close();
        }

    }

    @Override
    public void start() {

    }

    public void setType(BeanType type){
        this.type=type;
    }

    public void setId(Long id){
        this.id=id;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public void setCoverUrl(String coverUrl){
        this.coverUrl=coverUrl;
    }

    private String convertZhihuContent(String preResult){
        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // api中还有js的部分，这里不再解析js
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";

        String theme = "<body className=\"\" onload=\"onLoaded()\">";

        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }
}
