package org.waveapi.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Yaml {

    private Pattern numCheck = Pattern.compile("-?\\d+(\\.\\d+)?");

    /**
     * Very incomplete implementation of yaml
     */
    public Map<String, Object> load(InputStream openStream) {
        Map<String, Object> objects = new HashMap<>();
        try {
            String str = new String(openStream.readAllBytes());

            for (String line : str.split("\n")) {
                String[] lineSplit = line.split(":");
                String content = lineSplit[1].strip();
                if (numCheck.matcher(content).matches()) {
                    float f = Float.parseFloat(content);
                    if (Math.floor(f) != f) {
                        objects.put(lineSplit[0].strip(), f);
                    } else {
                        objects.put(lineSplit[0].strip(), (int)f);
                    }
                } else {
                    objects.put(lineSplit[0].strip(), content);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return objects;
    }
}
