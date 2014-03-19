package com.xjy.resume;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.xjy.resume.entity.Honor;
import com.xjy.resume.util.HonorsParser;

import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class HonorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honor);
        //通过Intent来获取到点击的是第几个，并且从xml文件中读取出相对应的对象
        Intent i = getIntent();
        Honor honor = getHonor(i.getIntExtra("id", 0));

        //将读取到的对象显示到activity中
        TextView name = (TextView) findViewById(R.id.honor_name);
        TextView time = (TextView) findViewById(R.id.honor_time);
        TextView introduce = (TextView) findViewById(R.id.honor_introduce);
        name.setText(honor.getName());
        time.setText(honor.getTime());
        introduce.setText(honor.getIntroduce());
    }

    private Honor getHonor(int id){
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            SAXParser sp = spf.newSAXParser();
            HonorsParser hp = new HonorsParser();
            hp.setId(id);
            sp.parse(getResources().openRawResource(R.raw.honors),hp);
            return hp.getHonor();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
