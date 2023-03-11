package org.waveapi.api;

import org.slf4j.LoggerFactory;

public class Logger {

    private final org.slf4j.Logger log;
    public Logger(WaveMod mod) {
        log = LoggerFactory.getLogger(mod.name);
    }

    public void log(String message) {
        log.info(message);
    }


}
