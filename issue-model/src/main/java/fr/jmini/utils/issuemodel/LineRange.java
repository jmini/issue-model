package fr.jmini.utils.issuemodel;

import java.io.Serializable;

/**
 * A line range in a source file is defined by its first and last line.
 */
public class LineRange implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer start;
    private Integer end;

    public LineRange() {
    }

    public LineRange(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public LineRange start(Integer s) {
        setStart(s);
        return this;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public LineRange end(Integer e) {
        setEnd(e);
        return this;
    }
}
