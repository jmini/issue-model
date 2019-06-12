package fr.jmini.utils.issuemodel;

import java.util.ArrayList;
import java.util.List;

import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.LineRangeList;

public class IssueMapper {

    public static edu.hm.hafner.analysis.Issue toAnalysisModelIssue(Issue issue) {
        IssueBuilder builder = new IssueBuilder();
        builder.setAdditionalProperties(issue.getAdditionalProperties());
        builder.setCategory(issue.getCategory());
        if (issue.getColumnEnd() != null) {
            builder.setColumnEnd(issue.getColumnEnd());
        }
        if (issue.getColumnStart() != null) {
            builder.setColumnStart(issue.getColumnStart());
        }
        builder.setDescription(issue.getDescription());
        builder.setDirectory(issue.getDirectory());
        builder.setFingerprint(issue.getFingerprint());
        builder.setFileName(issue.getFileName());
        builder.setId(issue.getId());
        if (issue.getLineEnd() != null) {
            builder.setLineEnd(issue.getLineEnd());
        }
        if (issue.getLineRanges() != null) {
            LineRangeList lineRanges = new LineRangeList();
            for (LineRange r : issue.getLineRanges()) {
                lineRanges.add(toAnalysisModelLineRange(r));
            }
            builder.setLineRanges(lineRanges);
        }
        if (issue.getLineStart() != null) {
            builder.setLineStart(issue.getLineStart());
        }
        builder.setMessage(issue.getMessage());
        builder.setModuleName(issue.getModuleName());
        builder.setOrigin(issue.getOrigin());
        builder.setPackageName(issue.getPackageName());
        builder.setReference(issue.getReference());
        if (issue.getSeverity() != null) {
            builder.setSeverity(toAnalysisModelSeverity(issue.getSeverity()));
        }
        builder.setType(issue.getType());
        return builder.build();
    }

    public static Issue toIssue(edu.hm.hafner.analysis.Issue issue) {
        Issue result = new Issue();
        result.setAdditionalProperties(issue.getAdditionalProperties());
        result.setCategory(issue.getCategory());
        result.setColumnEnd(issue.getColumnEnd());
        result.setColumnStart(issue.getColumnStart());
        result.setDescription(issue.getDescription());
        result.setFingerprint(issue.getFingerprint());
        result.setFileName(issue.getFileName());
        result.setId(issue.getId());
        result.setLineEnd(issue.getLineEnd());
        if (issue.getLineRanges() != null) {
            List<LineRange> lineRanges = new ArrayList<>();
            for (edu.hm.hafner.analysis.LineRange r : issue.getLineRanges()) {
                lineRanges.add(toLineRange(r));
            }
            result.setLineRanges(lineRanges);
        }
        result.setLineStart(issue.getLineStart());
        result.setMessage(issue.getMessage());
        result.setModuleName(issue.getModuleName());
        result.setOrigin(issue.getOrigin());
        result.setPackageName(issue.getPackageName());
        result.setReference(issue.getReference());
        result.setSeverity(toSeverity(issue.getSeverity()));
        result.setType(issue.getType());
        return result;
    }

    public static edu.hm.hafner.analysis.LineRange toAnalysisModelLineRange(LineRange lineRange) {
        if (lineRange.getStart() != null) {
            if (lineRange.getEnd() != null) {
                return new edu.hm.hafner.analysis.LineRange(lineRange.getStart(), lineRange.getEnd());
            }
            return new edu.hm.hafner.analysis.LineRange(lineRange.getStart());
        } else if (lineRange.getEnd() != null) {
            return new edu.hm.hafner.analysis.LineRange(lineRange.getEnd());
        }
        return new edu.hm.hafner.analysis.LineRange(0, 0);
    }

    public static LineRange toLineRange(edu.hm.hafner.analysis.LineRange lineRange) {
        LineRange result = new LineRange();
        result.setStart(lineRange.getStart());
        result.setEnd(lineRange.getEnd());
        return result;
    }

    public static edu.hm.hafner.analysis.Severity toAnalysisModelSeverity(Severity severity) {
        return edu.hm.hafner.analysis.Severity.valueOf(severity.name());
    }

    public static Severity toSeverity(edu.hm.hafner.analysis.Severity severity) {
        if (edu.hm.hafner.analysis.Severity.ERROR.equals(severity)) {
            return Severity.ERROR;
        }
        if (edu.hm.hafner.analysis.Severity.WARNING_HIGH.equals(severity)) {
            return Severity.HIGH;
        }
        if (edu.hm.hafner.analysis.Severity.WARNING_NORMAL.equals(severity)) {
            return Severity.NORMAL;
        }
        if (edu.hm.hafner.analysis.Severity.WARNING_LOW.equals(severity)) {
            return Severity.LOW;
        }
        throw new IllegalArgumentException("Unexpected severity parameter: " + severity);
    }
}
