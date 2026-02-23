package com.insiderOne.utilities;

public final class PageInit {

    private PageInit() {
        throw new IllegalStateException("the class is a utility class and cannot be instantiated");
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> pageClass) {
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Cannot create page: " + pageClass.getName(), e);
        }
    }
}
