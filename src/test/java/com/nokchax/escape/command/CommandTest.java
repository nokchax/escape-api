package com.nokchax.escape.command;

import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;

class CommandTest {

    @Test
    void test() {
        SomeClass someClass = new SomeClass();
        System.out.println("someClass = " + someClass);

    }

    class TestClass<T> {
        Class<T> clazz;

        TestClass() {
            ParameterizedType parameterizedType = (ParameterizedType) getClass()
                    .getGenericSuperclass();

            @SuppressWarnings("unchecked")
            Class<T> ret = (Class<T>) parameterizedType.getActualTypeArguments()[0];
            System.out.println("ret : " + ret);

            System.out.println(clazz);
        }
    }

    class SomeClass extends TestClass<String> {
        SomeClass() {
            super();
            System.out.println(String.class);
        }
    }
}