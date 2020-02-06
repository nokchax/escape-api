//package com.nokchax.escape.config;

//import com.nokchax.escape.config.properties.Admin;
//import com.nokchax.escape.config.properties.Master;
//import com.nokchax.escape.config.properties.TelegramBot;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.assertj.core.api.Assertions.assertThat;

//jupiter에서 아래 annotaion만으로 충분
//@SpringBootTest
//@ActiveProfiles("dev")
//public class AppPropertiesTest {
//    @Autowired
//    private AppProperties properties;
//
//    @Test
//    @DisplayName("프로파일 및 프로퍼티 로드 테스트")
//    public void configurationPropertiesTest() {
//        TelegramBot telegramBot = properties.getTelegramBot();
//        Master master = properties.getMaster();
//        Admin admin = properties.getAdmin();
//
//        assertThat(telegramBot.getName()).isEqualTo("peng");
//        assertThat(telegramBot.getToken()).isEqualTo("giantPeng");
//
//        assertThat(master.getId()).isEqualTo("nokchax");
//        assertThat(master.getName()).isEqualTo("광");
//        assertThat(master.getPw()).isEqualTo("123");
//
//        assertThat(admin.getIds()).contains("n","o","k","c","h","a","x");
//
//        System.out.println(properties);
//    }
//
//}