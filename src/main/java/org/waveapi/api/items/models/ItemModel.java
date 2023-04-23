package org.waveapi.api.items.models;

import org.waveapi.api.items.WaveItem;

import java.io.File;

public abstract class ItemModel {
    public abstract void build(File pack, WaveItem mod);
}
