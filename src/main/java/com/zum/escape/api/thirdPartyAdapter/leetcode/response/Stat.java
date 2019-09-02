package com.zum.escape.api.thirdPartyAdapter.leetcode.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stat {
    @JsonProperty("question_id")
    private Long questionId;
    @JsonProperty("frontend_question_id")
    private Long frontendQuestionId;
    @JsonProperty("question__title")
    private String questionTitle;
    @JsonProperty("question__title_slug")
    private String questionTitleSlug;
    @JsonProperty("question_hide")
    private boolean questionHide;
}
