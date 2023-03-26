package org.waveapi.ticker;

import org.waveapi.api.content.items.block.blockentities.DeltaTicking;

public interface DeltaTicker {
    void addTicking(DeltaTicking ticking);

    void tick();

    void removeTicking(DeltaTicking ticking);
}
