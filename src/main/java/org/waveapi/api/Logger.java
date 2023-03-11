package org.waveapi.api;

import org.apache.logging.slf4j.Log4jLogger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.waveapi.Main;

public class Logger {

    private final org.slf4j.Logger log;
    public Logger(WaveMod mod) {
        log = LoggerFactory.getLogger(mod.name);
    }

    public void log(String message) {
        log.info(message);
    }


}
