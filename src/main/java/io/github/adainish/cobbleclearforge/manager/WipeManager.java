package io.github.adainish.cobbleclearforge.manager;

import io.github.adainish.cobbleclearforge.CobbleClearForge;
import io.github.adainish.cobbleclearforge.obj.ItemWiper;
import io.github.adainish.cobbleclearforge.obj.PokemonWiper;

public class WipeManager
{
    public ItemWiper itemWiper;
    public PokemonWiper pokemonWiper;

    public WipeManager()
    {

    }

    public void init()
    {
        if (CobbleClearForge.config != null) {
            if (this.itemWiper != null)
                this.itemWiper.shutdown();
            if (this.pokemonWiper != null)
                this.pokemonWiper.shutdown();
            this.itemWiper = new ItemWiper();
            this.pokemonWiper = new PokemonWiper();
            this.itemWiper.init();
            this.pokemonWiper.init();
        } else {
            CobbleClearForge.getLog().error("Failed to initialise Cobblemon Clear, the config failed to load or contained mismatched data");
        }
    }
}
