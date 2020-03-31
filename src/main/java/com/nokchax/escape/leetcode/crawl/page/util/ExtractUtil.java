package com.nokchax.escape.leetcode.crawl.page.util;

import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import com.nokchax.escape.leetcode.crawl.page.response.SubmissionInfo;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Set;
import java.util.stream.Collectors;

public class ExtractUtil {

    /** 제출한 문제풀이 리스트 중에서 통과한 문제만 리턴 */
    public static Set<ProblemSolveInfo> extractSolvedProblems(Document document) {
        Elements problems = document.getElementsByAttributeValueStarting("href", "/problems");

        return problems.stream()
                .map(SubmissionInfo::new)
                .filter(SubmissionInfo::isAccepted)
                .map(SubmissionInfo::toSolveInfo)
                .collect(Collectors.toSet());
    }

    /** document에서 지금까지 푼 모든 문제 수를 리턴 */
    public static int extractSolvedProblemCount(Document document) {
        Elements solvedProblemsElement = extractProblemElements(document);

        if(notSolvedProblems(solvedProblemsElement)) {
            return 0;
        }

        String[] inputs = solvedProblemsElement.get(0)
                .text()
                .trim()
                .split("/");

        return Integer.parseInt(inputs[0].trim());
    }

    /** 문제를 풀었는지 풀지 않았는지 확인 */
    private static boolean notSolvedProblems(Elements solvedProblemsElement) {

        return !solvedProblemsElement.isEmpty()
                && !solvedProblemsElement.get(0).text().trim().isEmpty();
    }

    /** document로 부터 problem elements를 추출 */
    private static Elements extractProblemElements(Document document) {

        return document.getElementsByClass("fa-question")
                .parents()
                .get(0)
                .getElementsByClass("progress-bar-success");
    }


    /** element내의 text를 찾아 추출 */
    public static String extractExtractToString(Element element, String target, String targetId) {
        Elements elements;

        switch (target) {
            case "tag":
                elements = element.getElementsByTag(targetId);
                break;

            case "class":
                elements = element.getElementsByClass(targetId);
                break;

            default:
                throw new IllegalArgumentException("Unknown target : " + target + "\n" +
                        " Maybe leetcode user's page has been changed, check it"
                );
        }

        return elements.get(0)
                .text()
                .trim();
    }
}
