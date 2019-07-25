package com.warchaser.annotations_ui;

import com.warchaser.annotations_ui.viewid.Finder;
import com.warchaser.annotations_ui.viewid.IViewBinder;

import java.util.Map;
import java.util.TreeMap;

public class ViewBinder {

    private static Finder mFinder = new Finder();

    static Map<String, IViewBinder> mCache = new TreeMap<>();

    public static void bind(Object target){
        bind(target, target);
    }

    public static void bind(Object target, Object object){
        bind(target, object, mFinder);
    }

    private static void bind(Object target, Object object, Finder finder){
        //获取注解所在的类
        final String annotatedClassName = target.getClass().getName();

        //获取生成的类
        //先从缓存中获取
        IViewBinder binder = mCache.get(annotatedClassName);
        if(binder == null){
            try {
                final Class<?> clazz = Class.forName(annotatedClassName + "$$IViewBinder");
                binder = (IViewBinder) clazz.newInstance();
                mCache.put(annotatedClassName, binder);
            } catch (Exception | Error e){
                e.printStackTrace();
            }
        }
        if(binder != null){
            binder.bindView(target, object, finder);
        }
    }

    public static void unBind(Object host){
        final String className = host.getClass().getName();
        IViewBinder binder = mCache.get(className);
        if(binder != null){
            binder.unbind(host);
        }

        mCache.remove(className);
    }

}
