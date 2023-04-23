package org.waveapi.api.items.drop;

import org.waveapi.api.items.WaveItem;

import java.util.LinkedList;
import java.util.List;

public class ItemDrop extends Drop {

    private String item;
    private List<DropCondition> dropConditionList = new LinkedList<>();

    public ItemDrop(String item, int min, int max) {
        dropFunctionList = new LinkedList<>();
        this.item = item;
        dropFunctionList.add("""
                {
                                  "function": "minecraft:set_count",
                                  "count": {
                                    "type": "minecraft:uniform",
                                    "min":\s""" + min + """
.0
                                    ,
                                    "max":\s""" + max + """
.0
                                  },
                                  "add": false
                                }""");

    }
    public ItemDrop(WaveItem item, int min, int max) {
        this(item.getMod().name + ":" + item.getId(), min, max);
    }

    public ItemDrop(String item) {
        dropFunctionList = new LinkedList<>();
        this.item = item;
    }
    public ItemDrop(WaveItem item) {
        this(item.getMod().name + ":" + item.getId());
    }

    private final List<String> dropFunctionList;

    public ItemDrop apply(DropFunction fn) {
        dropFunctionList.add(fn.getFunction());
        return this;
    }

    @Override
    public void apply(DropCondition condition) {
        dropConditionList.add(condition);
    }

    @Override
    public void write(StringBuilder builder) {
        builder.append("{\"type\": \"minecraft:item\",");
        if (dropFunctionList.size() > 0) {
            builder.append("\"functions\": [");
            for (int i = 0 ; i < dropFunctionList.size() ; i++) {
                if (i > 0) {
                    builder.append(",");
                }
                builder.append(dropFunctionList.get(i));
            }
            builder.append("],");
        }
        if (dropConditionList.size() > 0) {
            builder.append("\"conditions\": [");
            for (int i = 0 ; i < dropConditionList.size() ; i++) {
                if (i > 0) {
                    builder.append(",");
                }
                builder.append(dropConditionList.get(i).getFunction());
            }
            builder.append("],");
        }
        builder.append("\"name\":\"").append(item).append("\"\n}");
    }
}
