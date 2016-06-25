package com.example.administrator.chabaike.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.chabaike.R;
import com.example.administrator.chabaike.utils.ConstantKey;
import com.example.administrator.chabaike.utils.Pref_Utils;


public class WelcomeActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout container_ll;
    private int[] imageResource = {R.drawable.slide1, R.drawable.slide2,R.drawable.slide3};
    private MyAdapter adapter;
    private int pre_index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.welcome_vp);
        initView();
        adapter = new MyAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(adapter);
    }

    private ImageView[] imageViews = new ImageView[imageResource.length];

    private void initView() {
        container_ll = (LinearLayout) findViewById(R.id.welcome_ll);
        LinearLayout.LayoutParams lv = new LinearLayout.LayoutParams(10, 10);
        lv.leftMargin=10;
        ViewGroup.LayoutParams lvp=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                ,ViewGroup.LayoutParams.MATCH_PARENT);
        ImageView iv=null;
        View view=null;
        for(int i=0;i<imageResource.length;i++){
            iv=new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setLayoutParams(lvp);
            imageViews[i]=iv;
            imageViews[i].setImageResource(imageResource[i]);

            if (i==imageResource.length-1){
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.setClass(WelcomeActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            view =new View(this);
            if (i==0){
                view.setBackgroundResource(R.drawable.page_now);
            }else{
                view.setBackgroundResource(R.drawable.page);
            }
            view.setLayoutParams(lv);
            container_ll.addView(view);
        }
    }


    class MyAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        @Override
        public int getCount() {
            return imageResource.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews[position]);
            return imageViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            container_ll.getChildAt(position).setBackgroundResource(R.drawable.page_now);
            container_ll.getChildAt(pre_index).setBackgroundResource(R.drawable.page);
            pre_index = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Pref_Utils.putBoolean(this, ConstantKey.PRE_KEY_FIRST_OPEN,false);

    }
}