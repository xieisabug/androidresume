package com.xjy.resume.util;

import com.xjy.resume.entity.Honor;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HonorsParser extends DefaultHandler {

    private Honor currentHonor;
    private String tagName;
    private boolean is;
    private int id;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (localName.equals("honor")) {
            if (Integer.parseInt(attributes.getValue(0)) == id) {
                currentHonor = new Honor();
                currentHonor.setId(id);
                is = true;
            }
        }
        this.tagName = localName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        this.tagName = "";
        if(localName.equals("honor") && is) {
            is = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (is) {
            String data = new String(ch, start, length);
            if (this.tagName.equals("name")) {
                currentHonor.setName(data);
            } else if (this.tagName.equals("time")) {
                currentHonor.setTime(data);
            } else if (this.tagName.equals("introduce")) {
                currentHonor.setIntroduce(data);
            }
        }
    }

    public Honor getHonor() {
        return currentHonor;
    }

    public void setId(int id) {
        this.id = id;
    }
}
