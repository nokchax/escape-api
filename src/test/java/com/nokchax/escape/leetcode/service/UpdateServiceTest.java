package com.nokchax.escape.leetcode.service;


import com.nokchax.escape.ServiceLayerTest;
import com.nokchax.escape.entry.service.EntryService;
import com.nokchax.escape.leetcode.crawl.api.LeetcodeApiCrawlerWithLogin;
import com.nokchax.escape.leetcode.crawl.api.response.CrawledProblemInfo;
import com.nokchax.escape.leetcode.crawl.page.LeetcodePageCrawler;
import com.nokchax.escape.mission.service.MissionService;
import com.nokchax.escape.problem.domain.Difficulty;
import com.nokchax.escape.problem.domain.Problem;
import com.nokchax.escape.problem.dto.ProblemDto;
import com.nokchax.escape.problem.repository.ProblemRepository;
import com.nokchax.escape.problem.service.ProblemService;
import com.nokchax.escape.user.domain.User;
import com.nokchax.escape.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Slf4j
class UpdateServiceTest extends ServiceLayerTest {
    @Autowired
    private UpdateService updateService;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MissionService missionService;
    @MockBean
    private UserService userService;
    @MockBean
    private EntryService entryService;
    @MockBean
    private LeetcodePageCrawler pageCrawler;
    @MockBean
    private LeetcodeApiCrawlerWithLogin apiCrawler;
    @SpyBean
    private ProblemService problemService;

    @Test
    void updateProblemsTest() {
        User user = new User("nokchax", "test", "greentea");
        given(userService.findRandomUser()).willReturn(user);
        given(apiCrawler.crawlProblems(user)).willReturn(testProblemInfo());

        List<Problem> problems = problemRepository.findAll();
        entityManager.clear();

        beforeQuery();
        List<ProblemDto> problemDtos = updateService.updateProblems();
        afterQuery();

        List<Problem> problemsAfterUpdate = problemRepository.findAll();

        assertThat(problemDtos.size()).isEqualTo(2);
        assertThat(problemsAfterUpdate.size()).isEqualTo(problems.size() + 1);
        System.out.println("Before problems size : " + problems.size());
        System.out.println("After problems size : " + problemsAfterUpdate.size());
        problemDtos.forEach(System.out::println);

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