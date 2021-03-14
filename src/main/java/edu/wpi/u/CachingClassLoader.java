package edu.wpi.u;

import java.util.HashMap;
import java.util.Map;

public class CachingClassLoader extends ClassLoader{

    private final Map<String, Class> classes = new HashMap<>();
    private final ClassLoader parent;

    public CachingClassLoader(ClassLoader parent) {
        this.parent = parent;
    }

    /**
     * Tries to load a class
     * @param className the class to be loaded
     * @return the loaded class
     * @throws ClassNotFoundException if the class does not exist
     */
    @Override
    public Class loadClass(String className) throws ClassNotFoundException {
        Class aClass = findClass(className);
        if (aClass == null) {
            throw new ClassNotFoundException(className);
        }
        return aClass;
    }

    /**
     * Finds a class in the HashMap of classes, or loads class into HashMap or adds a class that isn't in the HashMap
     * @param className the class name to be found
     * @return the found class or if not found, is added to hashmap
     */
    @Override
    protected Class findClass(String className) {
        if (classes.containsKey(className)) {
            return classes.get(className);
        } else {
            try {
                Class result = parent.loadClass(className);
                classes.put(className, result);
                return result;
            } catch (Exception e) {
                classes.put(className, null);
                return null;
            }
        }
    }
}