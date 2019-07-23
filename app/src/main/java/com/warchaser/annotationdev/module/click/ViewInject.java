package com.warchaser.annotationdev.module.click;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Retention 用于声明该注解生效的生命周期，有三个枚举值可以选择<br>
 * 1.RetentionPolicy.SOURCE,注释只保留在源码上面,编译成class的时候自动被编译器抹除<br>
 * 2.RetentionPolicy.CLASS,注释只保留到字节码class上面,VM加载class时自动抹除<br>
 * 3.RetentionPolicy.RUNTIME,注释永久保留,可以被VM加载时加载到内存中<br>
 * 注意,由于我们的目的是想在VM运行时对Filed上的该注解进行反射操作,因此RetentionPolicy的值为RUNTIME<br>
 *
 * Target 用于制定该注解可以声明在哪些成员上面,常见的值有FIELD和METHOD<br>
 * 由于我们当前的注解类是想声明在Field上,因此Target的值为ElementType.FIELD<br>
 * 注意,如果Target默认为，可以添加到任何元素上面<br>
 * */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {
    public abstract int value();
}
