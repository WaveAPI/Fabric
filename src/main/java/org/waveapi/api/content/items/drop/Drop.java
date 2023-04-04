package org.waveapi.api.content.items.drop;

public abstract class Drop {
    public abstract void write(StringBuilder builder);

    public void apply(DropCondition condition) {

    }

}
