package com.bedates.guncelhaberler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


public class koseyazarlarilistesi extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.koseyazarlarilistesikopya);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.koseyazarlarilistesi, menu);
        return true;
    }

    
}
