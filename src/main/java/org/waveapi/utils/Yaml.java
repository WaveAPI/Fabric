package org.waveapi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Yaml {


    /**
     * Very incomplete implementation of yaml
     */
    public Map<String, Object> load(InputStream openStream) {
        Map<String, Object> objects = new HashMap<>();
        try {
            String str = new String(openStream.readAllBytes());

            for (String line : str.split("\n")) {
                String[] lineSplit = line.split(":");
                objects.put(lineSplit[0].strip(), lineSplit[1].strip());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return objects;
    }
}
