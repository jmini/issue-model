package fr.jmini.utils.issuemodel;

import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import edu.hm.hafner.analysis.ReaderFactory;

public class StringReaderFactory extends ReaderFactory {
    private final String content;

    StringReaderFactory(final String content) {
        super(StandardCharsets.UTF_8);

        this.content = content;
    }

    @Override
    public String getFileName() {
        return "none";
    }

    @Override
    public Reader create() {
        return new StringReader(content);
    }
}