package com.warchaser.compiler.annotationprocessor.track;

import java.util.HashMap;
import java.util.Map;

public class TrackManager implements TrackInfoProvider{

    private Map<String, String> trackNameMap;

    private static TrackManager instance;

    public static TrackManager getInstance(){
        if(instance == null){
            instance = new TrackManager();
        }

        return instance;
    }

    private TrackManager(){
        trackNameMap = new HashMap<>();
        final String classFullName = "com.warchaser.compiler.annotationprocessor.track.TrackManager$Helper";
        try {
            final Class<?> clazz = Class.forName(classFullName);
            IData data = (IData)clazz.newInstance();
            data.loadInto(trackNameMap);
        } catch (Exception | Error e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTrackNameByClass(String className) {
        String output = className;
        if(trackNameMap != null && !trackNameMap.isEmpty()){
            final String value = trackNameMap.get(className);
            output = (value == null ? output : value);
        }
        return output;
    }

    @Override
    public String getAllPath() {
        if(trackNameMap.isEmpty()){
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : trackNameMap.entrySet()){
            stringBuilder.append("页面: ").append(entry.getKey());
            stringBuilder.append("\t");
            stringBuilder.append("路径: ").append(entry.getValue());
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
