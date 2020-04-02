package com.nokchax.escape.problem.repository;


import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.dto.ProblemSolveUserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("dev")
class ProblemRepositoryTest {
    @Autowired
    private ProblemRepository problemRepository;

    @ParameterizedTest
    @ValueSource(longs = {1, 1281})
    public void findProblemSolveUserTest(Long problemNo) {
        System.out.println("=========================================================Before find");
        List<ProblemSolveUserDto> problemSolveUser = problemRepository.findProblemSolveUser(problemNo);
        System.out.println("=========================================================After find");

        problemSolveUser.forEach(System.out::println);
    }


    @Test
    public void checkSolvedProblemCountTest() {
        /* solved problems
            1, 2, 3, 4, 5, 6, 7, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 24, 25, 26, 27, 28, 31, 32, 35, 36, 39
            30 문제
        */
        List<String> titles = Arrays.asList(
                        "Combination Sum II", "Combination Sum", "Count and Say", "Sudoku Solver", "Valid Sudoku", "Search Insert Position",
                "Find First and Last Position of Element in Sorted Array", "Search in Rotated Sorted Array", "Longest Valid Parentheses",
                "Next Permutation", "Substring with Concatenation of All Words", "Divide Two Integers", "Implement strStr()", "Remove Element",
                "Remove Duplicates from Sorted Array", "Reverse Nodes in k-Group", "Swap Nodes in Pairs", "Merge k Sorted Lists", "Generate Parentheses",
                "Merge Two Sorted Lists", "Valid Parentheses", "Remove Nth Node From End of List", "4Sum", "Letter Combinations of a Phone Number",
                "3Sum Closest", "3Sum", "Longest Common Prefix", "Roman to Integer", "Integer to Roman", "Container With Most Water", "Regular Expression Matching",
                "Palindrome Number", "String to Integer (atoi)", "Reverse Integer", "ZigZag Conversion", "Longest Palindromic Substring", "Median of Two Sorted Arrays",
                "Longest Substring Without Repeating Characters", "Add Two Numbers", "Two Sum"
        );

        System.out.println("=========================================================Before query");
        List<Problem> notSavedSolvedProblems = problemRepository.checkSolvedProblemCount("nokchax14", titles);
        System.out.println("=========================================================After query");

        assertThat(notSavedSolvedProblems.size()).isEqualTo(10);

        notSavedSolvedProblems.forEach(System.out::println);
    }
}