package com.warchaser.compiler.annotationprocessor.bindclick;

import com.google.auto.service.AutoService;
import com.warchaser.annotations.bindclick.BindClick;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class BindClickProcessor extends AbstractProcessor {

    private Filer mFiler;

    private Elements mElementsUtils;

    private Messager mMessager;

    private Map<String, ClickAnnotationClass> mClickAnnotatedClasses;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnv.getFiler();
        mElementsUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        mClickAnnotatedClasses = new TreeMap<>();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        mClickAnnotatedClasses.clear();

        try {
            processBindClick(roundEnvironment);
        } catch (Exception | Error e) {
            e.printStackTrace();
            error(e.getMessage());
        }

        for(ClickAnnotationClass clickAnnotationClass : mClickAnnotatedClasses.values()){
            try {
                clickAnnotationClass.generateFile().writeTo(mFiler);
            } catch (Exception | Error e) {
                e.printStackTrace();
                error("Generate file failed! The reason: %s", e.getMessage());
            }
        }

        return true;
    }

    /**
     *
     * */
    private void processBindClick(RoundEnvironment roundEnvironment) throws IllegalArgumentException{
        for(Element element : roundEnvironment.getElementsAnnotatedWith(BindClick.class)){
            final ClickAnnotationClass clickAnnotationClass = getClickAnnotatedClass(element);
            final BindClickField bindClickField = new BindClickField(element);
            clickAnnotationClass.addField(bindClickField);
        }
    }

    private ClickAnnotationClass getClickAnnotatedClass(Element element){
        final TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        final String fullName = typeElement.getQualifiedName().toString();
        ClickAnnotationClass clickAnnotationClass = mClickAnnotatedClasses.get(fullName);
        if(clickAnnotationClass == null){
            clickAnnotationClass = new ClickAnnotationClass(typeElement, mElementsUtils);
            mClickAnnotatedClasses.put(fullName, clickAnnotationClass);
        }
        return clickAnnotationClass;
    }

    private void error(String msg, Object ... args){
        if(mMessager != null){
            mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet<>();
        types.add(BindClick.class.getCanonicalName());
        return types;
    }
}
