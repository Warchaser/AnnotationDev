package com.warchaser.compiler.annotationprocessor.bindclick;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.warchaser.annotations.bindclick.ListenerClass;
import com.warchaser.annotations.bindclick.ListenerMethod;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class ClickAnnotationClass {

    private static class TypeUtil{
        static final ClassName BINDER = ClassName.get("com.warchaser.annotations_ui.bindclick", "ClickBinder");
    }

    private TypeElement mTypeElement;

    private ArrayList<BindClickField> mFields;

    private Elements mElements;

    private static final ClassName VIEW = ClassName.get("android.view", "View");

    public ClickAnnotationClass(TypeElement typeElement, Elements elements){
        mTypeElement = typeElement;
        mElements = elements;
        mFields = new ArrayList<>();
    }

    public void addField(BindClickField field){
        mFields.add(field);
    }

    public JavaFile generateFile(){
        //Generate method
        MethodSpec.Builder bindClickMethod = MethodSpec.methodBuilder("bindClick")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(mTypeElement.asType()), "host", Modifier.FINAL)
                .addParameter(TypeName.OBJECT, "source");

        for(BindClickField field : mFields){
            ListenerClass listenerClass = field.getListenerClass();
            ListenerMethod method = listenerClass.method()[0];
            String name = field.getFieldName().toString();

            MethodSpec.Builder onClickMethod = MethodSpec.methodBuilder(method.name())
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(VIEW, "view")
                    .returns(void.class)
                    .addStatement("host.$L()", name);

            TypeSpec onClick = TypeSpec.annotationBuilder("")
                    .superclass(ClassName.bestGuess(listenerClass.type()))
                    .addMethod(onClickMethod.build())
                    .build();

            bindClickMethod.addStatement("host.findViewById($L).$L($L)", field.getResId(), listenerClass.setter(), onClick);
        }

        MethodSpec.Builder unBindViewMethod = MethodSpec.methodBuilder("unBindClick")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(mTypeElement.asType()), "host")
                .addAnnotation(Override.class);

        for(BindClickField field : mFields){
            unBindViewMethod.addStatement("host.findViewById($L).setOnClickListener(null)", field.getResId());
        }

        TypeSpec injectClass = TypeSpec.classBuilder(mTypeElement.getSimpleName() + "$$ClickBinder")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.BINDER, TypeName.get(mTypeElement.asType())))
                .addMethod(bindClickMethod.build())
                .addMethod(unBindViewMethod.build())
                .build();

        final String packageName = mElements.getPackageOf(mTypeElement).getQualifiedName().toString();

        return JavaFile.builder(packageName, injectClass).build();
    }
}
