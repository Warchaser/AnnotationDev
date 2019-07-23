package com.warchaser.annotationdev.module.click;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ButterKnifeTest {

    private ButterKnifeTest(){

    }

    public static void inject(final Activity activity){
        /*
         * 通过字节码获取activity类中的所有字段
         * 在获取Filed的时候一定要使用getDeclaredFields()
         * 因为只有该方法才能获取到任何权限修饰的Filed,包括私有的
         */
        final Class activityClass = activity.getClass();
        final Field[] declaredFields = activityClass.getDeclaredFields();

        for(int i = 0; i < declaredFields.length; i++){
            final Field field = declaredFields[i];

            field.setAccessible(true);

            //获取到字段上面的注解对象
            ViewInject annotation = field.getAnnotation(ViewInject.class);

            if(annotation == null){
                continue;
            }

            final int id = annotation.value();

            View view = activity.findViewById(id);

            try {
                //将该控件设置给Field对象
                field.set(activity, view);
            } catch (Exception | Error e) {
                e.printStackTrace();
            }

        }

        //获取所有的方法
        final Method[] declaredMethods = activityClass.getDeclaredMethods();

        for(int i = 0; i < declaredMethods.length; i++){
            final Method method = declaredMethods[i];
            OnClick annotation = method.getAnnotation(OnClick.class);
            if(annotation == null){
                continue;
            }

            final int[] value = annotation.value();
            for(int j = 0; j < value.length; j++){
                final int id = value[j];
                final View button = activity.findViewById(id);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            method.invoke(activity, button);
                        } catch (Exception | Error e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }


    }
}
