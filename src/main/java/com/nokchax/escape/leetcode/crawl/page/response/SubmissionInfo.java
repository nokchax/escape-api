package com.nokchax.escape.leetcode.crawl.page.response;

import com.nokchax.escape.leetcode.crawl.page.util.ExtractUtil;
import com.zum.escape.api.util.DateStringToLocalDateTimeConverter;
import lombok.Data;
import org.jsoup.nodes.Element;

@Data
public class SubmissionInfo {
    private boolean accepted;
    private String problemTitle;
    private String submissionTime;

    public SubmissionInfo(Element problemElement) {
        String acceptedString = ExtractUtil.extractExtractToString(problemElement, "tag", "span");
        this.problemTitle = ExtractUtil.extractExtractToString(problemElement, "tag", "b");
        this.submissionTime = ExtractUtil.extractExtractToString(problemElement, "class", "text-muted");
        this.accepted = "Accepted".equalsIgnoreCase(acceptedString);
    }

    public ProblemSolveInfo toSolveInfo() {

        return ProblemSolveInfo.builder()
                .problemTitle(this.problemTitle)
                .solvedDate(DateStringToLocalDateTimeConverter.convert(submissionTime))
                .build();
    }
}

