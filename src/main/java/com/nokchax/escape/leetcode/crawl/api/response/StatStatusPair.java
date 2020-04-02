package com.nokchax.escape.leetcode.crawl.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatStatusPair {
    private Stat stat;
    private String status;
    private int level; // difficulty 1 easy, 2 medium, 3 hard

    public boolean solved() {
        return "ac".equalsIgnoreCase(status);
    }
}
