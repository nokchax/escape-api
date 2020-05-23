package com.nokchax.escape.command;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;

@Slf4j
class CommandTest {

    @Test
    void test() {
        log.info("JUST TEST CLASS START");
        ParentClass parentClass = new ParentClass();
        log.info("parentClass = " + parentClass);
        log.info("JUST TEST CLASS END");
        log.info("JUST CHILD CLASS START");
        ChildClass childClass = new ChildClass();
        log.info("childClass = " + childClass);
        log.info("JUST CHILD CLASS END");
        SomeClass someClass = new SomeClass();
        log.info("someClass = " + someClass);
    }

    static class TestClass<T> {
        Class<T> clazz;

        TestClass() {
            ParameterizedType parameterizedType = (ParameterizedType) getClass()
                    .getGenericSuperclass();

            @SuppressWarnings("unchecked")
            Class<T> ret = (Class<T>) parameterizedType.getActualTypeArguments()[0];
            log.info("ret : " + ret);

            log.info("{}", clazz);

            log.info("This : " + this);
        }
    }

    static class SomeClass extends TestClass<String> implements TestInterface {
        SomeClass() {
            super();
            log.info("{}", String.class);
        }
    }

    static class ParentClass {
        ParentClass() {
            log.info("This : " + this);
        }
    }

    static class ChildClass extends ParentClass implements TestInterface {
        ChildClass() {
            super();
        }
    }

    interface TestInterface {}
}