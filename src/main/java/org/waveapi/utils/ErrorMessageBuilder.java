package org.waveapi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ErrorMessageBuilder {
    public static void create(String problem, List<String> content) {
        Logger log = LoggerFactory.getLogger("WaveAPI");
        StringBuilder str = new StringBuilder();
        for (String c : content) {
            str.append("\n").append(c);
            log.error("\n" + c);
        }
        str.setLength(str.length() - 2);
        throw new RuntimeException("Missing mods: " + str);
    }
}
