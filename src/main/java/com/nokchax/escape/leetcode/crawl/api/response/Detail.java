package com.nokchax.escape.leetcode.crawl.api.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Detail {
    @JsonProperty("question_id")
    private Long problemId;
    @JsonProperty("frontend_question_id")
    private Long frontendProblemId;
    @JsonProperty("question__title")
    private String problemTitle;
    @JsonProperty("question__title_slug")
    private String problemTitleSlug;
    @JsonProperty("question_hide")
    private boolean problemHide;
}
