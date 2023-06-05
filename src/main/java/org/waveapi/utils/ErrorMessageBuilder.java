package org.waveapi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ErrorMessageBuilder {
    public static void create(String problem, List<String> content) {
        Logger log = LoggerFactory.getLogger("WaveAPI");
        for (String c : content) {
            log.error(c);
        }
        System.exit(0);
    }
}
