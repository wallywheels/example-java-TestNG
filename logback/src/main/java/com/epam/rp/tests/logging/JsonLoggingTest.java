package com.epam.rp.tests.logging;

import com.google.common.io.BaseEncoding;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * JUST an example of file logging
 *
 * @author Andrei Varabyeu
 */
public class JsonLoggingTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonLoggingTest.class);
    private static final String JSON_FILE_PATH = "xml/file.json";

    @Test
    public void logJsonBase64() throws IOException {
        /* here we are logging some binary data as BASE64 string */
        LOGGER.info("RP_MESSAGE#BASE64#{}#{}",
                BaseEncoding.base64().encode(Resources.asByteSource(Resources.getResource(JSON_FILE_PATH)).read()),
                "I'm logging content via BASE64");
    }

    @Test
    public void logJsonFile() throws IOException, InterruptedException {
        /* here we are logging some binary data as file (useful for selenium) */
        File file = File.createTempFile("rp-test", ".json");
        Resources.asByteSource(Resources.getResource(JSON_FILE_PATH)).copyTo(Files.asByteSink(file));

        for (int i = 0; i < 1; i++) {
            LOGGER.info("RP_MESSAGE#FILE#{}#{}", file.getAbsolutePath(), "I'm logging content via temp file");
        }
        Thread.sleep(5000L);

    }

}
