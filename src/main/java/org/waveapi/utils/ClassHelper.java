package org.waveapi.utils;

import javassist.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassHelper {

    public static ClassLoader loader;

    private static final File loaderBase = new File("./waveAPI/classes");
    public static boolean rebuild;
    private static final Map<String, Class<?>> cache = new HashMap();

    static {
        try {
            loader = new URLClassLoader(new URL[]{loaderBase.toURI().toURL()}, ClassHelper.class.getClassLoader());
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

        default String getName() {
            return null;
        }
    }

    public static <T> Class<?> LoadOrGenerateCompoundClass(Generator generator) {
        String[] base = null;
        List<String> interfaces = null;
        String name = generator.getName();
        if (name == null) {
            base = generator.getBaseMethods();
            interfaces = generator.getInterfaces();

            StringBuilder builder = new StringBuilder("wave");

            for (String b : base) {
                builder.append(".").append(b.replaceAll("\\.", ""));
            }

            for (String i : interfaces) {
                builder.append(".").append(i.replaceAll("\\.", ""));
            }
            name = builder.toString();
        }


        Class<?> cached = cache.get(name);
        if (cached != null) {
            return cached;
        }

        if (!rebuild && new File(loaderBase, name.replaceAll("\\.", "/")).exists()) {
            try {
                return loader.loadClass(name);
            } catch (ClassNotFoundException | ClassCastException e) {
                return LoadOrGenerateCompoundClass(generator);
            }
        } else {
            if (base == null) {
                base = generator.getBaseMethods();
                interfaces = generator.getInterfaces();
            }

            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.makeClass(name);
            ctClass.defrost();
            try {
                CtClass baseClass = pool.getCtClass(base[0]);
                ctClass.setSuperclass(baseClass.getSuperclass());

                for (CtConstructor constructor : baseClass.getDeclaredConstructors()) {
                    ctClass.addConstructor(new CtConstructor(constructor, ctClass, null));
                }

                for (String extra : base) {

                    CtClass cls = pool.getCtClass(extra);

                    for (CtClass interfce : cls.getInterfaces()) {
                        ctClass.addInterface(interfce);
                    }

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

                for (String impl : interfaces) {
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

                Class<?> toCached = loader.loadClass(name);
                cache.put(name, toCached);
                return toCached;
            } catch (CannotCompileException | NotFoundException | IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
