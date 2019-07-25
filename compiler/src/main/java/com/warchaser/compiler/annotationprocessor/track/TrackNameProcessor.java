package com.warchaser.compiler.annotationprocessor.track;

import com.google.auto.service.AutoService;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import com.warchaser.compiler.annotationprocessor.track.annotation.TrackName;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class TrackNameProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if(set != null && !set.isEmpty()){
            generateJavaClassFile(set, roundEnvironment);
            return true;
        }
        return false;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(TrackName.class.getCanonicalName());
        return types;
    }

    private void generateJavaClassFile(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment){
        //set of track
        Map<String, String> trackMap = new HashMap<>();
        //print on gradle console
        Messager messager = processingEnv.getMessager();

        for(TypeElement te : annotations){
            for(Element e : roundEnvironment.getElementsAnnotatedWith(te)){
                final Name simpleName =  e.getSimpleName();
                final String simpleNameStr = simpleName.toString();
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.toString());
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + simpleName);
                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.getEnclosingElement().toString());

                TrackName annotation = e.getAnnotation(TrackName.class);
                String name = "".equals(annotation.name()) ? simpleNameStr : annotation.name();
                trackMap.put(simpleNameStr, e.toString());
                messager.printMessage(Diagnostic.Kind.NOTE, "relatives: " + simpleNameStr + "-" + name);
            }
        }

        try {
            final String generatedPackageName = "com.warchaser.compiler.annotationprocessor.track";
            final String generatedClassName = "TrackManager$Helper";

            final JavaFileObject f = processingEnv.getFiler().createSourceFile(generatedClassName);
            messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + f.toUri());
            final Writer w = f.openWriter();

            try {
                final PrintWriter pw = new PrintWriter(w);
                pw.println("package " + generatedPackageName + ";\n");
                pw.println("import java.util.Map;");
                pw.println("import com.warchaser.compiler.annotationprocessor.track.IData;\n");
                pw.println("/**");
                pw.println("* this file is auto-generated by compiler, please do not modify. *");
                pw.println("* page paths *");
                pw.println("*/");
                pw.println("public class " + generatedClassName + " implements IData {");

                pw.println("\n @Override");
                pw.println("    public void loadInto(Map<String, String> map) {");
                final Iterator<String> keys = trackMap.keySet().iterator();
                while (keys.hasNext()) {
                    final String key = keys.next();
                    final String value = trackMap.get(key);
                    pw.println("        map.put(\"" + key + "\",\"" + value + "\");");
                }
                pw.println("    }");
                pw.println("}");
                pw.flush();
            } finally {
                w.close();
            }

        } catch (Exception | Error e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.toString());
        }
    }
}