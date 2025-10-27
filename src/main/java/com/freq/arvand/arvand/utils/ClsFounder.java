package com.freq.arvand.arvand.utils;

public class ClsFounder {
    public static Class<?> getClass(Class<?> className, String whatClass) throws Exception {
        if (className == null) {
            throw new IllegalArgumentException("Input class cannot be null");
        }

        try {
            String simpleName = className.getSimpleName();
            String packageName = className.getPackage().getName();
            String outClass;

            if (simpleName.endsWith("Impl")) {
                outClass = simpleName.replace("Impl", whatClass);
            }
            else if (simpleName.endsWith("Entity")) {
                outClass = simpleName.replace("Entity", whatClass);
            }
            else {
                throw new IllegalArgumentException("Class must end with 'Impl'");
            }
            String fullyQualified = packageName + "." + outClass;
            return Class.forName(fullyQualified);
        } catch (ClassNotFoundException e) {
            throw new Exception(whatClass + " for " + className.getName() + " not found", e);
        }
    }

}
