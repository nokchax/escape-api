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
     *
     * task
     *  id
     *  duration_type
     *  end_date_time
     *  goal_score
     *  start_date_time
     *
     * task participants
     *  task_id
     *  user_id
     *  easy
     *  hard
     *  medium
     *  score
     *
     */
}
