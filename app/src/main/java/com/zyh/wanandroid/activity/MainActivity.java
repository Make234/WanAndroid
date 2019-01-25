package com.zyh.wanandroid.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zyh.wanandroid.MyApplication;
import com.zyh.wanandroid.R;
import com.zyh.wanandroid.base.BaseActivity;
import com.zyh.wanandroid.databinding.ActivityMainBinding;
import com.zyh.wanandroid.fragment.DrawerFragment;
import com.zyh.wanandroid.fragment.HomeFragment;
import com.zyh.wanandroid.fragment.KnowledgeFragment;
import com.zyh.wanandroid.fragment.NavigationFragment;
import com.zyh.wanandroid.fragment.ProjectFragment;
import com.zyh.wanandroid.utils.BaseOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zyh
 */
public class MainActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    ActivityMainBinding mBinding;
    List<Fragment> mFragmentList;
    private long exitTime;
    private static final int DOUBLE_CLICK = 2000;
    private FragmentManager supportFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initTitle();
        initFragment();
        initView();
    }

    private void initView() {
        mBinding.bnv.setOnNavigationItemSelectedListener(this);
        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.vp, mFragmentList.get(0));
        fragmentTransaction.add(R.id.vp, mFragmentList.get(1));
        fragmentTransaction.add(R.id.vp, mFragmentList.get(2));
        fragmentTransaction.add(R.id.vp, mFragmentList.get(3));
        fragmentTransaction.add(R.id.ll_drawer, new DrawerFragment());
        fragmentTransaction.commitAllowingStateLoss();
        showFragment(0);
    }

    private void initTitle() {
        mBinding.toolbar.getLeftImageView().setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu));
        mBinding.toolbar.getLeftImageView().setOnClickListener(new BaseOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                int leftDrawer = Gravity.START;
                if (mBinding.drawerLayout.isDrawerOpen(leftDrawer)) {
                    mBinding.drawerLayout.closeDrawer(leftDrawer);
                } else {
                    mBinding.drawerLayout.openDrawer(leftDrawer);
                }
            }
        });
        mBinding.toolbar.getCenterTextView().setText(getString(R.string.title_home));
        mBinding.toolbar.getRightImageView().setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search));
        mBinding.toolbar.getRightImageView().setOnClickListener(new BaseOnClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        KnowledgeFragment knowledgeFragment = new KnowledgeFragment();
        NavigationFragment navigationFragment = new NavigationFragment();
        ProjectFragment projectFragment = new ProjectFragment();
        mFragmentList.add(homeFragment);
        mFragmentList.add(knowledgeFragment);
        mFragmentList.add(navigationFragment);
        mFragmentList.add(projectFragment);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (mBinding == null) {
            return false;
        }
        TextView centerTextView = mBinding.toolbar.getCenterTextView();
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                showFragment(0);
                centerTextView.setText(getString(R.string.title_home));
                return true;
            case R.id.navigation_knowledge:
                showFragment(1);
                centerTextView.setText(getString(R.string.knowledge));
                return true;
            case R.id.navigation_navigation:
                showFragment(2);
                centerTextView.setText(getString(R.string.navigation));
                return true;
            case R.id.navigation_project:
                showFragment(3);
                centerTextView.setText(getString(R.string.project));
                return true;
            default:
                break;
        }
        return false;
    }

    private void showFragment(int position) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.show(mFragmentList.get(position));
        for (int i = 0; i < mFragmentList.size(); i++) {
            if (position == i) {
                fragmentTransaction.show(mFragmentList.get(position));
            } else {
                fragmentTransaction.hide(mFragmentList.get(i));
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > DOUBLE_CLICK) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                //关闭在线状态上报服务
                MyApplication.getInstance().cleanActivity();
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
