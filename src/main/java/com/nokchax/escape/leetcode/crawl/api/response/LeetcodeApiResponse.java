package com.nokchax.escape.leetcode.crawl.api.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nokchax.escape.leetcode.crawl.page.response.CrawledUserInfo;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeetcodeApiResponse {
    @JsonProperty("num_total")
    private Long numTotal;
    @JsonProperty("stat_status_pairs")
    private List<StatStatusPair> statStatusPairs;

    public CrawledUserInfo toCrawledUserInfo() {
        return null;
    }
}
