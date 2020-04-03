package com.nokchax.escape.leetcode.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

class UpdateServiceTest {
    private List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    @Test
    void streamTest() {
        System.out.println("Foreach");
        list.stream()
                .forEach(System.out::println);

        System.out.println("Foreach ordered");
        list.stream()
                .forEachOrdered(System.out::println);
    }

    @Test
    void streamParallelTest() {
        System.out.println("Foreach parallel");
        list.stream()
                .parallel()
                .forEach(System.out::println);

        System.out.println("Foreach ordered parallel");
        list.stream()
                .parallel()
                .forEachOrdered(System.out::println);
    }


    @Test
    void findFirstTest() throws Exception {
        List<Integer> nullContainedLists = Arrays.asList(null, null, 1, null, 2);

        Integer integer = nullContainedLists.stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(Exception::new);

        System.out.println(integer);
    }

    @Test
    void findFirstTestWithMethodCall() throws Exception {
        List<TestInterface> nullContainedLists = Arrays.asList(
                () -> {System.out.println("null return class 1"); return null;},
                () -> {System.out.println("null return class 2"); return null;},
                () -> {System.out.println("integer return class 1"); return 1;}, // 이 아래 메소드는 실행 안되는게 맞음
                () -> {System.out.println("null return class 3"); return null;},
                () -> {System.out.println("integer return class 2"); return 2;}
        );

        Integer integer = nullContainedLists.stream()
                .map(TestInterface::returnInt)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(Exception::new);

        System.out.println(integer);
    }
}