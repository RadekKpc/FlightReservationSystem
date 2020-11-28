package com.wesolemarcheweczki.backend.generator;

import com.google.common.base.Splitter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("PropertySplitter")
public class PropertySplitter {

    /**
     * Example: one.example.property = KEY1:VALUE1,KEY2:VALUE2
     */
    public Map<String, String> map(String property) {
        return this.map(property, ",");
    }

    /**
     * Example: one.example.property = KEY1:VALUE1.1,VALUE1.2;KEY2:VALUE2.1,VALUE2.2
     */
    public Map<String, List<String>> mapOfList(String property) {
        Map<String, String> map = this.map(property, ";");

        Map<String, List<String>> mapOfList = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            mapOfList.put(entry.getKey(), this.list(entry.getValue()));
        }

        return mapOfList;
    }

    /**
     * Example: one.example.property = VALUE1,VALUE2,VALUE3,VALUE4
     */
    public List<String> list(String property) {
        return this.list(property, ",");
    }

    /**
     * Example: one.example.property = VALUE1.1,VALUE1.2;VALUE2.1,VALUE2.2
     */
    public List<List<String>> groupedList(String property) {
        List<String> unGroupedList = this.list(property, ";");

        List<List<String>> groupedList = new ArrayList<>();
        for (String group : unGroupedList) {
            groupedList.add(this.list(group));
        }

        return groupedList;

    }

    private List<String> list(String property, String splitter) {
        return Splitter.on(splitter).omitEmptyStrings().trimResults().splitToList(property);
    }

    private Map<String, String> map(String property, String splitter) {
        return Splitter.on(splitter).omitEmptyStrings().trimResults().withKeyValueSeparator(":").split(property);
    }

}