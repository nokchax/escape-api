package com.nokchax.escape.user.repository;

import com.nokchax.escape.JpaTest;
import com.nokchax.escape.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserRepositoryTest extends JpaTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    @ParameterizedTest
    @CsvSource(value = {"nokchax1, 1", "all, 15"})
    @DisplayName("사용자 이름에 맞는 사용자 엔티티 조회 테스트(all인 경우 모든 사용자를 리턴)")
    void findByUserIdTest(String userId, int expectedSize) {
        beforeQuery();
        List<User> users = userRepository.findByUserId(userId);
        afterQuery();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(expectedSize);

        showResult();
        users.forEach(System.out::println);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("사용자 이름에 맞는 사용자 엔티티 조회 테스트(아이디가 null이거나 비어있는 경우)")
    void findByUserIdTest_WithNullOrEmptyString(String userId) {
        beforeQuery();
        List<User> users = userRepository.findByUserId(userId);
        afterQuery();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(0);

        showResult();
        users.forEach(System.out::println);
    }

    @Test
    @DisplayName("텔레그램 아이디로 사용자 찾기 테스트")
    void findByTelegramIdTest() {
        beforeQuery();
        User user = userRepository.findByTelegramId("telegram1")
                .orElseThrow(NullPointerException::new);
        afterQuery();

        assertThat(user).isNotNull();
        assertThat(user.getId()).isEqualTo("nokchax1");
        assertThat(user.getSolvedProblem().size()).isNotZero();

        showResult();
        System.out.println(user);
        user.getSolvedProblem().forEach(System.out::println);
    }

    @Test
    @DisplayName("유저 아이디로 찾는데 푼 문제가 없는경우 테스트")
    void findByUserIdTest_NoSolvedProblemUser() {
        saveNewUser();

        beforeQuery();
        User user = userRepository.findById("no solved problem")
                .orElseThrow(NullPointerException::new);
        afterQuery();

        checkNotSolvedProblemUser(user);
    }

    @Test
    @DisplayName("텔레그램 아이디로 사용자 찾기(푼 문제가 없을경우)")
    @Transactional
    void findByTelegramIdTest_NoSolvedProblemUser() {
        saveNewUser();

        beforeQuery();
        User user = userRepository.findByTelegramId("telegram")
                .orElseThrow(NullPointerException::new);
        afterQuery();

        checkNotSolvedProblemUser(user);
    }

    private void checkNotSolvedProblemUser(User user) {
        assertThat(user).isNotNull();
        assertThat(user.getTelegramId()).isEqualTo("telegram");
        assertThat(user.getSolvedProblemCount()).isZero();
        assertThat(user.getSolvedProblem().size()).isZero(); // 여기

        showResult();
        System.out.println(user);
        user.getSolvedProblem().forEach(System.out::println);
    }

    private void saveNewUser() {
        User user = User.builder()
                .id("no solved problem")
                .name("잠만보")
                .telegramId("telegram")
//                .solvedProblem(new HashSet<>())
                .build();

        beforeQuery();
        userRepository.saveAndFlush(user);
        afterQuery();

        entityManager.clear(); //왜 OneToMany 연관관계 컬랙션 객체가 null인가 했네...
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = ("not exist id value"))
    @DisplayName("DB상에는 존재하지 않는 텔레그램 아이디로 사용자 찾기 테스트")
    void findByTelegramIdTestWithExceptionExpected(String telegramId) {
        beforeQuery();
        assertThatThrownBy(() -> userRepository.findByTelegramId(telegramId).orElseThrow(NullPointerException::new))
                .isInstanceOf(NullPointerException.class);
        afterQuery();
    }

    @Test
    @DisplayName("사용자 중에서 최신 미션을 수행중이지 않은 사용자 리턴 테스트")
    void findUsersNotInLatestMission() {
        beforeQuery();
        List<User> usersNotInLatestMission = userRepository.findUsersNotInLatestMission();
        afterQuery();

        assertThat(usersNotInLatestMission).isNotNull();
        assertThat(usersNotInLatestMission.size()).isOne();

        usersNotInLatestMission.forEach(System.out::println);
    }

}