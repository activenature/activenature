package com.active.chdating.net;

import android.webkit.WebView;

public class DetailContract {

    interface View extends BaseView<Presenter>{
        void showLoading();
        void stopLoading();
        void showLoadingError();
        void showSharingError();
        void showResult(String result,String string);
        void showResultWithoutBody(String url,String string);
        void showCover();
        void setTitle(String title);
        void setImageMode(boolean showImage);
        void showBrowserNotFoundError();
        void showTextCopied();
        void showCopyTextError();
        void showAddedToBookmarks();
        void showDeleteFromBookmarks();
    }

    interface Presenter extends BasePresenter{
        void openInBrowser();
        void shareAsText();
        void openUrl(WebView webView, String url);
        void copyText();
        void copyLink();
        void addToOrDeleteFromBookmarks();
        boolean queryIfIsBookmarked();
        void requestData();
    }
}
