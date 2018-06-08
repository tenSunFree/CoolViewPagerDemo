package com.example.administrator.coolviewpagerdemo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.TextViewCompat;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.huanhailiuxin.coolviewpager.CoolViewPager;

import java.util.ArrayList;
import java.util.List;

public class ActivityPageTransformer extends BaseActivity {

    private int newPosition, imgPosition, imgPosition2;
    private TextView famousPaintingsNameTextView;
    private CoolViewPager vp;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_page_transformer);
        getSupportActionBar().hide();
        initViews();
    }

    private void initViews() {

        /** 讓文字自動放大縮小 */
        famousPaintingsNameTextView = findViewById(R.id.famousPaintingsNameTextView);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(famousPaintingsNameTextView, TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(famousPaintingsNameTextView, 5, 100, 1, TypedValue.COMPLEX_UNIT_SP);

        /** 套用ttf格式的字体 */
        String fonts = "fonts/font.ttf";
        Typeface typeface = Typeface.createFromAsset(getAssets(), fonts);
        famousPaintingsNameTextView.setTypeface(typeface);
        famousPaintingsNameTextView.setTypeface(typeface);

        /** 初始化CoolViewPager 相關數據設定 */
        vp = findViewById(R.id.vp);
        initData();
        vp.setPageTransformer(false, new com.huanhailiuxin.coolviewpager.transformer.VerticalRotateTransformer());
        vp.setOnPageChangeListener(new CoolViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position <= 6) {
                    switch (position) {
                        case 0:
                            famousPaintingsNameTextView.setText("拿破侖穿越阿爾卑斯山");
                            break;
                        case 1:
                            famousPaintingsNameTextView.setText("盲女");
                            break;
                        case 2:
                            famousPaintingsNameTextView.setText("向日葵");
                            break;
                        case 3:
                            famousPaintingsNameTextView.setText("星空");
                            break;
                        case 4:
                            famousPaintingsNameTextView.setText("維蘇威火山爆發");
                            break;
                        case 5:
                            famousPaintingsNameTextView.setText("最後的審判");
                            break;
                        case 6:
                            famousPaintingsNameTextView.setText("女占卜師");
                            break;
                    }
                } else {
                    newPosition = (position + 1) % 7;
                    switch (newPosition) {
                        case 0:
                            famousPaintingsNameTextView.setText("女占卜師");
                            break;
                        case 1:
                            famousPaintingsNameTextView.setText("拿破侖穿越阿爾卑斯山");
                            break;
                        case 2:
                            famousPaintingsNameTextView.setText("盲女");
                            break;
                        case 3:
                            famousPaintingsNameTextView.setText("向日葵");
                            break;
                        case 4:
                            famousPaintingsNameTextView.setText("星空");
                            break;
                        case 5:
                            famousPaintingsNameTextView.setText("維蘇威火山爆發");
                            break;
                        case 6:
                            famousPaintingsNameTextView.setText("最後的審判");
                            break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        List<View> items = new ArrayList<>();
        items.add(createImageView(R.drawable.famous_paintings));
        items.add(createImageView(R.drawable.famous_paintings2));
        items.add(createImageView(R.drawable.famous_paintings3));
        items.add(createImageView(R.drawable.famous_paintings4));
        items.add(createImageView(R.drawable.famous_paintings5));
        items.add(createImageView(R.drawable.famous_paintings6));
        items.add(createImageView(R.drawable.famous_paintings7));
        adapter = new MyAdapter(items);
        vp.setAdapter(adapter);
    }

    class MyAdapter extends PagerAdapter {
        private List<View> views;

        public MyAdapter(List<View> items) {
            this.views = items;
        }

        @Override
        public int getCount() {
            return views.size() * 1000;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        /**
         * 必须要先判断, 否则报错: java.lang.IllegalStateException: The specified child already has a parent
         * ViewGroup的addView() 方法不能添加一个已存在父控件的视图, 所以在执行前需要判断要添加的View实例是不是存在父控件
         * 如果存在父控件, 需要其父控件先将该View移除掉, 再执行addView
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {

            /** 初始化每個Item的圖片 */
            imgPosition = (position + 1) % views.size();
            imgPosition2 = 0;
            if (imgPosition != 0) {
                if (views.get(imgPosition - 1).getParent() != null) {
                    ((ViewGroup) views.get(imgPosition - 1).getParent()).removeView(views.get(imgPosition - 1));
                }
                container.addView(views.get(imgPosition - 1));
                imgPosition2 = imgPosition - 1;
            } else if (imgPosition == 0) {
                if (views.get(views.size() - 1).getParent() != null) {
                    ((ViewGroup) views.get(views.size() - 1).getParent()).removeView(views.get(views.size() - 1));
                }
                container.addView(views.get(views.size() - 1));
                imgPosition2 = views.size() - 1;
            }
            return views.get(imgPosition2);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
