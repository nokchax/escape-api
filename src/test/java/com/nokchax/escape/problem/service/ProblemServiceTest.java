package com.nokchax.escape.problem.service;

import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.exception.UserNotFoundException;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ProblemServiceTest extends ServiceLayerTest {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void checkSolvedProblemExistTest() {
        beforeQuery();
        User user = userRepository.findByUserIdWithSolvedProblems("nokchax14").get(0);
        afterQuery();

        assertThat(user.getSolvedProblemCount()).isEqualTo(30);
        assertThat(user.getSolvedProblem().size()).isEqualTo(30);

        beforeQuery();
        problemService.checkSolvedProblemExist(user, createTestCrawledUserInfo());
        afterQuery();

        entityManager.flush();
        entityManager.clear();

        beforeQuery();
        user = userRepository.findById("nokchax14")
                .orElseThrow(() -> new UserNotFoundException("nokchax14"));
        afterQuery();

        assertThat(user.getSolvedProblemCount()).isEqualTo(40);
        assertThat(user.getSolvedProblem().size()).isEqualTo(40);
    }


    private static CrawledUserInfo createTestCrawledUserInfo() {
        return CrawledUserInfo.builder()
                .userId("nokchax14")
                .solvedProblemCount(40)
                .solvedProblems(createTestSolvedProblem())
                .build();
    }

    private static Set<ProblemSolveInfo> createTestSolvedProblem() {
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

        return titles.stream()
                .map(
                        title -> ProblemSolveInfo.builder()
                        .problemTitle(title)
                        .solvedDate(LocalDateTime.of(1900, 1, 1,0, 0))
                        .build()
                )
                .collect(Collectors.toSet());
    }
}