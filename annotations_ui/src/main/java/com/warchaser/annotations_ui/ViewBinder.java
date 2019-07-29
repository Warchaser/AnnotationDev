package com.warchaser.annotations_ui;

import com.warchaser.annotations_ui.bindclick.ClickBinder;
import com.warchaser.annotations_ui.viewid.Finder;
import com.warchaser.annotations_ui.viewid.IViewBinder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class ViewBinder {

    private static Finder mFinder = new Finder();

    static Map<String, IViewBinder> mCache = new TreeMap<>();
    private static final Map<String, ClickBinder> clickBinderMap = new LinkedHashMap<>();

    public static void bind(Object target){
        bind(target, target);
        bindClick(target, target);
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

    private static void bindClick(Object host, Object object) {
        String className = host.getClass().getName();
        try {
            ClickBinder binder = clickBinderMap.get(className);
            if (binder == null) {
                Class<?> aClass = Class.forName(className + "$$ClickBinder");
                binder = (ClickBinder) aClass.newInstance();

                clickBinderMap.put(className, binder);
            }
            if (binder != null) {
                binder.bindClick(host, object);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
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
