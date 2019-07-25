package com.warchaser.compiler.annotationprocessor.bindclick;


import com.warchaser.annotations.bindclick.BindClick;
import com.warchaser.annotations.bindclick.ListenerClass;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;

public class BindClickField {

    private TypeMirror mFieldType;

    private Name mFieldName;

    private int mResId;

    private ListenerClass mListenerClass;

    public BindClickField(Element element){

        if(element.getKind() != ElementKind.METHOD){
            throw new IllegalArgumentException("Only method can be annotated!");
        }


        final BindClick annotation = element.getAnnotation(BindClick.class);

        mResId = annotation.value();
        mFieldName = element.getSimpleName();
        mFieldType = element.asType();
        mListenerClass = annotation.annotationType().getAnnotation(ListenerClass.class);
    }

    public Name getFieldName(){
        return mFieldName;
    }

    public ListenerClass getListenerClass(){
        return mListenerClass;
    }

    public int getResId(){
        return mResId;
    }

    public TypeMirror getFieldType(){
        return mFieldType;
    }

}
