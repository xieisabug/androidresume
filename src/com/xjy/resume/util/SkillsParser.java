package com.xjy.resume.util;

import android.content.Context;
import android.util.Log;

import com.xjy.resume.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillsParser {
    public static Map<String, Object> getSkillById(int id, Context context) {
        Map<String, Object> skill = new HashMap<String, Object>();
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(context.getResources().openRawResource(R.raw.skills), "UTF-8");
            int eventType = xmlPullParser.getEventType();
            boolean read = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("skill")) {
                            //Log.i("SkillActivity", xmlPullParser.getAttributeValue(0));
                            if (Integer.parseInt(xmlPullParser.getAttributeValue(0)) == id) {
                                read = true;
                            }
                        } else if (xmlPullParser.getName().equals("name")) {
                            skill.put("name", xmlPullParser.nextText());
                        } else if (xmlPullParser.getName().equals("point")) {
                            skill.put("point",xmlPullParser.nextText());
                        } else if (xmlPullParser.getName().equals("introduce")) {
                            skill.put("introduce", xmlPullParser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equals("skill") && read) {
                            return skill;
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static List<Map<String,Object>> getSkillsList(Context context) {
        List<Map<String,Object>> skills = new ArrayList<Map<String, Object>>();
        Map<String, Object> skill = null;
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(context.getResources().openRawResource(R.raw.skills), "UTF-8");
            int eventType = xmlPullParser.getEventType();
            boolean read = false;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (xmlPullParser.getName().equals("skill")) {
                            skill = new HashMap<String, Object>();
                            skill.put("id", xmlPullParser.getAttributeValue(0));
                            //Log.i("SkillActivity", xmlPullParser.getAttributeValue(0));
                        } else if (xmlPullParser.getName().equals("name")) {
                            if (skill != null) {
                                skill.put("name", xmlPullParser.nextText());
                            }
                        } else if (xmlPullParser.getName().equals("point")) {
                            if (skill != null) {
                                skill.put("point",xmlPullParser.nextText());
                            }
                        } else if (xmlPullParser.getName().equals("introduce")) {
                            if (skill != null) {
                                skill.put("introduce", xmlPullParser.nextText());
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equals("skill")) {
                            skills.add(skill);
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return skills;
    }
    
}
