package com.warchaser.compiler.annotationprocessor.viewid;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class ViewAnnotationClass {

    static class TypeUtil{
        public final static ClassName finderType = ClassName.get("com.warchaser.annotations_ui.viewid", "IFinder");

        public final static ClassName binderType = ClassName.get("com.warchaser.annotations_ui.viewid", "IViewBinder");
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
                .addParameter(TypeName.get(mTypeElement.asType()), "target")
                .addParameter(TypeName.OBJECT, "object")
                .addParameter(TypeUtil.finderType, "finder");

        for(ViewField field : mFields){
            finderBuilder.addStatement(
                    "target.$N = ($T)(finder.findView(object, $L))",
                    field.getFieldName(),
                    ClassName.get(field.getFieldType()),
                    field.getResId()
            );
        }

        MethodSpec.Builder unBinder = MethodSpec.methodBuilder("unbind")
                .returns(void.class)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mTypeElement.asType()), "target");

        for(ViewField field : mFields){
            unBinder.addStatement("target.$N = null", field.getFieldName());
        }

        TypeSpec clazz = TypeSpec.classBuilder(mTypeElement.getSimpleName() + "$$IViewBinder")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.binderType, TypeName.get(mTypeElement.asType())))
                .addMethod(finderBuilder.build())
                .addMethod(unBinder.build())
                .build();

        final String packageName = mElementUtils.getPackageOf(mTypeElement).getQualifiedName().toString();

        return JavaFile.builder(packageName, clazz).build();
    }

}
