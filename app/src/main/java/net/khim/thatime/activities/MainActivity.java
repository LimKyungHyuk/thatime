package net.khim.thatime.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import net.khim.thatime.R;
import net.khim.thatime.fragments.FragmentHome;
import net.khim.thatime.fragments.FragmentList;
import net.khim.thatime.fragments.FragmentMap;
import net.khim.thatime.fragments.FragmentRgst;
import net.khim.thatime.fragments.FragmentSetting;
import net.khlim.lib.BackPressCloseHandler;

public class MainActivity extends AppCompatActivity {

    FragmentHome fragmentHome;
    FragmentMap fragmentMap;
    FragmentList fragmentList;
    FragmentRgst fragmentRgst;
    FragmentSetting fragmentSetting;

    BottomNavigationView navigation;

    private BackPressCloseHandler backPressCloseHandler;


    public interface OnActivityResult {
        void onActivityResultForFragment(int requestCode, int resultCode, Intent data);
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.btn_bottom_home:

                    //fragmentHome.setArguments(new Bundle());
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentHome).commit();

                    return true;
                case R.id.btn_bottom_map:

                    getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentMap).commit();
                    return true;

                case R.id.btn_bottom_register:

                    getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentRgst).commit();
                    return true;

                case R.id.btn_bottom_list:

                    getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentList).commit();
                    return true;

                case R.id.btn_bottom_setting:

                    getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentSetting).commit();
                    return true;
            }

            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentHome = new FragmentHome();
        fragmentMap = new FragmentMap();
        fragmentList = new FragmentList();
        fragmentRgst = new FragmentRgst();
        fragmentSetting = new FragmentSetting();

        // 초기화면
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragmentHome).commit();

        // 네비게이션
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // 백버튼 해들러
        backPressCloseHandler = new BackPressCloseHandler(this);
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        fragmentRgst.onActivityResultForFragment(requestCode, resultCode, data);
    }
}
