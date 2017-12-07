package com.yztc.gankio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.yztc.gankio.ui.classify.ClassifyFragment;
import com.yztc.gankio.ui.discover.DiscoverFragment;
import com.yztc.gankio.ui.mine.MineFragment;
import com.yztc.gankio.ui.recommend.RecommendFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String SELINDEX = "INDEX";
    @BindView(R.id.main_indicate)
    BottomNavigationView mainIndicate;

    private Fragment recommend, cls, discover, mine;
    private FragmentManager fragmentMgr;

    private String[] tags = {"RECOMMEND", "CLASSIFY", "DISCOVER", "MINE"};

    private int selindex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        fragmentMgr = getSupportFragmentManager();

        mainIndicate.setOnNavigationItemSelectedListener(new SelectedListener());

        if (savedInstanceState != null) {
            //读取上一次界面Save的时候tab选中的状态
            selindex = savedInstanceState.getInt(SELINDEX, selindex);
            recommend = fragmentMgr.findFragmentByTag(tags[0]);
            cls = fragmentMgr.findFragmentByTag(tags[1]);
            discover = fragmentMgr.findFragmentByTag(tags[2]);
            mine = fragmentMgr.findFragmentByTag(tags[3]);
            restoreSelect();
        } else {
            mainIndicate.findViewById(R.id.menu_recommend).performClick();
        }

    }


    private void restoreSelect() {
        // 选中index
        View view = null;
        switch (selindex) {
            case 0:
                view = mainIndicate.findViewById(R.id.menu_recommend);
                break;
            case 1:
                view = mainIndicate.findViewById(R.id.menu_show);
                break;
            case 2:
                view = mainIndicate.findViewById(R.id.menu_discover);
                break;
            case 3:
                view = mainIndicate.findViewById(R.id.menu_mine);
                break;
            default:
                break;
        }
        view.performClick();
    }


    class SelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction ft = fragmentMgr.beginTransaction();

            switch (item.getItemId()) {
                case R.id.menu_recommend:
                    selindex = 0;
                    hint(ft, 1, 2, 3);
                    if (recommend == null) {
                        recommend = new RecommendFragment();
                        ft.add(R.id.main_container, recommend, tags[0]);
                    } else {
                        ft.show(recommend);
                    }
                    break;
                case R.id.menu_show:
                    selindex = 1;
                    hint(ft, 0, 2, 3);
                    if (cls == null) {
                        cls = new ClassifyFragment();
                        ft.add(R.id.main_container, cls, tags[1]);
                    } else {
                        ft.show(cls);
                    }
                    break;
                case R.id.menu_discover:
                    selindex = 2;
                    hint(ft, 0, 1, 3);
                    if (discover == null) {
                        discover = new DiscoverFragment();
                        ft.add(R.id.main_container, discover, tags[2]);
                    } else {
                        ft.show(discover);
                    }
                    break;
                case R.id.menu_mine:
                    selindex = 3;
                    hint(ft, 0, 1, 2);
                    if (mine == null) {
                        mine = new MineFragment();
                        ft.add(R.id.main_container, mine, tags[3]);
                    } else {
                        ft.show(mine);
                    }
                    break;
            }

            ft.commit();

            return true;
        }
    }

    private void hint(FragmentTransaction ft, Integer... fs) {
        for (int j = 0; j < fs.length; j++) {
            switch (fs[j]) {
                case 0:
                    if (recommend != null)
                        ft.hide(recommend);
                    break;
                case 1:
                    if (cls != null)
                        ft.hide(cls);
                    break;
                case 2:
                    if (discover != null)
                        ft.hide(discover);
                    break;
                case 3:
                    if (mine != null)
                        ft.hide(mine);
                    break;
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELINDEX, selindex);
    }


    long curr;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - curr > 2000) {
            curr = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
            System.exit(0);
        }
    }

}
