package fr.jmini.utils.issuemodel;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * An issue reported by a static analysis tool.
 */
public class Issue implements Serializable {
    private static final long serialVersionUID = 1L;

    private String fileName;

    private String directory;

    private Integer lineStart;
    private Integer lineEnd;
    private Integer columnStart;
    private Integer columnEnd;
    private List<LineRange> lineRanges;
    private String message;

    private String category;
    private String type;
    private Severity severity;
    private String description;
    private String packageName;
    private String moduleName;
    private String origin;
    private String reference;
    private String fingerprint;
    private Serializable additionalProperties;

    private UUID id;

    public Integer getLineStart() {
        return lineStart;
    }

    public void setLineStart(int lineStart) {
        this.lineStart = lineStart;
    }

    public Issue lineStart(int ls) {
        setLineStart(ls);
        return this;
    }

    public Integer getLineEnd() {
        return lineEnd;
    }

    public void setLineEnd(int lineEnd) {
        this.lineEnd = lineEnd;
    }

    public Issue lineEnd(int le) {
        setLineEnd(le);
        return this;
    }

    public Integer getColumnStart() {
        return columnStart;
    }

    public void setColumnStart(int columnStart) {
        this.columnStart = columnStart;
    }

    public Issue columnStart(int cs) {
        setColumnStart(cs);
        return this;
    }

    public Integer getColumnEnd() {
        return columnEnd;
    }

    public void setColumnEnd(int columnEnd) {
        this.columnEnd = columnEnd;
    }

    public Issue columnEnd(int ce) {
        setColumnEnd(ce);
        return this;
    }

    public List<LineRange> getLineRanges() {
        return lineRanges;
    }

    public void setLineRanges(List<LineRange> lineRanges) {
        this.lineRanges = lineRanges;
    }

    public Issue lineRanges(List<LineRange> lr) {
        setLineRanges(lr);
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Issue fileName(String fn) {
        setFileName(fn);
        return this;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public Issue directory(String d) {
        setDirectory(d);
        return this;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Issue category(String c) {
        setCategory(c);
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Issue type(String t) {
        setType(t);
        return this;
    }

    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    public Issue severity(Severity s) {
        setSeverity(s);
        return this;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Issue message(String m) {
        setMessage(m);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Issue description(String d) {
        setDescription(d);
        return this;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Issue packageName(String pn) {
        setPackageName(pn);
        return this;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Issue moduleName(String mn) {
        setModuleName(mn);
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Issue origin(String o) {
        setOrigin(o);
        return this;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Issue reference(String r) {
        setReference(r);
        return this;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public Issue fingerprint(String f) {
        setFingerprint(f);
        return this;
    }

    public Serializable getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Serializable additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public Issue additionalProperties(Serializable ap) {
        setAdditionalProperties(ap);
        return this;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Issue id(UUID i) {
        setId(i);
        return this;
    }

}
