package com.active.chdating.homepage;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.ViewGroup;

import com.active.chdating.R;

public class MySocialToolSocialAccountActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_mysocialtool_socialaccount_main);
        addPreferencesFromResource(R.xml.homepage_mysocialtool_socialaccount);


    }
}
