package diy.net.menzap.activity;

/**
 * Created by swathissunder on 15/09/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import diy.net.menzap.R;
import diy.net.menzap.helper.DataHolder;
import diy.net.menzap.helper.UserDBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataHolder.getInstance().initHelper(this);

        UserDBHelper userDBHelper = new UserDBHelper(this);
        boolean isRegistered = userDBHelper.isRegistered();

        // if registered then Tabs otherwise login screen
        if(isRegistered)
            startActivity(new Intent(MainActivity.this, TabsActivity.class));
        else
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        DataHolder.getInstance().destroyHelper();
    }
}
