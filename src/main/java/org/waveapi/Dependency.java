package org.waveapi;

public interface Dependency {
    String getMissingMessage();

    boolean check();
    String getLoadBefore();

}
