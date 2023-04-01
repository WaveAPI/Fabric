package org.waveapi.utils;

import javassist.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

public class ClassHelper {

    public static ClassLoader loader;

    static {
        try {
            loader = new URLClassLoader(new URL[]{new File("./waveAPI/classes").toURI().toURL()}, ClassHelper.class.getClassLoader());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public interface Generator {
        String[] getBaseMethods();
        default String[] extras() {
            return new String[] {};
        }
        List<String> getInterfaces();
    }

    public static <T> Class<?> LoadOrGenerateCompoundClass(String name, Generator generator, boolean generate) {
        if (generate) {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.makeClass(name);
            ctClass.defrost();
            try {
                String[] base = generator.getBaseMethods();
                CtClass baseClass = pool.getCtClass(base[0]);
                ctClass.setSuperclass(baseClass.getSuperclass());

                for (CtConstructor constructor : baseClass.getDeclaredConstructors()) {
                    ctClass.addConstructor(new CtConstructor(constructor, ctClass, null));
                }

                for (String extra : base) {
                    CtClass cls = pool.getCtClass(extra);
                    for (CtMethod method : cls.getDeclaredMethods()) {
                        String lName = method.getLongName();
                        boolean notContainsMethod = Arrays.stream(ctClass.getDeclaredMethods()).noneMatch(
                                (x) -> x.getLongName().equals(lName)
                        );
                        if (notContainsMethod) {
                            ctClass.addMethod(new CtMethod(method, ctClass, null));
                        }
                    }

                    for (CtField field : cls.getDeclaredFields()) {

                        boolean notContainsField = Arrays.stream(ctClass.getDeclaredFields()).noneMatch(
                                (x) -> x.getName().equals(field.getName())
                        );
                        if (notContainsField) {
                            ctClass.addField(new CtField(field, ctClass));
                        }
                    }
                }

                for (String impl : generator.getInterfaces()) {
                    CtClass cls = pool.getCtClass(impl);
                    for (CtClass interfce : cls.getInterfaces()) {
                        ctClass.addInterface(interfce);
                    }
                    for (CtMethod method : cls.getDeclaredMethods()) {
                        ctClass.addMethod(new CtMethod(method, ctClass, null));
                    }

                    for (CtField field : cls.getDeclaredFields()) {
                        boolean notContainsField = Arrays.stream(ctClass.getDeclaredFields()).noneMatch((x) -> x.getName().equals(field.getName()));
                        if (notContainsField) {
                            ctClass.addField(new CtField(field, ctClass));
                        }
                    }
                }

                ctClass.writeFile("./waveAPI/classes");
                ctClass.freeze();
            } catch (CannotCompileException | NotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return loader.loadClass(name);
        } catch (ClassNotFoundException | ClassCastException e) {
            return LoadOrGenerateCompoundClass(name, generator, true);
        }
    }

}
