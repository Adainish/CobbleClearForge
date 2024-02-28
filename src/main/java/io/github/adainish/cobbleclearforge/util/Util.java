package io.github.adainish.cobbleclearforge.util;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Species;
import io.github.adainish.cobbleclearforge.CobbleClear;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class Util
{
    public static List<Species> pokemonList() {
        return PokemonSpecies.INSTANCE.getImplemented();
    }


    public static Species getNullableFromString(String species)
    {
        return PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocation.of("cobblemon:%sp%".replace("%sp%", species), ':'));
    }
    public static Species getSpeciesFromString(String species)
    {
        Species sp = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocation.of("cobblemon:%sp%".replace("%sp%", species), ':'));
        if (sp == null)
            sp = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocation.of("cobblemon:vulpix", ':'));
        return sp;
    }

    public static List<Species> ultrabeastList() {
        List <Species> speciesList = new ArrayList<>(pokemonList());

        speciesList.removeIf(sp -> !sp.create(1).isUltraBeast());

        return speciesList;
    }

    public static List<Species> nonSpecialList() {
        List <Species> speciesList = new ArrayList<>(pokemonList());

        speciesList.removeIf(sp -> sp.create(1).isUltraBeast());

        speciesList.removeIf(sp -> sp.create(1).isLegendary());

        return speciesList;
    }

    public static List<Species> legendaryList() {

        List <Species> speciesList = new ArrayList <>(pokemonList());

        speciesList.removeIf(sp -> !sp.create(1).isLegendary());

        return speciesList;
    }

    public static void send(CommandSourceStack sender, String message) {
        sender.sendSystemMessage(Component.literal(((TextUtil.getMessagePrefix()).getString() + message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")));
    }

    public static void doBroadcast(String message) {
        CobbleClear.getServer().getPlayerList().getPlayers().forEach(serverPlayerEntity -> {
            serverPlayerEntity.sendSystemMessage(Component.literal(TextUtil.getMessagePrefix().getString() + formattedString(message)));
        });
    }

    public static String formattedString(String s) {
        return s.replaceAll("&", "§");
    }
}
