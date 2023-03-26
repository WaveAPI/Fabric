package org.waveapi.ticker.tickers;

import net.minecraft.block.entity.BlockEntity;
import org.waveapi.api.content.items.block.blockentities.DeltaTicking;
import org.waveapi.ticker.DeltaTickManager;
import org.waveapi.ticker.DeltaTicker;

import java.util.ArrayList;
import java.util.List;

public class LimitedPerTick implements DeltaTicker {

    private record tickWrap(DeltaTicking ticking, long lastTick) {

    }
    private final List<tickWrap> ticking;
    private int i;
    private final int perTick;

    private long currentTick;

    public LimitedPerTick() {
        ticking = new ArrayList<>();
        i = 0;
        perTick = (int) DeltaTickManager.deltaTickingSettings.get("tickersPerTick");
    }

    @Override
    public void addTicking(DeltaTicking ticking) {
        System.out.println("test");
        this.ticking.add(new tickWrap(ticking, currentTick));
    }

    @Override
    public void tick() {
        currentTick++;
        for (int x = 0 ; x < perTick ; x++) {
            if (i >= ticking.size()) {
                return;
            }
            tickWrap deltaTicking = this.ticking.get(i);
            if (deltaTicking.ticking instanceof BlockEntity && !((BlockEntity) deltaTicking.ticking).isRemoved()) {
                ticking.remove(i);
            }
            deltaTicking.ticking.tick((int) (currentTick - deltaTicking.lastTick));

            i++;
        }
    }

    @Override
    public void removeTicking(DeltaTicking ticking) {
        this.ticking.removeIf(tickWrap -> tickWrap.ticking == ticking);
    }
}
