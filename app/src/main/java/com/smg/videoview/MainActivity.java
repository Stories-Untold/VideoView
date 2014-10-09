package com.smg.videoview;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private LinearLayout point_group;
    private TextView imageDesc;
    private ImageView home;
    private ImageView menu;
    private RelativeLayout level1;
    private RelativeLayout level2;
    private RelativeLayout level3;
    private boolean isLevel3Show = true;
    private boolean isLevel2Show = true;
    private boolean isLevel1Show = true;
    private ViewPager paper_top;
    private int lastPosition = 0;
    private int[] imageIds = {
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
    };
    private String[] titles = {
            "标题01",
            "标题02",
            "标题03",
            "标题04",
            "标题05"
    };
    private ArrayList<ImageView> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        //初始化数据
        images = new ArrayList<ImageView>();
        for (int i = 0; i < imageIds.length; i++) {
            ImageView image = new ImageView(this);
            image.setImageResource(imageIds[i]);
            images.add(image);
            ImageView point = new ImageView(this);
            if (i == 0) {
                point.setEnabled(true);
                imageDesc.setText(titles[0]);
            } else {
                point.setEnabled(false);
            }
            point.setImageResource(R.drawable.point_select);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 10);
            point.setLayoutParams(params);
            point_group.addView(point);
        }
        initPaper();
        paper_top.setOnPageChangeListener(this);
        isRunning = true;
        mHandler.sendEmptyMessageDelayed(0, 2000);
    }

    private Boolean isRunning = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            paper_top.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            isRunning = false;
                            return true;
                        case MotionEvent.ACTION_UP:
                            isRunning = true;
                            mHandler.sendEmptyMessageDelayed(0, 2000);
                            return true;
                    }
                    return false;
                }
            });
            if (isRunning) {
                paper_top.setCurrentItem(paper_top.getCurrentItem() + 1);
                mHandler.sendEmptyMessageDelayed(0, 2000);
            }
        }
    };

    @Override
    protected void onDestroy() {
        isRunning = false;
    }

    private void initPaper() {
        MyPaperAdapter adapter = new MyPaperAdapter();
        paper_top.setAdapter(adapter);
        paper_top.setCurrentItem(imageIds.length * 100000);
    }

    private void init() {
        home = (ImageView) findViewById(R.id.home);
        menu = (ImageView) findViewById(R.id.menu);
        level1 = (RelativeLayout) findViewById(R.id.level1);
        level2 = (RelativeLayout) findViewById(R.id.level2);
        level3 = (RelativeLayout) findViewById(R.id.level3);
        paper_top = (ViewPager) findViewById(R.id.paper_top);
        point_group = (LinearLayout) findViewById(R.id.point_group);
        imageDesc = (TextView) findViewById(R.id.imageDesc);
        home.setOnClickListener(this);
        menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home:
                if (isLevel2Show) {
                    MyUtils.startAnimOut(level2, 1000);
                } else {
                    MyUtils.startAnimIn(level2, 1000);
                }
                if (isLevel3Show) {
                    MyUtils.startAnimOut(level3, 500);
                    isLevel3Show = false;
                }
                isLevel2Show = !isLevel2Show;
                break;
            case R.id.menu:
                if (isLevel3Show) {
                    MyUtils.startAnimOut(level3, 500);
                } else {
                    MyUtils.startAnimIn(level3, 500);
                }
                isLevel3Show = !isLevel3Show;
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (isLevel1Show) {
                MyUtils.startAnimOut(level1, 1500);
            } else {
                MyUtils.startAnimIn(level1, 1500);
            }
            if (isLevel3Show) {
                MyUtils.startAnimOut(level3, 500);
                isLevel3Show = false;
            }
            if (isLevel2Show) {
                MyUtils.startAnimOut(level2, 1000);
                isLevel2Show = false;
            }
            isLevel1Show = !isLevel1Show;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        i = i % images.size();
        imageDesc.setText(titles[i]);
        point_group.getChildAt(i).setEnabled(true);
        point_group.getChildAt(lastPosition).setEnabled(false);
        lastPosition = i;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private class MyPaperAdapter extends PagerAdapter {


        /**
         * 实例化相应的一个Item
         *
         * @param container view的容器
         * @param position  相应的位置
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(images.get(position % images.size()));
            return images.get(position % images.size());
        }

        /**
         * 销毁对应位置上的Obj
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }
}
