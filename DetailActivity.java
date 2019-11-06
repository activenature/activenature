package com.active.chdating.net;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.active.chdating.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DetailActivity extends AppCompatActivity implements DetailContract.View {

    CollapsingToolbarLayout toolbarLayout;
    WebView webView;
    ImageView imageView;
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.universal_read_layout);

        toolbarLayout=findViewById(R.id.toolbar_layout);
        webView=findViewById(R.id.web_view);
        imageView=findViewById(R.id.image_view);
        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent=getIntent();
        DetailPresenter presenter=new DetailPresenter(DetailActivity.this,DetailActivity.this);
        presenter.setType((BeanType)intent.getSerializableExtra("type"));
        presenter.setId(intent.getLongExtra("id",0));
        presenter.setTitle(intent.getStringExtra("title"));
        presenter.setCoverUrl(intent.getStringExtra("coverUrl"));
        presenter.requestData();

        setCollapsingToolbarLayoutTitle(intent.getStringExtra("title"));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void showSharingError() {

    }

    @Override
    public void showResult(String result,String st) {

        webView.loadDataWithBaseURL("x-data://base",result,"text/html","utf-8",null);
        Glide.with(DetailActivity.this).load(st).asBitmap().placeholder(R.drawable.smallspring).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.smallspring).centerCrop().into(imageView);


    }

    @Override
    public void showResultWithoutBody(String url,String st) {
        webView.loadUrl(url);
        Glide.with(DetailActivity.this).load(st).asBitmap().placeholder(R.drawable.smallspring).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.smallspring).centerCrop().into(imageView);

    }

    @Override
    public void showCover() {

    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setImageMode(boolean showImage) {

    }

    @Override
    public void showBrowserNotFoundError() {

    }

    @Override
    public void showTextCopied() {

    }

    @Override
    public void showCopyTextError() {

    }

    @Override
    public void showAddedToBookmarks() {

    }

    @Override
    public void showDeleteFromBookmarks() {

    }

    @Override
    public void setPresenter(DetailContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {

    }

    private void setCollapsingToolbarLayoutTitle(String title){
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleTextAppearance((R.style.TextAppearance_Design_CollapsingToolbar_Expanded));
        toolbarLayout.setCollapsedTitleTextAppearance(R.style.Widget_Design_CollapsingToolbar);

    }
}
