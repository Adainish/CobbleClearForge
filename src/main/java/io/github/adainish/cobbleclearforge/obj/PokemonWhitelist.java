package io.github.adainish.cobbleclearforge.obj;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Species;
import io.github.adainish.cobbleclearforge.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PokemonWhitelist
{
    public List<String> whitelistedPokemon = new ArrayList<>();

    public PokemonWhitelist()
    {

    }

    public List<Species> getWhiteListedSpeciesList()
    {
        return whitelistedPokemon.stream().map(Util::getSpeciesFromString).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public boolean isWhiteListed(PokemonEntity pokemonEntity)
    {
        return getWhiteListedSpeciesList().contains(pokemonEntity.getPokemon().getSpecies());
    }
}
