package com.jdry.pms.mainFrame.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.jdry.pms.mainFrame.pojo.CalEvent;
 
@Component
public class SampleDataProvider {
 
    @DataProvider
    public Collection<CalEvent> getEvents(Map<String, Object> params) {
        Date startTime = null, endTime = null;
        if (params != null) {
            startTime = (Date)params.get("startTime");
            endTime = (Date)params.get("endTime");
        } else {
            return null;
        }
 
        List<CalEvent> list = new ArrayList<CalEvent>();
        CalEvent event = null;
 
        Random random = new Random();
 
        for (int i = 0, j = 42; i < j; i++) {
            event = new CalEvent();
            event.setTitle("event " + i);
 
            Date eventStartTime = new Date(startTime.getTime() + 86400000 * random.nextInt(42) + random.nextInt(24) * 3600000);
            Date eventEndTime = new Date(eventStartTime.getTime() +  360000 * random.nextInt(50));
 
            event.setStartTime(eventStartTime);
            event.setEndTime(eventEndTime);
            list.add(event);
        }
 
        return list;
    }
 
    @DataResolver
    public String saveEvent(CalEvent event) {
        return "事件:" + event.getTitle() + "已被成功保存!";
    }
}
