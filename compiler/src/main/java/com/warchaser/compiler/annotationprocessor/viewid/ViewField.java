package com.warchaser.compiler.annotationprocessor.viewid;


import com.warchaser.annotations.viewid.ViewById;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;

public class ViewField {

    private TypeMirror mFieldType;

    private Name mFieldName;

    private int mResId;

    public ViewField(Element element){
        if(element.getKind() != ElementKind.FIELD){
            throw new IllegalArgumentException("Only fields can be annotated!");
        }

        final ViewById annotation = element.getAnnotation(ViewById.class);

        mResId = annotation.value();
        mFieldName = element.getSimpleName();
        mFieldType = element.asType();
    }

    public TypeMirror getFieldType(){
        return mFieldType;
    }

    public Name getFieldName(){
        return mFieldName;
    }

    public int getResId() {
        return mResId;
    }
}
