package com.example.administrator.chabaike.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;


import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.beans.TabInfo;
import com.example.administrator.chabaike.fragment.ContentFragment;

public class HomeActivity extends FragmentActivity {

    private TabLayout mtabs;
    private ViewPager viewPager;
    private MyFragmentAdapter adapter;

    private  TabInfo[] tabs=new TabInfo[] {
            new TabInfo("社会热点",6),
            new TabInfo("企业要闻",1),
            new TabInfo("医疗新闻",2),
            new TabInfo("生活贴士",3),
            new TabInfo("药品新闻",4),
            new TabInfo("疾病快讯",7),
            new TabInfo("食品新闻",5)
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
    }

    private void initView() {
        mtabs= (TabLayout) findViewById(R.id.home_top);
        viewPager= (ViewPager) findViewById(R.id.home_vp);
        adapter=new MyFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        mtabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mtabs.setupWithViewPager(viewPager);

    }
    /**
     * activity 非正常销毁调用此方法 写入的数据在onCreate方法中的 saveState中
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    class  MyFragmentAdapter extends FragmentStatePagerAdapter{

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ContentFragment cf=new ContentFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("id",tabs[position].class_id);
            cf.setArguments(bundle);
            return cf;
        }

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position].name;
        }
    }
}