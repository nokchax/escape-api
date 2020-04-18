package com.nokchax.escape.leetcode.service;


import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.command.UpdateCommand;
import com.nokchax.escape.entry.dto.EntryDto;
import com.nokchax.escape.entry.service.EntryService;
import com.nokchax.escape.leetcode.crawl.api.LeetcodeApiCrawlerWithLogin;
import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.leetcode.crawl.page.LeetcodePageCrawler;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import com.nokchax.escape.mission.service.MissionService;
import com.nokchax.escape.problem.domain.Difficulty;
import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.domain.SolvedProblem;
import com.nokchax.escape.problem.dto.ProblemDto;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.problem.repository.SolvedProblemRepository;
import com.nokchax.escape.problem.service.ProblemService;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo.NOT_UPDATED_USER_INFO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
class UpdateServiceTest extends ServiceLayerTest {
    private static final String TEST_USER_ID = "nokchax3";
    private static final String ANOTHER_TEST_USER_ID = "nokchax4";
    private static final User MOCK_TEST_USER = new User(TEST_USER_ID, "test", "greentea");
    private static final User ANOTHER_MOCK_TEST_USER = new User(ANOTHER_TEST_USER_ID, "test", "greenteaa");
    public static final UpdateCommand.UpdateArgument UPDATE_ARGUMENT = UpdateCommand.UpdateArgument
            .builder()
            .target(TEST_USER_ID)
            .requestUsersTelegramId("")
            .build();

    @Autowired
    private UpdateService updateService;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private SolvedProblemRepository solvedProblemRepository;
    @SpyBean
    private UserService userService;
    @SpyBean
    private EntryService entryService;
    @MockBean
    private LeetcodePageCrawler pageCrawler;
    @MockBean
    private LeetcodeApiCrawlerWithLogin apiCrawler;

    @Test
    @DisplayName("문제 리스트 업데이트 테스트")
    void updateProblemsTest() {
        given(userService.findRandomUser()).willReturn(MOCK_TEST_USER);
        given(apiCrawler.crawlProblems(MOCK_TEST_USER)).willReturn(testProblemInfo());

        List<Problem> problems = problemRepository.findAll();
        entityManager.clear();

        beforeQuery();
        List<ProblemDto> problemDtos = updateService.updateProblems();
        afterQuery();

        List<Problem> problemsAfterUpdate = problemRepository.findAll();

        assertThat(problemDtos.size()).isEqualTo(2);
        assertThat(problemsAfterUpdate.size()).isEqualTo(problems.size() + 1);

        showResult();
        log.info("Before problems size : " + problems.size());
        log.info("After problems size : " + problemsAfterUpdate.size());
        problemDtos.forEach(System.out::println);
    }

    @Test
    @Transactional
    @DisplayName("페이지 크롤링으로 업데이트를 성공한 경우(문제를 푼 경우)")
    void updateUserTest() {
        given(pageCrawler.crawlUserInfo(any())).willReturn(testCrawledUserInfo());

        List<SolvedProblem> solvedProblemsBeforeUpdate = solvedProblemRepository.findSolvedProblemsByUserId(TEST_USER_ID);

        beforeQuery();
        updateService.updateLatestMission(UPDATE_ARGUMENT);
        afterQuery();

        entityManager.flush();
        entityManager.clear();

        List<SolvedProblem> solvedProblemsAfterUpdate = solvedProblemRepository.findSolvedProblemsByUserId(TEST_USER_ID);

        assertThat(solvedProblemsBeforeUpdate.size()).isEqualTo(solvedProblemsAfterUpdate.size() - 1);
        verify(pageCrawler, times(1)).crawlUserInfo(any());
        verify(apiCrawler, times(0)).crawlUserInfo(any());

        showResult();
        log.info("업데이트 전 : {}", solvedProblemsBeforeUpdate.size());
        log.info("업데이트 후 : {}", solvedProblemsAfterUpdate.size());
        List<EntryDto> users = entryService.findAllUserInLatestMission(Collections.singletonList(MOCK_TEST_USER));
        users.forEach(System.out::println);
    }

    @Test
    @Transactional
    @DisplayName("페이지 크롤링으로 업데이트를 성공한 경우(추가적으로 푼 문제가 없는경우)")
    void updateUserTest_didNotSolvedProblem() {
        given(pageCrawler.crawlUserInfo(any())).willReturn(NOT_UPDATED_USER_INFO);

        List<SolvedProblem> solvedProblemsBeforeUpdate = solvedProblemRepository.findSolvedProblemsByUserId(TEST_USER_ID);

        beforeQuery();
        updateService.updateLatestMission(UPDATE_ARGUMENT);
        afterQuery();

        entityManager.flush();
        entityManager.clear();

        List<SolvedProblem> solvedProblemsAfterUpdate = solvedProblemRepository.findSolvedProblemsByUserId(TEST_USER_ID);

        assertThat(solvedProblemsBeforeUpdate.size()).isEqualTo(solvedProblemsAfterUpdate.size());
        verify(pageCrawler, times(1)).crawlUserInfo(any());
        verify(apiCrawler, times(0)).crawlUserInfo(any());

        showResult();
        log.info("업데이트 전 : {}", solvedProblemsBeforeUpdate.size());
        log.info("업데이트 후 : {}", solvedProblemsAfterUpdate.size());
        List<EntryDto> users = entryService.findAllUserInLatestMission(Collections.singletonList(MOCK_TEST_USER));
        users.forEach(System.out::println);
    }

    @Test
    @Transactional
    @DisplayName("페이지 크롤링으로 업데이트 실패 후 로그인 크롤링으로 업데이트 성공")
    void updateUserTest_FailToCrawlWithPageBuSuccessToLoginApi() {
        given(pageCrawler.crawlUserInfo(any())).willReturn(testCrawledUserInfoButNotEnoughInfo());
        given(apiCrawler.crawlUserInfo(any())).willReturn(testCrawledUserInfoEnough());

        List<SolvedProblem> solvedProblemsBeforeUpdate = solvedProblemRepository.findSolvedProblemsByUserId(TEST_USER_ID);

        beforeQuery();
        updateService.updateLatestMission(UPDATE_ARGUMENT);
        afterQuery();

        entityManager.flush();
        entityManager.clear();

        List<SolvedProblem> solvedProblemsAfterUpdate = solvedProblemRepository.findSolvedProblemsByUserId(TEST_USER_ID);

        verify(pageCrawler, times(1)).crawlUserInfo(any());
        verify(apiCrawler, times(1)).crawlUserInfo(any());
        assertThat(solvedProblemsBeforeUpdate.size() + 2).isEqualTo(solvedProblemsAfterUpdate.size());

        showResult();
        log.info("업데이트 전 : {}", solvedProblemsBeforeUpdate.size());
        log.info("업데이트 후 : {}", solvedProblemsAfterUpdate.size());
        List<EntryDto> users = entryService.findAllUserInLatestMission(Collections.singletonList(MOCK_TEST_USER));
        users.forEach(System.out::println);
    }

    @Test
    @Transactional
    @DisplayName("페이지 크롤링시 에러, 로그인 크롤링으로 업데이트 성공")
    void updateUserTest_PageCrawlerThrowsException() {
        given(pageCrawler.crawlUserInfo(any())).willThrow(RuntimeException.class);
        given(apiCrawler.crawlUserInfo(any())).willReturn(testCrawledUserInfoEnough());

        List<SolvedProblem> solvedProblemsBeforeUpdate = solvedProblemRepository.findSolvedProblemsByUserId(TEST_USER_ID);

        beforeQuery();
        updateService.updateLatestMission(UPDATE_ARGUMENT);
        afterQuery();

        entityManager.flush();
        entityManager.clear();

        List<SolvedProblem> solvedProblemsAfterUpdate = solvedProblemRepository.findSolvedProblemsByUserId(TEST_USER_ID);

        verify(pageCrawler, times(1)).crawlUserInfo(any());
        verify(apiCrawler, times(1)).crawlUserInfo(any());
        assertThat(solvedProblemsBeforeUpdate.size() + 2).isEqualTo(solvedProblemsAfterUpdate.size());

        showResult();
        log.info("업데이트 전 : {}", solvedProblemsBeforeUpdate.size());
        log.info("업데이트 후 : {}", solvedProblemsAfterUpdate.size());
        List<EntryDto> users = entryService.findAllUserInLatestMission(Collections.singletonList(MOCK_TEST_USER));
        users.forEach(System.out::println);
    }

    @Test
    @Transactional
    @DisplayName("페이지 크롤링시 에러, 로그인 크롤링으로도 에러")
    void updateUserTest_PageCrawlerAndLoginCrawlerThrowsException() {
        given(pageCrawler.crawlUserInfo(MOCK_TEST_USER)).willReturn(testCrawledUserInfo());
        given(pageCrawler.crawlUserInfo(any())).willThrow(RuntimeException.class);
        given(apiCrawler.crawlUserInfo(any())).willThrow(RuntimeException.class);

        beforeQuery();
        List<EntryDto> entries = updateService.updateLatestMission(UPDATE_ARGUMENT);
        afterQuery();

        assertThat(entries.size()).isOne();
    }

    @Test
    @Transactional
    @DisplayName("일부 유저는 크롤 성공, 일부는 실패")
    void someUserSuccessToCrawlButOtherNotTest() {
        given(pageCrawler.crawlUserInfo(ANOTHER_MOCK_TEST_USER)).willThrow(RuntimeException.class);
        given(apiCrawler.crawlUserInfo(ANOTHER_MOCK_TEST_USER)).willThrow(RuntimeException.class);
        given(pageCrawler.crawlUserInfo(MOCK_TEST_USER)).willReturn(NOT_UPDATED_USER_INFO);
        given(userService.findByArgument(UPDATE_ARGUMENT)).willReturn(Arrays.asList(MOCK_TEST_USER, ANOTHER_MOCK_TEST_USER));

        beforeQuery();
        List<EntryDto> entries = updateService.updateLatestMission(UPDATE_ARGUMENT);
        afterQuery();

        assertThat(entries.size()).isEqualTo(2);
    }

    private CrawledUserInfo testCrawledUserInfo() {
        return CrawledUserInfo.builder()
                .userId(TEST_USER_ID)
                .solvedProblemCount(95)
                .solvedProblems(
                        new HashSet<>(Collections.singletonList(
                                ProblemSolveInfo.builder()
                                        .problemTitle("Minimum Knight Moves")
                                        .solvedDate(LocalDateTime.of(2020, 3, 24, 0, 0))
                                        .build()
                        ))
                )
                .build();
    }

    private CrawledUserInfo testCrawledUserInfoButNotEnoughInfo() {
        return CrawledUserInfo.builder()
                .userId(TEST_USER_ID)
                .solvedProblemCount(96)
                .solvedProblems(
                        new HashSet<>(Collections.singletonList(
                                ProblemSolveInfo.builder()
                                        .problemTitle("Minimum Knight Moves")
                                        .solvedDate(LocalDateTime.of(2020, 3, 24, 0, 0))
                                        .build()
                        ))
                )
                .build();
    }

    private CrawledUserInfo testCrawledUserInfoEnough() {
        return CrawledUserInfo.builder()
                .userId(TEST_USER_ID)
                .solvedProblemCount(96)
                .solvedProblems(
                        new HashSet<>(Arrays.asList(
                                ProblemSolveInfo.builder()
                                        .problemTitle("Minimum Knight Moves")
                                        .solvedDate(LocalDateTime.of(2020, 3, 24, 0, 0))
                                        .build(),
                                ProblemSolveInfo.builder()
                                        .problemTitle("Find Smallest Common Element in All Rows")
                                        .solvedDate(LocalDateTime.of(2020, 3, 24, 0, 0))
                                        .build()
                        ))
                )
                .build();
    }

    private List<CrawledProblemInfo> testProblemInfo() {
        return Arrays.asList(
                CrawledProblemInfo.builder()// 새문제
                        .difficulty(Difficulty.EASY)
                        .frontendProblemId(10001L)
                        .problemHide(false)
                        .problemId(10001L)
                        .problemTitle("test title")
                        .problemTitleSlug("test-title")
                        .build(),

                CrawledProblemInfo.builder()// 변화가 없는 문제
                        .difficulty(Difficulty.MEDIUM)
                        .frontendProblemId(2L)
                        .problemHide(false)
                        .problemId(2L)
                        .problemTitle("Add Two Numbers")
                        .problemTitleSlug("add-two-numbers")
                        .build(),

                CrawledProblemInfo.builder()// 업데이트 된 문제
                        .difficulty(Difficulty.EASY)
                        .frontendProblemId(1L)
                        .problemHide(false)
                        .problemId(1L)
                        .problemTitle("Two Sum changed")
                        .problemTitleSlug("two-sum")
                        .build()
        );
    }
}