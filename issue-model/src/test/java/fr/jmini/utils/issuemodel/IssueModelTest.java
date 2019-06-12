package fr.jmini.utils.issuemodel;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.parser.JsonLogParser;
import edu.hm.hafner.analysis.parser.JsonParser;

public class IssueModelTest {

    @Test
    void testPreconditions() throws Exception {
        checkIssue1(createIssue1());
        checkIssue2(createIssue2());
        checkIssue3(createIssue3());
        checkIssue4(createIssue4(), false);
    }

    @Test
    void testJsonSerializer() throws Exception {
        Gson gson = new Gson();

        Issue issue1 = createIssue1();
        String json1 = gson.toJson(issue1);
        assertThat(json1).isEqualTo("{"
                + "\"fileName\":\"test.txt\","
                + "\"lineStart\":190,"
                + "\"lineEnd\":195}");

        Issue issue2 = createIssue2();
        String json2 = gson.toJson(issue2);
        assertThat(json2).isEqualTo("{"
                + "\"fileName\":\"file.txt\","
                + "\"lineRanges\":[{\"start\":10,\"end\":10},{\"start\":20,\"end\":20}]}");

        Issue issue3 = createIssue3();
        String json3 = gson.toJson(issue3);
        assertThat(json3).isEqualTo("{"
                + "\"fileName\":\"example.txt\","
                + "\"lineStart\":10,"
                + "\"severity\":\"HIGH\"}");

        Issue issue4 = createIssue4();
        String json4 = gson.toJson(issue4);
        assertThat(json4).isEqualTo("{"
                + "\"fileName\":\"fn.txt\","
                + "\"directory\":\"d\","
                + "\"lineStart\":10,"
                + "\"lineEnd\":11,"
                + "\"columnStart\":110,"
                + "\"columnEnd\":120,"
                + "\"lineRanges\":[{\"start\":10,\"end\":11}],"
                + "\"message\":\"msg\","
                + "\"category\":\"c\","
                + "\"type\":\"t\","
                + "\"severity\":\"LOW\","
                + "\"description\":\"d\","
                + "\"packageName\":\"pn\","
                + "\"moduleName\":\"mdl\","
                + "\"origin\":\"orgn\","
                + "\"reference\":\"ref\","
                + "\"fingerprint\":\"fgpt\","
                + "\"additionalProperties\":\"ap\","
                + "\"id\":\"823b92b6-98eb-41c4-83ce-b6ec1ed6f98f\"}");
    }

    @Test
    void testSerializeToFile() throws Exception {
        Path log = Paths.get("src/test/resources/example.log");
        String expected = readFile(log);

        Path tempFile = Files.createTempFile("test", "log");
        logToFile(createIssue1(), tempFile);
        logToFile(createIssue2(), tempFile);
        logToFile(createIssue3(), tempFile);
        logToFile(createIssue4(), tempFile);
        String actual = readFile(tempFile);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void testMapLineRange() throws Exception {
        edu.hm.hafner.analysis.LineRange lineRange1 = IssueMapper.toAnalysisModelLineRange(new LineRange(111, 222));
        assertThat(lineRange1.getStart()).isEqualTo(111);
        assertThat(lineRange1.getEnd()).isEqualTo(222);

        edu.hm.hafner.analysis.LineRange lineRange2 = IssueMapper.toAnalysisModelLineRange(new LineRange().start(555));
        assertThat(lineRange2.getStart()).isEqualTo(555);
        assertThat(lineRange2.getEnd()).isEqualTo(555);

        edu.hm.hafner.analysis.LineRange lineRange3 = IssueMapper.toAnalysisModelLineRange(new LineRange().end(999));
        assertThat(lineRange3.getStart()).isEqualTo(999);
        assertThat(lineRange3.getEnd()).isEqualTo(999);

        edu.hm.hafner.analysis.LineRange lineRange4 = IssueMapper.toAnalysisModelLineRange(new LineRange());
        assertThat(lineRange4.getStart()).isEqualTo(0);
        assertThat(lineRange4.getEnd()).isEqualTo(0);

        LineRange lineRange5 = IssueMapper.toLineRange(new edu.hm.hafner.analysis.LineRange(333, 444));
        assertThat(lineRange5.getStart()).isEqualTo(333);
        assertThat(lineRange5.getEnd()).isEqualTo(444);

        LineRange lineRange6 = IssueMapper.toLineRange(new edu.hm.hafner.analysis.LineRange(777));
        assertThat(lineRange6.getStart()).isEqualTo(777);
        assertThat(lineRange6.getEnd()).isEqualTo(777);
    }

    @Test
    void testMapSeverity() throws Exception {
        assertThat(IssueMapper.toAnalysisModelSeverity(Severity.ERROR)).isEqualTo(edu.hm.hafner.analysis.Severity.ERROR);
        assertThat(IssueMapper.toAnalysisModelSeverity(Severity.HIGH)).isEqualTo(edu.hm.hafner.analysis.Severity.WARNING_HIGH);
        assertThat(IssueMapper.toAnalysisModelSeverity(Severity.LOW)).isEqualTo(edu.hm.hafner.analysis.Severity.WARNING_LOW);
        assertThat(IssueMapper.toAnalysisModelSeverity(Severity.NORMAL)).isEqualTo(edu.hm.hafner.analysis.Severity.WARNING_NORMAL);

        assertThat(IssueMapper.toSeverity(edu.hm.hafner.analysis.Severity.ERROR)).isEqualTo(Severity.ERROR);
        assertThat(IssueMapper.toSeverity(edu.hm.hafner.analysis.Severity.WARNING_HIGH)).isEqualTo(Severity.HIGH);
        assertThat(IssueMapper.toSeverity(edu.hm.hafner.analysis.Severity.WARNING_LOW)).isEqualTo(Severity.LOW);
        assertThat(IssueMapper.toSeverity(edu.hm.hafner.analysis.Severity.WARNING_NORMAL)).isEqualTo(Severity.NORMAL);
    }

    @Test
    void testMapIssue() throws Exception {
        edu.hm.hafner.analysis.Issue issueIn1 = IssueMapper.toAnalysisModelIssue(createIssue1());
        checkIssue1(issueIn1);
        Issue issueOut1 = IssueMapper.toIssue(issueIn1);
        checkIssue1(issueOut1);

        edu.hm.hafner.analysis.Issue issueIn2 = IssueMapper.toAnalysisModelIssue(createIssue2());
        checkIssue2(issueIn2);
        Issue issueOut2 = IssueMapper.toIssue(issueIn2);
        checkIssue2(issueOut2);

        edu.hm.hafner.analysis.Issue issueIn3 = IssueMapper.toAnalysisModelIssue(createIssue3());
        checkIssue3(issueIn3);
        Issue issueOut3 = IssueMapper.toIssue(issueIn3);
        checkIssue3(issueOut3);

        edu.hm.hafner.analysis.Issue issueIn4 = IssueMapper.toAnalysisModelIssue(createIssue4());
        checkIssue4(issueIn4);
        Issue issueOut4 = IssueMapper.toIssue(issueIn4);
        checkIssue4(issueOut4, true);
    }

    @Test
    void testJsonLogParser() throws Exception {
        Path log = Paths.get("src/test/resources/example.log");
        String content = readFile(log);

        JsonLogParser parser = new JsonLogParser();
        Report report = parser.parse(new StringReaderFactory(content));

        assertThat(report).hasSize(4);
        assertThat(report.getErrorMessages()).isEmpty();

        checkIssue1(report.get(0));
        checkIssue2(report.get(1));
        checkIssue3(report.get(2));
        checkIssue4(report.get(3));
    }

    @Test
    void testJsonParser() throws Exception {
        IssuesHolder issueHolder = new IssuesHolder().issues(Arrays.asList(
                createIssue1(),
                createIssue2(),
                createIssue3(),
                createIssue4()));

        Gson gson = new Gson();
        String content = gson.toJson(issueHolder);

        JsonParser parser = new JsonParser();
        Report report = parser.parse(new StringReaderFactory(content));

        assertThat(report).hasSize(4);
        assertThat(report.getErrorMessages()).isEmpty();

        checkIssue1(report.get(0));
        checkIssue2(report.get(1));
        checkIssue3(report.get(2));
        checkIssue4(report.get(3));
    }

    private void logToFile(Issue issue, Path file) {
        try {
            if (Files.notExists(file)) {
                Files.createFile(file);
            }
            Files.write(file, (convertIssue(issue) + "\n").getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String convertIssue(Issue issue) {
        return new Gson().toJson(issue);
    }

    private static Issue createIssue1() {
        Issue issue1 = new Issue();
        issue1.setLineStart(190);
        issue1.setLineEnd(195);
        issue1.setFileName("test.txt");
        return issue1;
    }

    private static void checkIssue1(Issue issue) {
        assertThat(issue.getLineStart()).isEqualTo(190);
        assertThat(issue.getLineEnd()).isEqualTo(195);
        assertThat(issue.getFileName()).isEqualTo("test.txt");
    }

    private static void checkIssue1(edu.hm.hafner.analysis.Issue issue) {
        assertThat(issue.getLineStart()).isEqualTo(190);
        assertThat(issue.getLineEnd()).isEqualTo(195);
        assertThat(issue.getFileName()).isEqualTo("test.txt");
    }

    private static Issue createIssue2() {
        Issue issue2 = new Issue();
        issue2.setFileName("file.txt");
        issue2.setLineRanges(Arrays.asList(
                new LineRange(10, 10),
                new LineRange(20, 20)));
        return issue2;
    }

    private static void checkIssue2(Issue issue) {
        assertThat(issue.getFileName()).isEqualTo("file.txt");
        LineRange lineRange1 = issue.getLineRanges()
                .get(0);
        LineRange lineRange2 = issue.getLineRanges()
                .get(1);
        assertThat(lineRange1.getStart()).isEqualTo(10);
        assertThat(lineRange1.getEnd()).isEqualTo(10);
        assertThat(lineRange2.getStart()).isEqualTo(20);
        assertThat(lineRange2.getEnd()).isEqualTo(20);
    }

    private static void checkIssue2(edu.hm.hafner.analysis.Issue issue) {
        assertThat(issue.getFileName()).isEqualTo("file.txt");
        edu.hm.hafner.analysis.LineRange lineRange1 = issue.getLineRanges()
                .get(0);
        edu.hm.hafner.analysis.LineRange lineRange2 = issue.getLineRanges()
                .get(1);
        assertThat(lineRange1.getStart()).isEqualTo(10);
        assertThat(lineRange1.getEnd()).isEqualTo(10);
        assertThat(lineRange2.getStart()).isEqualTo(20);
        assertThat(lineRange2.getEnd()).isEqualTo(20);
    }

    private static Issue createIssue3() {
        Issue issue3 = new Issue();
        issue3.setLineStart(10);
        issue3.setSeverity(Severity.HIGH);
        issue3.setFileName("example.txt");
        return issue3;
    }

    private static void checkIssue3(Issue issue) {
        assertThat(issue.getLineStart()).isEqualTo(10);
        assertThat(issue.getSeverity()).isEqualTo(Severity.HIGH);
        assertThat(issue.getFileName()).isEqualTo("example.txt");
    }

    private static void checkIssue3(edu.hm.hafner.analysis.Issue issue) {
        assertThat(issue.getLineStart()).isEqualTo(10);
        assertThat(issue.getSeverity()).isEqualTo(edu.hm.hafner.analysis.Severity.WARNING_HIGH);
        assertThat(issue.getFileName()).isEqualTo("example.txt");
    }

    private static Issue createIssue4() {
        Issue issue4 = new Issue()
                .additionalProperties("ap")
                .category("c")
                .columnStart(110)
                .columnEnd(120)
                .description("d")
                .directory("d")
                .fileName("fn.txt")
                .fingerprint("fgpt")
                .id(UUID.fromString("823b92b6-98eb-41c4-83ce-b6ec1ed6f98f"))
                .lineStart(10)
                .lineEnd(11)
                .lineRanges(Collections.singletonList(new LineRange(10, 11)))
                .message("msg")
                .moduleName("mdl")
                .origin("orgn")
                .packageName("pn")
                .reference("ref")
                .severity(Severity.LOW)
                .type("t");
        return issue4;
    }

    private static void checkIssue4(Issue issue, boolean mergeFileAndDirectory) {
        assertThat(issue.getAdditionalProperties()).isEqualTo("ap");
        assertThat(issue.getCategory()).isEqualTo("c");
        assertThat(issue.getColumnStart()).isEqualTo(110);
        assertThat(issue.getColumnEnd()).isEqualTo(120);
        assertThat(issue.getDescription()).isEqualTo("d");
        if (mergeFileAndDirectory) {
            assertThat(issue.getFileName()).isEqualTo("d/fn.txt");
            assertThat(issue.getDirectory()).isNull();
        } else {
            assertThat(issue.getFileName()).isEqualTo("fn.txt");
            assertThat(issue.getDirectory()).isEqualTo("d");
        }
        assertThat(issue.getFingerprint()).isEqualTo("fgpt");
        assertThat(issue.getId()).isEqualTo(UUID.fromString("823b92b6-98eb-41c4-83ce-b6ec1ed6f98f"));
        assertThat(issue.getLineStart()).isEqualTo(10);
        assertThat(issue.getLineEnd()).isEqualTo(11);
        assertThat(issue.getLineRanges()).hasSize(1);
        assertThat(issue.getLineRanges()
                .get(0)
                .getStart()).isEqualTo(10);
        assertThat(issue.getLineRanges()
                .get(0)
                .getEnd()).isEqualTo(11);
        assertThat(issue.getMessage()).isEqualTo("msg");
        assertThat(issue.getModuleName()).isEqualTo("mdl");
        assertThat(issue.getOrigin()).isEqualTo("orgn");
        assertThat(issue.getPackageName()).isEqualTo("pn");
        assertThat(issue.getReference()).isEqualTo("ref");
        assertThat(issue.getSeverity()).isEqualTo(Severity.LOW);
        assertThat(issue.getType()).isEqualTo("t");
    }

    private static void checkIssue4(edu.hm.hafner.analysis.Issue issue) {
        assertThat(issue.getAdditionalProperties()).isEqualTo("ap");
        assertThat(issue.getCategory()).isEqualTo("c");
        assertThat(issue.getColumnStart()).isEqualTo(110);
        assertThat(issue.getColumnEnd()).isEqualTo(120);
        assertThat(issue.getDescription()).isEqualTo("d");
        assertThat(issue.getFileName()).isEqualTo("d/fn.txt");
        assertThat(issue.getFingerprint()).isEqualTo("fgpt");
        assertThat(issue.getId()).isEqualTo(UUID.fromString("823b92b6-98eb-41c4-83ce-b6ec1ed6f98f"));
        assertThat(issue.getLineStart()).isEqualTo(10);
        assertThat(issue.getLineEnd()).isEqualTo(11);
        assertThat(issue.getLineRanges()).hasSize(1);
        assertThat(issue.getLineRanges()
                .get(0)
                .getStart()).isEqualTo(10);
        assertThat(issue.getLineRanges()
                .get(0)
                .getEnd()).isEqualTo(11);
        assertThat(issue.getMessage()).isEqualTo("msg");
        assertThat(issue.getModuleName()).isEqualTo("mdl");
        assertThat(issue.getOrigin()).isEqualTo("orgn");
        assertThat(issue.getPackageName()).isEqualTo("pn");
        assertThat(issue.getReference()).isEqualTo("ref");
        assertThat(issue.getSeverity()).isEqualTo(edu.hm.hafner.analysis.Severity.WARNING_LOW);
        assertThat(issue.getType()).isEqualTo("t");
    }

    private static String readFile(Path file) {
        String content;
        try {
            content = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException("Could not read file: " + file, e);
        }
        return content;
    }

}
