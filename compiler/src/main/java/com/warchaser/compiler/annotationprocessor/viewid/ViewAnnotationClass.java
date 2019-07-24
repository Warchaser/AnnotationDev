package com.warchaser.compiler.annotationprocessor.viewid;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class ViewAnnotationClass {

    static class TypeUtil{
        public final static ClassName finderType = ClassName.get("com.warchaser.annotations_ui", "IFinder");

        public final static ClassName binderType = ClassName.get("com.warchaser.annotations_ui", "IViewBinder");
    }

    private TypeElement mTypeElement;

    private Elements mElementUtils;

    List<ViewField> mFields = new ArrayList<>();

    public ViewAnnotationClass(TypeElement typeElement, Elements elementUtils){
        mTypeElement = typeElement;
        mElementUtils = elementUtils;
    }

    public void addField(ViewField viewField){
        mFields.add(viewField);
    }

    public JavaFile generateFile(){
        MethodSpec.Builder finderBuilder = MethodSpec.methodBuilder("bindView")
                .returns(void.class)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get((Type) mTypeElement.asType()), "target")
                .addParameter(TypeName.OBJECT, "object")
                .addParameter(TypeUtil.finderType, "finder");


    }

}
