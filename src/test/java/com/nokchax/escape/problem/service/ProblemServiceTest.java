package com.nokchax.escape.problem.service;

import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.exception.UserNotFoundException;
import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import com.nokchax.escape.problem.domain.Difficulty;
import com.nokchax.escape.problem.dto.ProblemDto;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.repository.UserRepository;
import com.nokchax.escape.util.ObjectMaker;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
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
    private static final List<String> TITLES = Arrays.asList(
            "Combination Sum II", "Combination Sum", "Count and Say", "Sudoku Solver", "Valid Sudoku", "Search Insert Position",
            "Find First and Last Position of Element in Sorted Array", "Search in Rotated Sorted Array", "Longest Valid Parentheses",
            "Next Permutation", "Substring with Concatenation of All Words", "Divide Two Integers", "Implement strStr()", "Remove Element",
            "Remove Duplicates from Sorted Array", "Reverse Nodes in k-Group", "Swap Nodes in Pairs", "Merge k Sorted Lists", "Generate Parentheses",
            "Merge Two Sorted Lists", "Valid Parentheses", "Remove Nth Node From End of List", "4Sum", "Letter Combinations of a Phone Number",
            "3Sum Closest", "3Sum", "Longest Common Prefix", "Roman to Integer", "Integer to Roman", "Container With Most Water", "Regular Expression Matching",
            "Palindrome Number", "String to Integer (atoi)", "Reverse Integer", "ZigZag Conversion", "Longest Palindromic Substring", "Median of Two Sorted Arrays",
            "Longest Substring Without Repeating Characters", "Add Two Numbers", "Two Sum"
    );

    private static final List<CrawledProblemInfo> NEW_PROBLEMS = Arrays.asList(
            ObjectMaker.crawledProblemInfoMaker(10001, "1 1", "1_1", 10001, Difficulty.EASY),
            ObjectMaker.crawledProblemInfoMaker(10002, "1 2", "1_2", 10002, Difficulty.MEDIUM),
            ObjectMaker.crawledProblemInfoMaker(10003, "1 3", "1_3", 10003, Difficulty.HARD),
            ObjectMaker.crawledProblemInfoMaker(10004, "1 4", "1_4", 10004, Difficulty.EASY)
    );

    private static final List<CrawledProblemInfo> EXIST_BUT_UPDATED_PROBLEMS = Arrays.asList(
            ObjectMaker.crawledProblemInfoMaker(100,"Same Tree2","same-tree",100, Difficulty.EASY),
            ObjectMaker.crawledProblemInfoMaker(10,"Regular Expression Matching","regular-expression-matching",10, Difficulty.MEDIUM),
            ObjectMaker.crawledProblemInfoMaker(9,"Palindrome Number","palindrome-number-2",9, Difficulty.EASY),
            ObjectMaker.crawledProblemInfoMaker(11,"Reverse Integer","reverse-integer",7, Difficulty.EASY)
    );

    private static final List<CrawledProblemInfo> EXIST_NOT_UPDATED_PROBLEMS = Arrays.asList(
            ObjectMaker.crawledProblemInfoMaker(4,"Median of Two Sorted Arrays","median-of-two-sorted-arrays",4, Difficulty.HARD),
            ObjectMaker.crawledProblemInfoMaker(3,"Longest Substring Without Repeating Characters","longest-substring-without-repeating-characters",3, Difficulty.MEDIUM),
            ObjectMaker.crawledProblemInfoMaker(2,"Add Two Numbers","add-two-numbers",2, Difficulty.MEDIUM),
            ObjectMaker.crawledProblemInfoMaker(1,"Two Sum","two-sum",1, Difficulty.EASY)
    );

    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private ProblemService problemService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    @DisplayName("유저와 유저가 푼 문제리스트를 넘겼을때 제대로 새로 푼 문제들을 저장을 하는지")
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

        user.getSolvedProblem().forEach(System.out::println);
        assertThat(user.getSolvedProblemCount()).isEqualTo(TITLES.size());
        assertThat(user.getSolvedProblem().size()).isEqualTo(TITLES.size());
    }


    private static CrawledUserInfo createTestCrawledUserInfo() {
        return CrawledUserInfo.builder()
                .userId("nokchax14")
                .solvedProblemCount(TITLES.size())
                .solvedProblems(createTestSolvedProblem())
                .build();
    }

    private static Set<ProblemSolveInfo> createTestSolvedProblem() {

        return TITLES.stream()
                .map(
                        title -> ProblemSolveInfo.builder()
                        .problemTitle(title)
                        .solvedDate(LocalDateTime.of(1900, 1, 1,0, 0))
                        .build()
                )
                .collect(Collectors.toSet());
    }

    @Test
    @DisplayName("새로운 문제 업데이트 테스트")
    void checkProblemUpdatedTest() {
        //완전히 새로운 문제
        NEW_PROBLEMS.forEach(problem -> {
            assertThat(problemRepository.existsById(problem.getProblemId())).isFalse();
        });

        beforeQuery();
        List<ProblemDto> problemDtos = problemService.checkProblemIsNewOrUpdated(NEW_PROBLEMS);
        afterQuery();

        showResult();
        assertThat(problemDtos).isNotNull();
        assertThat(problemDtos.size()).isEqualTo(NEW_PROBLEMS.size());
        problemDtos.forEach(System.out::println);


        beforeClear();
        entityManager.clear();
        afterClear();

        NEW_PROBLEMS.forEach(problem -> {
            assertThat(problemRepository.existsById(problem.getProblemId())).isTrue();
        });
    }

    @Test
    @DisplayName("기존 문제 업데이트 테스트")
    void checkExistingProblemUpdatedTest() {
        //기존 문제인데 업데이트된 문제들
        EXIST_BUT_UPDATED_PROBLEMS.forEach(problem -> {
            assertThat(problemRepository.existsById(problem.getProblemId())).isTrue();
        });

        beforeQuery();
        List<ProblemDto> problemDtos = problemService.checkProblemIsNewOrUpdated(EXIST_BUT_UPDATED_PROBLEMS);
        afterQuery();

        showResult();
        assertThat(problemDtos).isNotNull();
        assertThat(problemDtos.size()).isEqualTo(EXIST_BUT_UPDATED_PROBLEMS.size());
        problemDtos.forEach(System.out::println);


        beforeClear();
        entityManager.clear();
        afterClear();

        EXIST_BUT_UPDATED_PROBLEMS.forEach(problem -> {
            assertThat(problemRepository.existsById(problem.getProblemId())).isTrue();
        });
    }

    @Test
    @DisplayName("업데이트 된 문제가 없을때 테스트")
    void checkExistingProblemButNotUpdatedTest() {
        //기존 문제에 존재하는 문제들이고 업데이트도 없을때
        EXIST_NOT_UPDATED_PROBLEMS.forEach(problem -> {
            assertThat(problemRepository.existsById(problem.getProblemId())).isTrue();
        });

        beforeQuery();
        List<ProblemDto> problemDtos = problemService.checkProblemIsNewOrUpdated(EXIST_NOT_UPDATED_PROBLEMS);
        afterQuery();

        showResult();
        assertThat(problemDtos).isNotNull();
        assertThat(problemDtos.size()).isZero();
        problemDtos.forEach(System.out::println);


        beforeClear();
        entityManager.clear();
        afterClear();

        EXIST_NOT_UPDATED_PROBLEMS.forEach(problem -> {
            assertThat(problemRepository.existsById(problem.getProblemId())).isTrue();
        });
    }
}