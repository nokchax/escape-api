package com.nokchax.escape.problem.service;

import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import com.nokchax.escape.mission.service.MissionService;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@DataJpaTest
@Import({ProblemService.class, MissionService.class})
@ActiveProfiles("dev")
class ProblemServiceTest {
    @Autowired
    private ProblemService problemService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void checkSolvedProblemExistTest() throws Exception {
        User user = userRepository.findById("nokchax14")
                .orElseThrow(Exception::new);

        System.out.println("Before Update");
        System.out.println(user);


        System.out.println("=========================================================Before query");
        problemService.checkSolvedProblemExist(user, createTestCrawledUserInfo());
        System.out.println("=========================================================After query");

        entityManager.flush();
        entityManager.clear();

        System.out.println("After Update");
        user = userRepository.findById("nokchax14")
                .orElseThrow(Exception::new);

        System.out.println(user);
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