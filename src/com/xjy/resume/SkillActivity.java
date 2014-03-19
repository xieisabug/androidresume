package com.xjy.resume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.xjy.resume.util.SkillsParser;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.Map;

public class SkillActivity extends Activity {
    private TextView name;
    private TextView point;
    private TextView introduce;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);
        //通过Intent来获取到点击的是第几个，并且从xml文件中读取出相对应的对象
        Intent i = getIntent();
        this.id = i.getIntExtra("id", 0);

        //将读取到的对象显示到activity中
        name = (TextView) findViewById(R.id.skill_name);
        point = (TextView) findViewById(R.id.skill_point);
        introduce = (TextView) findViewById(R.id.skill_introduce);
        init();
    }

    private void init(){
        Map<String,Object> skill = SkillsParser.getSkillById(this.id, this);
        name.setText((String) skill.get("name"));
        point.setText((String)skill.get("point"));
        introduce.setText((String)skill.get("introduce"));
    }
}
