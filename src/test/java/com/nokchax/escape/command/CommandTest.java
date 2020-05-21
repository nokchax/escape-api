package com.nokchax.escape.command;

import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;

class CommandTest {

    @Test
    void test() {
        System.out.println("JUST TEST CLASS START");
        ParentClass parentClass = new ParentClass();
        System.out.println("parentClass = " + parentClass);
        System.out.println("JUST TEST CLASS END");
        System.out.println("JUST CHILD CLASS START");
        ChildClass childClass = new ChildClass();
        System.out.println("childClass = " + childClass);
        System.out.println("JUST CHILD CLASS END");
        SomeClass someClass = new SomeClass();
        System.out.println("someClass = " + someClass);
    }

    static class TestClass<T> {
        Class<T> clazz;

        TestClass() {
            ParameterizedType parameterizedType = (ParameterizedType) getClass()
                    .getGenericSuperclass();

            @SuppressWarnings("unchecked")
            Class<T> ret = (Class<T>) parameterizedType.getActualTypeArguments()[0];
            System.out.println("ret : " + ret);

            System.out.println(clazz);

            System.out.println("This : " + this);
        }
    }

    static class SomeClass extends TestClass<String> implements TestInterface {
        SomeClass() {
            super();
            System.out.println(String.class);
        }
    }

    static class ParentClass {
        ParentClass() {
            System.out.println("This : " + this);
        }
    }

    static class ChildClass extends ParentClass implements TestInterface {
        ChildClass() {
            super();
        }
    }

    interface TestInterface {}
}