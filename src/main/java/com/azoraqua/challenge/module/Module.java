package com.azoraqua.challenge.module;

import com.azoraqua.challenge.ChallengePlugin;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Module {

    public abstract void initialize(ChallengePlugin plugin);

    public boolean isEnabled() {
        return JavaPlugin.getPlugin(ChallengePlugin.class)
                .getConfig()
                .getBoolean(String.format("modules.%s.enabled", getClass().getSimpleName().replace("Module", "").toLowerCase()), false);
    }
}
