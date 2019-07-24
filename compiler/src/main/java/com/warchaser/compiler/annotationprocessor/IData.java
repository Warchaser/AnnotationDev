package com.warchaser.compiler.annotationprocessor;

import java.util.Map;

public interface IData {

    /**
     * 载入数据
     * */
    void loadInto(Map<String, String> map);

}
