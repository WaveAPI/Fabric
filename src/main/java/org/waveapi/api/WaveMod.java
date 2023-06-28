package org.waveapi.api;

import org.waveapi.api.events.Events;

import java.io.InputStream;

public abstract class WaveMod {

    public String name;
    public String version;

    public WaveMod(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public WaveMod() {

    }


    public void init() {}

    public void registerEvents(Events register) {}

    public void registerClientEvents(Events register) {}

    public InputStream getResource (String path) { // TODO: add more options for getResource case
        return getClass().getClassLoader().getResourceAsStream(path);
    }

}
