package fr.jmini.utils.issuemodel;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Severity;
import edu.hm.hafner.analysis.parser.JsonLogParser;
import edu.hm.hafner.analysis.parser.JsonParser;
import edu.hm.hafner.analysis.parser.XmlParser;

public class ParserTest {

    @Test
    void testXml() throws Exception {
        Path reportFile = Paths.get("src/test/resources/report.xml");
        String content = readFile(reportFile);

        XmlParser parser = new XmlParser("/report/issue");
        Report report = parser.parse(new StringReaderFactory(content));

        assertThat(report).hasSize(2);
        assertThat(report.getErrorMessages()).isEmpty();

        checkIssue1(report.get(0));
        checkIssue2(report.get(1));
    }

    @Test
    void testJson() throws Exception {
        Path reportFile = Paths.get("src/test/resources/report.json");
        String content = readFile(reportFile);

        JsonParser parser = new JsonParser();
        Report report = parser.parse(new StringReaderFactory(content));

        assertThat(report).hasSize(2);
        assertThat(report.getErrorMessages()).isEmpty();

        checkIssue1(report.get(0));
        checkIssue2(report.get(1));
    }

    private static void checkIssue1(edu.hm.hafner.analysis.Issue issue) {
        assertThat(issue.getType()).isEqualTo("TxtChecker");
        assertThat(issue.getFileName()).isEqualTo("/tmp/file.txt");
        assertThat(issue.getLineStart()).isEqualTo(5);
        assertThat(issue.getSeverity()).isEqualTo(Severity.ERROR);
        assertThat(issue.getMessage()).isEqualTo("Token 'aaa' is not expected here");
    }

    private static void checkIssue2(edu.hm.hafner.analysis.Issue issue) {
        assertThat(issue.getType()).isEqualTo("TxtChecker");
        assertThat(issue.getFileName()).isEqualTo("/tmp/file.txt");
        assertThat(issue.getLineStart()).isEqualTo(6);
        assertThat(issue.getSeverity()).isEqualTo(Severity.ERROR);
        assertThat(issue.getMessage()).isEqualTo("Token 'bbb' is not expected here");

    }

    @Test
    void testLog() throws Exception {
        Path log = Paths.get("src/test/resources/log.txt");
        String content = readFile(log);
    
        JsonLogParser parser = new JsonLogParser();
        Report report = parser.parse(new StringReaderFactory(content));
    
        assertThat(report).hasSize(2);
        assertThat(report.getErrorMessages()).isEmpty();
    
        Issue issue1 = report.get(0);
        assertThat(issue1.getFileName()).isEqualTo("file.txt");
        assertThat(issue1.getLineStart()).isEqualTo(5);
        assertThat(issue1.getSeverity()).isEqualTo(Severity.ERROR);
        assertThat(issue1.getMessage()).isEqualTo("Token 'aaa' is wrong");
    
        Issue issue2 = report.get(1);
        assertThat(issue2.getFileName()).isEqualTo("file.txt");
        assertThat(issue2.getLineStart()).isEqualTo(6);
        assertThat(issue2.getSeverity()).isEqualTo(Severity.ERROR);
        assertThat(issue2.getMessage()).isEqualTo("Token 'bbb' is wrong");
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
