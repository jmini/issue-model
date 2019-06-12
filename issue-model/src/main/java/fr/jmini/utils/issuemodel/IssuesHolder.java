package fr.jmini.utils.issuemodel;

import java.io.Serializable;
import java.util.List;

/**
 * An issue reported by a static analysis tool.
 */
public class IssuesHolder implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Issue> issues;

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public IssuesHolder issues(List<Issue> list) {
        setIssues(list);
        return this;
    }
}
