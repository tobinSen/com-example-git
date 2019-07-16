package com.uplooking.xml;

import org.apache.commons.lang3.time.DateFormatUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;

public class DateXmlAdapter extends XmlAdapter<String, Date> {


    @Override
    public Date unmarshal(String v) throws Exception {
        return DateFormatUtils.ISO_DATETIME_FORMAT.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        return DateFormatUtils.ISO_DATETIME_FORMAT.format(v);

    }
}
