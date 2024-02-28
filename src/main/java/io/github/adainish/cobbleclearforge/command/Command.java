package io.github.adainish.cobbleclearforge.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.adainish.cobbleclearforge.CobbleClear;
import io.github.adainish.cobbleclearforge.config.Config;
import io.github.adainish.cobbleclearforge.util.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public class Command
{
    public static LiteralArgumentBuilder<CommandSourceStack> getCommand() {
        return Commands.literal("cobbleclear")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                .executes(cc -> {
                    //send error message
                    Util.send(cc.getSource(), "&cPlease provide a valid argument");
                    return 1;
                })
                .then(Commands.literal("reload")
                        .executes(cc -> {
                            //do reload
                            CobbleClear.instance.reload();
                            Util.send(cc.getSource(), "&cReloaded the mod");
                            return 1;
                        })
                )
                .then(Commands.literal("items")
                        .executes(cc -> {
                            //forcefully clear items
                            Util.send(cc.getSource(), "&aForcefully wiped items");
                            CobbleClear.manager.itemWiper.wipe();
                            return 1;
                        })
                )
                .then(Commands.literal("pokemon")
                        .executes(cc -> {
                            Util.send(cc.getSource(), "&aForcefully wiped pokemon");
                            CobbleClear.manager.pokemonWiper.wipe();
                            return 1;
                        })
                )
                .then(Commands.literal("whitelist")
                        .executes(cc -> {
                            Util.send(cc.getSource(), "&aPlease provide either the item argument or the species argument alongside a valid species");
                            return 1;
                        })
                        .then(Commands.literal("item")
                                .executes(cc -> {
                                    try {
                                        ServerPlayer player = cc.getSource().getPlayerOrException();
                                        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
                                        if (stack.isEmpty())
                                        {
                                            throw new Exception("No item in hand");
                                        }
                                        ResourceLocation location = BuiltInRegistries.ITEM.getKey(stack.getItem());
                                        if (!location.toString().isBlank() && !location.toString().isEmpty()) {
                                            //check if items isn't already whitelisted
                                            if (!CobbleClear.config.itemWhitelist.isWhiteListed(location.toString())) {
                                                //whitelist item
                                                Config config = CobbleClear.config;
                                                config.itemWhitelist.whitelistedItemIDs.add(location.toString().toLowerCase());
                                                Config.saveConfig(config);
                                                Util.send(cc.getSource(), "&aWhitelisted the item.. Use the reload command to see the changes");
                                            } else {        // else throw exception
                                                throw new Exception("Items already been whitelisted");
                                            }
                                        } else {
                                            throw new Exception("Unable to find item data... Thus being unable to whitelist it");
                                        }
                                    } catch (Exception e)
                                    {
                                        Util.send(cc.getSource(), e.getMessage());
                                    }

                                    return 1;
                                })
                        )
                        .then(Commands.literal("species")
                                .executes(cc -> {
                                    //provide species error
                                    Util.send(cc.getSource(), "&cPlease provide a valid species");
                                    return 1;
                                })
                                .then(Commands.argument("speciesname", StringArgumentType.string())
                                        .executes(cc -> {
                                            String speciesName = StringArgumentType.getString(cc, "speciesname");
                                            //check valid species
                                            if (Util.getNullableFromString(speciesName) != null)
                                            {
                                                if (!CobbleClear.config.pokemonWhitelist.isWhiteListed(speciesName))
                                                {
                                                    Config config = CobbleClear.config;
                                                    config.pokemonWhitelist.whitelistedPokemon.add(speciesName.toLowerCase());
                                                    Config.saveConfig(config);
                                                    Util.send(cc.getSource(), "&aWhitelisted the pokemon.. Use the reload command to see the changes");
                                                } else {
                                                    Util.send(cc.getSource(), "&cThat pokemon species was already whitelisted");
                                                }
                                            } else {
                                                Util.send(cc.getSource(), "&cThat was not a valid species");
                                            }
                                            return 1;
                                        })
                                )
                        )
                )
                ;
    }
}
