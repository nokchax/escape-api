package com.nokchax.escape.leetcode.crawl.page.util;

import com.nokchax.escape.leetcode.crawl.page.response.ProblemSolveInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class ExtractUtilTest {
    public static final String PAGE_RESPONSE;
    private Document parsedDocument = Jsoup.parse(PAGE_RESPONSE);

    @Test
    @DisplayName("푼 문제 리스트를 제대로 추출하는지 테스트")
    void extractSolvedProblemTest() {
        Set<ProblemSolveInfo> problemSolveInfos = ExtractUtil.extractSolvedProblems(parsedDocument);

        assertThat(problemSolveInfos).isNotNull();
        assertThat(problemSolveInfos.size()).isEqualTo(12);

        problemSolveInfos.forEach(System.out::println);
    }

    @Test
    @DisplayName("푼 문제수를 제대로 추출하는지 테스트")
    void extractSolveProblemCountTest() {
        assertThat(ExtractUtil.extractSolvedProblemCount(parsedDocument)).isEqualTo(125);
    }

    static {
        PAGE_RESPONSE = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <title>KwnagHyun Kim - Profile - LeetCode</title>\n" +
                "    <meta property=\"og:title\" content=\"KwnagHyun Kim\" />\n" +
                "  </head>\n" +
                "  <body>\n" +
                "  <div class=\"content-wrapper\">\n" +
                "    <div id=\"lc_navbar\" class=\"navbar navbar-inverse navbar-extra\"></div>\n" +
                "    <div id=\"lc_navbar_placeholder\"></div>\n" +
                "    <div id=\"base_content\">\n" +
                "      \n" +
                "  <div class=\"response-container container puiblic-profile-base\" ng-app=\"app\"\n" +
                "       ng-controller=\"PublicProfileController as pc\"\n" +
                "       ng-init=\"pc.init('nokchax', '194', '0', '0', '0',\n" +
                "                        '106', '100001', '100000', '2', '1',\n" +
                "                        '2,', null,\n" +
                "                        {&quot;percentiles&quot;: [], &quot;avg_percentile&quot;: 0, &quot;top_tags&quot;: []}, [&quot;Weekly Contest 2&quot;, &quot;Weekly Contest 3&quot;, &quot;Weekly Contest 4&quot;, &quot;Weekly Contest 5&quot;, &quot;Weekly Contest 6&quot;, &quot;Weekly Contest 7&quot;, &quot;Weekly Contest 8&quot;, &quot;Weekly Contest 9&quot;, &quot;Smarking Algorithm Contest&quot;, &quot;Smarking Algorithm Contest 2&quot;, &quot;Smarking Algorithm Contest 3&quot;, &quot;Smarking Algorithm Contest 4&quot;, &quot;Weekly Contest 10&quot;, &quot;Weekly Contest 11&quot;, &quot;Weekly Contest 12&quot;, &quot;Weekly Contest 13&quot;, &quot;Weekly Contest 14&quot;, &quot;Weekly Contest 15&quot;, &quot;Weekly Contest 16A&quot;, &quot;Weekly Contest 16B&quot;, &quot;Weekly Contest 17&quot;, &quot;Weekly Contest 18A&quot;, &quot;Weekly Contest 18B&quot;, &quot;Weekly Contest 19&quot;, &quot;Weekly Contest 20&quot;, &quot;Weekly Contest 21&quot;, &quot;Weekly Contest 22&quot;, &quot;Weekly Contest 23&quot;, &quot;Weekly Contest 24&quot;, &quot;Weekly Contest 25&quot;, &quot;Weekly Contest 26&quot;, &quot;Weekly Contest 27&quot;, &quot;Weekly Contest 28&quot;, &quot;Weekly Contest 29&quot;, &quot;Weekly Contest 30&quot;, &quot;Weekly Contest 31&quot;, &quot;Weekly Contest 32&quot;, &quot;Weekly Contest 33&quot;, &quot;Weekly Contest 34&quot;, &quot;Weekly Contest 35&quot;, &quot;Weekly Contest 36&quot;, &quot;Weekly Contest 37&quot;, &quot;Weekly Contest 38&quot;, &quot;Weekly Contest 39&quot;, &quot;Weekly Contest 40&quot;, &quot;Weekly Contest 41&quot;, &quot;Weekly Contest 42&quot;, &quot;Weekly Contest 43&quot;, &quot;Weekly Contest 44&quot;, &quot;Weekly Contest 45&quot;, &quot;Weekly Contest 46&quot;, &quot;Weekly Contest 47&quot;, &quot;Weekly Contest 48&quot;, &quot;Weekly Contest 49&quot;, &quot;Weekly Contest 50&quot;, &quot;Weekly Contest 51&quot;, &quot;Weekly Contest 52&quot;, &quot;Weekly Contest 53&quot;, &quot;Weekly Contest 54&quot;, &quot;Weekly Contest 55&quot;, &quot;Weekly Contest 56&quot;, &quot;Weekly Contest 57&quot;, &quot;Weekly Contest 58&quot;, &quot;Weekly Contest 59&quot;, &quot;Weekly Contest 60&quot;, &quot;Weekly Contest 61&quot;, &quot;Weekly Contest 62&quot;, &quot;Weekly Contest 63&quot;, &quot;Weekly Contest 64&quot;, &quot;Weekly Contest 65&quot;, &quot;Weekly Contest 66&quot;, &quot;Weekly Contest 67&quot;, &quot;Weekly Contest 68&quot;, &quot;Weekly Contest 69&quot;, &quot;Weekly Contest 70&quot;, &quot;Weekly Contest 71&quot;, &quot;Weekly Contest 72&quot;, &quot;Weekly Contest 73&quot;, &quot;Weekly Contest 74&quot;, &quot;Weekly Contest 75&quot;, &quot;Weekly Contest 76&quot;, &quot;Weekly Contest 77&quot;, &quot;Weekly Contest 78&quot;, &quot;Weekly Contest 79&quot;, &quot;Weekly Contest 80&quot;, &quot;Weekly Contest 81&quot;, &quot;Weekly Contest 82&quot;, &quot;Weekly Contest 83&quot;, &quot;Weekly Contest 84&quot;, &quot;Weekly Contest 85&quot;, &quot;Weekly Contest 86&quot;, &quot;Weekly Contest 87&quot;, &quot;Weekly Contest 88&quot;, &quot;Weekly Contest 89&quot;, &quot;Weekly Contest 90&quot;, &quot;Weekly Contest 91&quot;, &quot;Weekly Contest 92&quot;, &quot;Weekly Contest 93&quot;, &quot;Weekly Contest 94&quot;, &quot;Weekly Contest 95&quot;, &quot;Weekly Contest 96&quot;, &quot;Weekly Contest 97&quot;, &quot;Weekly Contest 98&quot;, &quot;Weekly Contest 99&quot;, &quot;Weekly Contest 100&quot;, &quot;Weekly Contest 101&quot;, &quot;Weekly Contest 102&quot;, &quot;Weekly Contest 103&quot;, &quot;Weekly Contest 104&quot;, &quot;Weekly Contest 105&quot;, &quot;Weekly Contest 106&quot;, &quot;Weekly Contest 107&quot;, &quot;Weekly Contest 108&quot;, &quot;Weekly Contest 109&quot;, &quot;Weekly Contest 110&quot;, &quot;Weekly Contest 111&quot;, &quot;Weekly Contest 112&quot;, &quot;Weekly Contest 113&quot;, &quot;Weekly Contest 114&quot;, &quot;Weekly Contest 115&quot;, &quot;Weekly Contest 116&quot;, &quot;Weekly Contest 117&quot;, &quot;Weekly Contest 118&quot;, &quot;Weekly Contest 119&quot;, &quot;Weekly Contest 120&quot;, &quot;Weekly Contest 121&quot;, &quot;Weekly Contest 122&quot;, &quot;Weekly Contest 123&quot;, &quot;Weekly Contest 124&quot;, &quot;Weekly Contest 125&quot;, &quot;Weekly Contest 126&quot;, &quot;Weekly Contest 127&quot;, &quot;Weekly Contest 128&quot;, &quot;Weekly Contest 129&quot;, &quot;Weekly Contest 130&quot;, &quot;Weekly Contest 131&quot;, &quot;Weekly Contest 132&quot;, &quot;Weekly Contest 133&quot;, &quot;Weekly Contest 134&quot;, &quot;Weekly Contest 135&quot;, &quot;Weekly Contest 136&quot;, &quot;Weekly Contest 137&quot;, &quot;Weekly Contest 138&quot;, &quot;Weekly Contest 139&quot;, &quot;Biweekly Contest 1&quot;, &quot;Biweekly Contest 2&quot;, &quot;Weekly Contest 141&quot;, &quot;Weekly Contest 142&quot;, &quot;Biweekly Contest 3&quot;, &quot;Weekly Contest 143&quot;, &quot;Weekly Contest 144&quot;, &quot;Biweekly Contest 4&quot;, &quot;Weekly Contest 145&quot;, &quot;Weekly Contest 146&quot;, &quot;Biweekly Contest 5&quot;, &quot;Weekly Contest 147&quot;, &quot;Weekly Contest 148&quot;, &quot;Biweekly Contest 6&quot;, &quot;Weekly Contest 149&quot;, &quot;Weekly Contest 150&quot;, &quot;Biweekly Contest 7&quot;, &quot;Weekly Contest 151&quot;, &quot;Weekly Contest 152&quot;, &quot;Biweekly Contest 8&quot;, &quot;Weekly Contest 153&quot;, &quot;Weekly Contest 154&quot;, &quot;Biweekly Contest 9&quot;, &quot;Weekly Contest 155&quot;, &quot;Weekly Contest 156&quot;, &quot;Biweekly Contest 10&quot;, &quot;Weekly Contest 157&quot;, &quot;Weekly Contest 158&quot;, &quot;Biweekly Contest 11&quot;, &quot;Weekly Contest 159&quot;, &quot;Weekly Contest 160&quot;, &quot;Biweekly Contest 12&quot;, &quot;Weekly Contest 161&quot;, &quot;Weekly Contest 162&quot;, &quot;Biweekly Contest 13&quot;, &quot;Weekly Contest 163&quot;, &quot;Weekly Contest 164&quot;, &quot;Biweekly Contest 14&quot;, &quot;Weekly Contest 165&quot;, &quot;Weekly Contest 166&quot;, &quot;Biweekly Contest 15&quot;, &quot;Weekly Contest 167&quot;, &quot;Weekly Contest 168&quot;, &quot;Biweekly Contest 16&quot;, &quot;Weekly Contest 169&quot;, &quot;Weekly Contest 170&quot;, &quot;Biweekly Contest 17&quot;, &quot;Weekly Contest 171&quot;, &quot;Weekly Contest 172&quot;, &quot;Biweekly Contest 18&quot;, &quot;Weekly Contest 173&quot;, &quot;Weekly Contest 174&quot;, &quot;Biweekly Contest 19&quot;, &quot;Weekly Contest 176&quot;, &quot;Biweekly Contest 20&quot;, &quot;Weekly Contest 177&quot;, &quot;Weekly Contest 178&quot;, &quot;Biweekly Contest 21&quot;, &quot;Weekly Contest 179&quot;, &quot;Weekly Contest 180&quot;, &quot;Biweekly Contest 22&quot;, &quot;Weekly Contest 181&quot;, &quot;Weekly Contest 182&quot;, &quot;Biweekly Contest 23&quot;, &quot;Weekly Contest 183&quot;])\" ng-cloak>\n" +
                "    <div class=\"row\">\n" +
                "      <div class=\"col-sm-5 col-md-4\">\n" +
                "        <div class=\"panel panel-default\">\n" +
                "          <div class=\"panel-heading\">\n" +
                "            <h3 class=\"panel-title\" style=\"display: flex; justify-content: space-between;\">\n" +
                "              Basic profile\n" +
                "              <div id=\"invite\" data-user-slug=\"nokchax\"></div>\n" +
                "              \n" +
                "            </h3>\n" +
                "          </div>\n" +
                "          <div class=\"panel-body\">\n" +
                "            <img style=\"float:left; margin-right: 15px; margin-top: 5px;\"\n" +
                "                 width=\"80px\"\n" +
                "                 src=\"https://www.gravatar.com/avatar/d9eebfb017b0b2d0420723cd044cfa9e.png?s=200\"\n" +
                "                 class=\"img-rounded\"\n" +
                "                 alt=\"user avatar\" />\n" +
                "            <h4 class=\"realname\" title=\"KwnagHyun Kim\">\n" +
                "              KwnagHyun Kim\n" +
                "            </h4>\n" +
                "            <p class=\"username\" title=\"nokchax\">\n" +
                "              nokchax\n" +
                "              \n" +
                "            </p>\n" +
                "            <span style=\"cursor: pointer\" class=\"ranking\" data-toggle=\"popover\" data-placement=\"top\" data-trigger=\"hover\"\n" +
                "                  data-content=\"Ranking: <b>{[{ pc.ranking }]}</b>\">\n" +
                "              <i ng-repeat=\"i in pc.getNumber(pc.full_star_count) track by $index\" ng-style=\"{'color': colors.yellow}\" class=\"fa fa-lg fa-star\">&nbsp;</i>\n" +
                "              <i ng-repeat=\"i in pc.getNumber(pc.half_star_count) track by $index\" ng-style=\"{'color': colors.yellow}\" class=\"fa fa-lg fa-star-half-o\">&nbsp;</i>\n" +
                "              <i ng-repeat=\"i in pc.getNumber(pc.empty_star_count) track by $index\" ng-style=\"{'color': colors.yellow}\" class=\"fa fa-lg fa-star-o\">&nbsp;</i>\n" +
                "            </span>\n" +
                "\n" +
                "            <div class=\"social-icons profile-social-icons\">\n" +
                "              \n" +
                "              \n" +
                "                <a id=\"linkedin_oauth2-connect\"\n" +
                "                   class=\"hide fa-stack fa-lg linkedin_oauth2-color social-login-btn\">\n" +
                "                  <i class=\"fa fa-circle fa-stack-2x\"></i>\n" +
                "                  \n" +
                "                    <i class=\"fa fa-linkedin fa-stack-1x fa-inverse\"></i>\n" +
                "                  \n" +
                "                </a>\n" +
                "                <a id=\"linkedin_oauth2-disconnect\"\n" +
                "                   style=\"color: grey; opacity: 0.5;\" class=\"fa-stack fa-lg linkedin_oauth2-color social-login-btn\">\n" +
                "                  <i class=\"fa fa-circle fa-stack-2x\"></i>\n" +
                "                  \n" +
                "                    <i class=\"fa fa-linkedin fa-stack-1x fa-inverse\"></i>\n" +
                "                  \n" +
                "                </a>\n" +
                "              \n" +
                "                <a id=\"google-connect\"\n" +
                "                   class=\"hide fa-stack fa-lg google-color social-login-btn\">\n" +
                "                  <i class=\"fa fa-circle fa-stack-2x\"></i>\n" +
                "                  \n" +
                "                    <i class=\"fa fa-google fa-stack-1x fa-inverse\"></i>\n" +
                "                  \n" +
                "                </a>\n" +
                "                <a id=\"google-disconnect\"\n" +
                "                   style=\"color: grey; opacity: 0.5;\" class=\"fa-stack fa-lg google-color social-login-btn\">\n" +
                "                  <i class=\"fa fa-circle fa-stack-2x\"></i>\n" +
                "                  \n" +
                "                    <i class=\"fa fa-google fa-stack-1x fa-inverse\"></i>\n" +
                "                  \n" +
                "                </a>\n" +
                "              \n" +
                "                <a id=\"github-connect\"\n" +
                "                   class=\"hide fa-stack fa-lg github-color social-login-btn\">\n" +
                "                  <i class=\"fa fa-circle fa-stack-2x\"></i>\n" +
                "                  \n" +
                "                    <i class=\"fa fa-github fa-stack-1x fa-inverse\"></i>\n" +
                "                  \n" +
                "                </a>\n" +
                "                <a id=\"github-disconnect\"\n" +
                "                   style=\"color: grey; opacity: 0.5;\" class=\"fa-stack fa-lg github-color social-login-btn\">\n" +
                "                  <i class=\"fa fa-circle fa-stack-2x\"></i>\n" +
                "                  \n" +
                "                    <i class=\"fa fa-github fa-stack-1x fa-inverse\"></i>\n" +
                "                  \n" +
                "                </a>\n" +
                "              \n" +
                "                <a id=\"facebook-connect\"\n" +
                "                   class=\"hide fa-stack fa-lg facebook-color social-login-btn\">\n" +
                "                  <i class=\"fa fa-circle fa-stack-2x\"></i>\n" +
                "                  \n" +
                "                    <i class=\"fa fa-facebook fa-stack-1x fa-inverse\"></i>\n" +
                "                  \n" +
                "                </a>\n" +
                "                <a id=\"facebook-disconnect\"\n" +
                "                   style=\"color: grey; opacity: 0.5;\" class=\"fa-stack fa-lg facebook-color social-login-btn\">\n" +
                "                  <i class=\"fa fa-circle fa-stack-2x\"></i>\n" +
                "                  \n" +
                "                    <i class=\"fa fa-facebook fa-stack-1x fa-inverse\"></i>\n" +
                "                  \n" +
                "                </a>\n" +
                "              \n" +
                "              <script>\n" +
                "                \n" +
                "              </script>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "          <ul class=\"list-group\">\n" +
                "            \n" +
                "            \n" +
                "              \n" +
                "            \n" +
                "            \n" +
                "            \n" +
                "          </ul>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"panel panel-default\">\n" +
                "          <div class=\"panel-heading\">\n" +
                "            <h3 class=\"panel-title\">\n" +
                "              Contest\n" +
                "            </h3>\n" +
                "          </div>\n" +
                "          <ul class=\"list-group\">\n" +
                "            <li class=\"list-group-item\">\n" +
                "              <span class=\"badge progress-bar-success\">\n" +
                "                0\n" +
                "              </span>\n" +
                "              <i class=\"fa fa-cubes fa-fw\"></i>&nbsp;Finished Contests\n" +
                "            </li>\n" +
                "            \n" +
                "          </ul>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"panel panel-default\">\n" +
                "          <div class=\"panel-heading\">\n" +
                "            <h3 class=\"panel-title\">Progress</h3>\n" +
                "          </div>\n" +
                "          <ul class=\"list-group\" ng-if=\"ac.selectedMiDist !== null\">\n" +
                "            <li class=\"list-group-item\">\n" +
                "              <span class=\"badge progress-bar-success\">\n" +
                "                125 / 1411\n" +
                "              </span>\n" +
                "              <i class=\"fa fa-question fa-lg fa-fw\"></i>&nbsp;\n" +
                "                Solved Question\n" +
                "            </li>\n" +
                "            <li class=\"list-group-item\">\n" +
                "              <span class=\"badge progress-bar-success\">\n" +
                "                194 / 300\n" +
                "              </span>\n" +
                "              <i class=\"fa fa-cog fa-lg fa-fw\"></i>&nbsp;\n" +
                "                Accepted Submission\n" +
                "            </li>\n" +
                "            <li class=\"list-group-item\">\n" +
                "              <span class=\"badge progress-bar-info\">\n" +
                "                64.7 %\n" +
                "              </span>\n" +
                "              <i class=\"fa fa-check fa-lg fa-fw\"></i>&nbsp;\n" +
                "              Acceptance Rate\n" +
                "            </li>\n" +
                "          </ul>\n" +
                "          <div class=\"panel-body\">\n" +
                "            <nvd3 options=\"pc.progress_options\" data=\"pc.progress_data\"></nvd3>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"panel panel-default\">\n" +
                "          <div class=\"panel-heading\">\n" +
                "            <h3 class=\"panel-title\">Contribution</h3>\n" +
                "          </div>\n" +
                "          <ul class=\"list-group\">\n" +
                "            <li class=\"list-group-item\">\n" +
                "              <span class=\"badge progress-bar-success\">\n" +
                "                306\n" +
                "              </span>\n" +
                "              <i class=\"fa fa-gift fa-fw\"></i>&nbsp;Points\n" +
                "            </li>\n" +
                "            <li class=\"list-group-item\">\n" +
                "              <span class=\"badge progress-bar-success\">\n" +
                "                0\n" +
                "              </span>\n" +
                "              <i class=\"fa fa-question-circle fa-fw\"></i>&nbsp;Problems\n" +
                "            </li>\n" +
                "            <li class=\"list-group-item\">\n" +
                "              <span class=\"badge progress-bar-success\">\n" +
                "                0\n" +
                "              </span>\n" +
                "              <i class=\"fa fa-gift fa-fw\"></i>&nbsp;Test Cases\n" +
                "            </li>\n" +
                "          </ul>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "\n" +
                "      <div class=\"col-sm-7 col-md-8\">\n" +
                "        <div class=\"panel panel-default\">\n" +
                "          <div class=\"panel-heading\">\n" +
                "            <h3 class=\"panel-title\" ng-bind=\"heatmap_title\"></h3>\n" +
                "          </div>\n" +
                "          <div class=\"panel-body col-centered heatmap-panel\">\n" +
                "            <div id=\"cal-heatmap\"></div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "\n" +
                "        \n" +
                "\n" +
                "        \n" +
                "\n" +
                "        <div class=\"panel panel-default\" ng-if=\"pc.miprogress_data\">\n" +
                "          <div class=\"panel-heading\">\n" +
                "            <h3 class=\"panel-title\">\n" +
                "              Mock Interview Progress\n" +
                "            </h3>\n" +
                "          </div>\n" +
                "          <div class=\"panel-body\">\n" +
                "            <nvd3 options=\"pc.miprogress_options\" data=\"pc.miprogress_data\"></nvd3>\n" +
                "            <br />\n" +
                "            <div ng-if=\"pc.mi_top_tags\">\n" +
                "              <b>Top Skills: </b>{[{pc.mi_top_tags}]}\n" +
                "            </div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "\n" +
                "        \n" +
                "\n" +
                "        \n" +
                "        <div class=\"panel panel-default\">\n" +
                "          <div class=\"panel-heading\">\n" +
                "            <h3 class=\"panel-title\">Most recent submissions</h3>\n" +
                "          </div>\n" +
                "          <ul class=\"list-group\">\n" +
                "            \n" +
                "              <a href=\"/problems/single-number/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Single Number</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  23 minutes ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/single-number/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Single Number</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  1 week, 3 days ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/longest-chunked-palindrome-decomposition/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Longest Chunked Palindrome Decomposition</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  2 weeks, 5 days ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/longest-chunked-palindrome-decomposition/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('11')\">\n" +
                "                  Wrong Answer\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Longest Chunked Palindrome Decomposition</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  2 weeks, 5 days ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/count-all-valid-pickup-and-delivery-options/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Count All Valid Pickup and Delivery Options</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  3 weeks, 6 days ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/count-all-valid-pickup-and-delivery-options/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('20')\">\n" +
                "                  Compile Error\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Count All Valid Pickup and Delivery Options</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  3 weeks, 6 days ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/count-all-valid-pickup-and-delivery-options/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('11')\">\n" +
                "                  Wrong Answer\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Count All Valid Pickup and Delivery Options</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  3 weeks, 6 days ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/minimum-falling-path-sum-ii/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Minimum Falling Path Sum II</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  1 month ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/minimum-falling-path-sum-ii/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('11')\">\n" +
                "                  Wrong Answer\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Minimum Falling Path Sum II</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  1 month ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/number-of-submatrices-that-sum-to-target/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Number of Submatrices That Sum to Target</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  1 month, 1 week ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/number-of-submatrices-that-sum-to-target/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('11')\">\n" +
                "                  Wrong Answer\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Number of Submatrices That Sum to Target</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  1 month, 1 week ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/brace-expansion-ii/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Brace Expansion II</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  1 month, 2 weeks ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/n-queens-ii/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>N-Queens II</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  1 month, 3 weeks ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/maximum-candies-you-can-get-from-boxes/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Maximum Candies You Can Get from Boxes</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  2 months ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/my-calendar-iii/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>My Calendar III</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  2 months, 1 week ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/n-queens/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>N-Queens</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  2 months, 2 weeks ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/n-queens/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>N-Queens</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  2 months, 2 weeks ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/swim-in-rising-water/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Swim in Rising Water</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  2 months, 3 weeks ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/maximum-frequency-stack/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('10')\">\n" +
                "                  Accepted\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Maximum Frequency Stack</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  3 months ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "              <a href=\"/problems/maximum-frequency-stack/\"\n" +
                "                 class=\"list-group-item\">\n" +
                "                <span class=\"badge\" ng-class=\"getStatus('11')\">\n" +
                "                  Wrong Answer\n" +
                "                </span>\n" +
                "                <span class=\"badge progress-bar-info\">\n" +
                "                  java\n" +
                "                </span>\n" +
                "                <b>Maximum Frequency Stack</b> &nbsp;\n" +
                "                <span class=\"text-muted\">\n" +
                "                  3 months ago\n" +
                "                </span>\n" +
                "              </a>\n" +
                "            \n" +
                "          </ul>\n" +
                "        </div>\n" +
                "        \n" +
                "\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "  </div>\n" +
                "\n" +
                "  \n" +
                "  <footer class=\"site-footer\" id=\"lc-footer\">\n" +
                "    <div class=\"container\">\n" +
                "      <hr>\n" +
                "      <div class=\"row\">\n" +
                "        \n" +
                "        <div class=\"col-sm-4 copyright\">\n" +
                "          <span>Copyright © 2020 LeetCode</span>\n" +
                "        </div>\n" +
                "        \n" +
                "        <div class=\"text-right col-sm-8 region-us\">\n" +
                "          <div class=\"links\">\n" +
                "            <a href=\"/support/\">Help Center</a>\n" +
                "            &nbsp;|&nbsp;\n" +
                "            \n" +
                "              <span class=\"hidden-xs hidden-sm\" id=\"Join_LeetCode\">\n" +
                "                <a href=\"/jobs/\">Jobs</a>\n" +
                "                &nbsp;|&nbsp;\n" +
                "              </span>\n" +
                "            \n" +
                "            \n" +
                "            <span class=\"hidden-xs hidden-sm\" id=\"Join_LeetCode\">\n" +
                "              <a href=\"/bugbounty/\">Bug Bounty</a>\n" +
                "              &nbsp;|&nbsp;\n" +
                "            </span>\n" +
                "            \n" +
                "            \n" +
                "            <a href=\"/terms/\">Terms</a>\n" +
                "            &nbsp;|&nbsp;\n" +
                "            <a href=\"/privacy/\">Privacy<span class=\"hide-too-small\">  Policy</span></a>\n" +
                "            \n" +
                "              <span class=\"region-base\">\n" +
                "                \n" +
                "                  <span class=\"hidden-xs space\">&nbsp; &nbsp; </span>\n" +
                "                  <br class=\"visible-xs newline\"/>\n" +
                "                  <a href=\"/region/\" class=\"choose-region us\">\n" +
                "                    <img src=\"/static/images/region/us.svg\" height=\"14\"/>\n" +
                "                    <span class=\"hidden-md\">United States</span>\n" +
                "                  </a>\n" +
                "                \n" +
                "              </span>\n" +
                "            \n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "      \n" +
                "    </div>\n" +
                "  </footer>\n" +
                "\n" +
                "  <div class=\"modal fade simple-modal\" id=\"supportModal\" role=\"dialog\">\n" +
                "    <div class=\"modal-center\">\n" +
                "      <div class=\"modal-dialog\">\n" +
                "        <div class=\"modal-content\">\n" +
                "          <div class=\"modal-header\">\n" +
                "            <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">&times;</button>\n" +
                "            <span class=\"text-lg text-300\">&nbsp;How can we help you?</span>\n" +
                "          </div>\n" +
                "\n" +
                "          <div class=\"modal-body\">\n" +
                "            <div class=\"row text-center\">\n" +
                "              <div class=\"col-sm-4\">\n" +
                "                <a\n" +
                "                  class=\"support-module btn btn-default\"\n" +
                "                  href=\"mailto:billing@leetcode.com?subject=Billing%20Issue&body=Name:%0D%0A%0D%0AUsername:%0D%0A%0D%0AMessage:%0D%0A%0D%0A\"\n" +
                "                  target=\"_blank\"\n" +
                "                >\n" +
                "                  <i class=\"fa fa-credit-card text-xl\" aria-hidden=\"true\"></i>\n" +
                "                  <br/><span class=\"text\">Billing &amp; Account</span>\n" +
                "                </a>\n" +
                "              </div>\n" +
                "              <div class=\"col-sm-4\">\n" +
                "                <a\n" +
                "                  class=\"support-module btn btn-default\"\n" +
                "                  href=\"mailto:support@leetcode.com?subject=General%20Support&body=Name:%0D%0A%0D%0AUsername:%0D%0A%0D%0AMessage:%0D%0A%0D%0A\"\n" +
                "                  target=\"_blank\"\n" +
                "                >\n" +
                "                  <i class=\"fa fa-life-ring text-xl\" aria-hidden=\"true\"></i>\n" +
                "                  <br/><span class=\"text\">General Support</span>\n" +
                "                </a>\n" +
                "              </div>\n" +
                "              <div class=\"col-sm-4\">\n" +
                "                <a\n" +
                "                  class=\"support-module btn btn-default\"\n" +
                "                  href=\"mailto:feedback@leetcode.com?subject=Other%20Inquiries&body=Name:%0D%0A%0D%0AUsername:%0D%0A%0D%0AMessage:%0D%0A%0D%0A\"\n" +
                "                  target=\"_blank\"\n" +
                "                >\n" +
                "                  <i class=\"fa fa-question-circle text-xl\" aria-hidden=\"true\"></i>\n" +
                "                  <br/><span class=\"text\">Other Inquiries</span>\n" +
                "                </a>\n" +
                "              </div>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "  </body>\n" +
                "</html>";
    }
}