package io.github.adainish.cobbleclearforge.obj;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemWhitelist
{

    public List<String> whitelistedItemIDs = new ArrayList<>();

    public ItemWhitelist()
    {}

    public static Optional<Item> getItemFromString(String id)
    {
        ResourceLocation location = new ResourceLocation(id);
        return BuiltInRegistries.ITEM.getOptional(location);
    }

    public List<Item> convertedWhiteListedItems()
    {
        List<Item> items = new ArrayList<>();
        for (String s : whitelistedItemIDs) {
            //pull item from registry and add to whitelist
            if (getItemFromString(s).isPresent())
                items.add(getItemFromString(s).get());
        }
        return items;
    }

    public boolean isWhiteListed(String string)
    {
        for (String s:whitelistedItemIDs) {
            if (string.equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

    public boolean isWhiteListed(ItemEntity itemEntity)
    {
        Item item = itemEntity.getItem().getItem();
        return convertedWhiteListedItems().contains(item);
    }
}
