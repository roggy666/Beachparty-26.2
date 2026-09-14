package net.satisfy.beachparty.core.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.satisfy.beachparty.Beachparty;

@Config(name = Beachparty.MOD_ID)
@Config.Gui.Background("beachparty:textures/block/sandwaves.png")
public class BeachpartyConfig implements ConfigData {

    public static BeachpartyConfig get() {
        return AutoConfig.getConfigHolder(BeachpartyConfig.class).getConfig();
    }


    @ConfigEntry.Category("worldgen")
    @ConfigEntry.Gui.Tooltip
    public boolean spawnPalms = true;

    @ConfigEntry.Category("worldgen")
    @ConfigEntry.Gui.Tooltip
    public boolean spawnSeashells = true;

    @ConfigEntry.Category("worldgen")
    @ConfigEntry.Gui.Tooltip
    public boolean spawnSandwaves = true;

    @ConfigEntry.Category("message_in_a_bottle")
    @ConfigEntry.Gui.Tooltip
    public boolean allowBottleSpawning = true;

    @ConfigEntry.Category("message_in_a_bottle")
    @ConfigEntry.Gui.Tooltip
    public int bottleMaxCount = 2;

    @ConfigEntry.Category("message_in_a_bottle")
    @ConfigEntry.Gui.Tooltip
    public int bottleSpawnInterval = 6000;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip
    public boolean destroySandcastle = true;
}
