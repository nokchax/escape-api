package com.nokchax.escape.leetcode.crawl.page.response;

import com.nokchax.escape.leetcode.crawl.page.util.ExtractUtil;
import com.nokchax.escape.leetcode.crawl.page.util.time.Times;
import lombok.Data;
import org.jsoup.nodes.Element;

@Data
public class SubmissionInfo {
    public static final String TAG = "tag";
    public static final String CLASS = "class";
    private boolean accepted;
    private String problemTitle;
    private String submissionTime;

    public SubmissionInfo(Element problemElement) {
        String acceptedString = ExtractUtil.extractExtractToString(problemElement, TAG, "span");
        this.problemTitle = ExtractUtil.extractExtractToString(problemElement, TAG, "b");
        this.submissionTime = ExtractUtil.extractExtractToString(problemElement, CLASS, "text-muted");
        this.accepted = "Accepted".equalsIgnoreCase(acceptedString);
    }

    public ProblemSolveInfo toSolveInfo() {

        return ProblemSolveInfo.builder()
                .problemTitle(this.problemTitle)
                .solvedDate(Times.of(submissionTime).calculate())
                .build();
    }
}

