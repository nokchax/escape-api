package com.nokchax.escape;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class EscapeApplication {
    // TODO: 2019-12-27 refactoring and construct dev environment (db, crawling)
    public static void main(String[] args) {
        // to init telegram bot
        ApiContextInitializer.init();

        new SpringApplicationBuilder()
                .sources(EscapeApplication.class)
                .properties("spring.config.additional-location=file:/data/etc/escape/token.yml")
                .run(args);
    }

    // TODO: 2020-03-18 DB 테이블 정리할겸 한번 그려보기 -> 개선사항 찾기
    /**
     * table list
     *
     * users
     *  id
     *  name
     *  password
     *  solve_question_count
     *  추가할 것 : telegram id??? /u만 했을때 알아서 자기 계정을 업데이트 하기 위함.
     *
     * problem
     *  question_id
     *  difficulty
     *  hide
     *  title
     *  title_slug
     *  front_end_question_id
     *
     * user history
     *  date_time
     *  user_id
     *  description
     *  point
     *
     * user problem
     *  problem_id
     *  user_id
     *  solved_time
     *  추가할 것 : updated_time (나중에 solved_time이 잘못 업데이트 됐을때를 대비) or envers 도입???
     *
     * task
     *  id
     *  duration_type
     *  start_date_time
     *  end_date_time
     *  goal_score
     *
     * task participants
     *  task_id
     *  user_id
     *  easy
     *  hard
     *  medium
     *  score
     *
     * 요구사항 재정리
     *  문제풀이 기간은 변경될 수 있다.
     *  문제 풀이 기간이 기존과 달라졌다고 하더라도, 이전 것들이 영향을 받아서는 안된다.
     *  사용자는 참가가 자유롭다. (참가 안하는 사용자도 있어야함)
     *
     */
}
