package org.waveapi.api.items.drop;

public class SilkTouchDrop extends Drop {
    private Drop dropWithSilkTouch, dropOtherwise;
    public SilkTouchDrop(Drop dropWithSilkTouch, Drop dropOtherwise) {
        this.dropWithSilkTouch = dropWithSilkTouch;
        this.dropOtherwise = dropOtherwise;
    }

    @Override
    public void write(StringBuilder builder) {
        builder.append("""
                          {
                          "type": "minecraft:alternatives",
                          "children": [
                            """);
        dropWithSilkTouch.apply(DropCondition.SILK_TOUCH);
        dropWithSilkTouch.write(builder);
        builder.append(",");
        dropOtherwise.write(builder);
        builder.append("]}");
    }
}
