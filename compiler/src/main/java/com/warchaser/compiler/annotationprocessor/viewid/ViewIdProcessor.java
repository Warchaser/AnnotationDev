package com.warchaser.compiler.annotationprocessor.viewid;

import com.google.auto.service.AutoService;
import com.warchaser.annotations.viewid.ViewById;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

/**
 * Processor
 * FindById
 * */
@AutoService(Processor.class)
public class ViewIdProcessor extends AbstractProcessor {

    private TreeMap<String, ViewAnnotationClass> annotationClassTreeMap;

    private Messager $messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        annotationClassTreeMap = new TreeMap<>();
        $messager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        annotationClassTreeMap.clear();
        for(Element element : roundEnvironment.getElementsAnnotatedWith(ViewById.class)){
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            String className = typeElement.getQualifiedName().toString();
            ViewAnnotationClass viewAnnotationClass = annotationClassTreeMap.get(className);
            if(viewAnnotationClass == null){
                viewAnnotationClass = new ViewAnnotationClass(typeElement, processingEnv.getElementUtils());
                annotationClassTreeMap.put(className, viewAnnotationClass);
            }

            ViewField viewField = new ViewField(element);

            viewAnnotationClass.addField(viewField);
        }

        for(ViewAnnotationClass viewAnnotationClass : annotationClassTreeMap.values()){
            try {
                viewAnnotationClass.generateFile().writeTo(processingEnv.getFiler());
            } catch (Exception | Error e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> supportedAnnotationTypes = new HashSet<>();
        supportedAnnotationTypes.add(ViewById.class.getCanonicalName());
        return supportedAnnotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
