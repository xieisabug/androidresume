package com.xjy.resume;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.xjy.resume.entity.Skill;
import com.xjy.resume.util.SkillsParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 用于滑动的activity
 */
public class PagerActivity extends Activity {

    Context context = this;
    private List<View> viewList;
    private List<String> titleList;
    public ListView honorsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        inflateView();
        viewPager.setAdapter(new MyPagerAdapter());
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < honorsListView.getChildCount(); i++) {
            View child = honorsListView.getChildAt(i);
            ScaleAnimation sa = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
            sa.setDuration(500);
            sa.setFillAfter(true);
            sa.setStartOffset(i*50);
            child.startAnimation(sa);
        }
    }

    //获取所有荣誉文本
    private List<String> honorsListData() {
        List<String> ret = new ArrayList<String>();
        String[] honors = getResources().getStringArray(R.array.honors);
        Collections.addAll(ret, honors);
        return ret;
    }

    public void animateIn(){

    }

    //反射布局
    private void inflateView() {
        View honors = getLayoutInflater().inflate(R.layout.honors, null);
        View skills = getLayoutInflater().inflate(R.layout.skills, null);
        // 给界面中的ListView设置数据
        if (honors != null) {
            honorsListView = (ListView) honors.findViewById(R.id.honors_list);
            honorsListView.setAdapter(new ArrayAdapter<String>(this, R.layout.honors_item, honorsListData()));
            honorsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,final int position, long id) {
                    int count = honorsListView.getChildCount();
                    for (int i = 0; i < count; i++) {
                        View child = honorsListView.getChildAt(i);
                        ScaleAnimation sa = new ScaleAnimation(1.0f, 0.0f, 1.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
                        sa.setDuration(500);
                        sa.setFillAfter(true);
                        sa.setStartOffset((count - i)*50);
                        if (child != null) {
                            child.setAnimation(sa);
                        }
                        child.startAnimation(sa);
                    }
                    honorsListView.getChildAt(0).getAnimation().setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            Intent toHonor = new Intent(context, HonorActivity.class);
                            toHonor.putExtra("id", position + 1);
                            startActivity(toHonor);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }
            });
        }

        if (skills != null) {
            LinearLayout ll = (LinearLayout) skills.findViewById(R.id.skills_layout);
            List<Map<String, Object>> skillList = SkillsParser.getSkillsList(context);
            for (Map<String, Object> skill : skillList) {
                Skill o = new Skill(context, Integer.parseInt((String) skill.get("id")), (String) skill.get("name"),
                        Integer.parseInt((String) skill.get("point")));
                ll.addView(o);
            }
        }

        //将所有界面加入viewList
        viewList = new ArrayList<View>();
        viewList.add(honors);
        viewList.add(skills);

        //所有标题加入titleList
        titleList = new ArrayList<String>();
        titleList.add("我的荣誉");
        titleList.add("我的技能");
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //return super.instantiateItem(container, position);
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
            container.removeView(viewList.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //return super.getPageTitle(position);
            return titleList.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }
    }

}
