package io.github.adainish.cobbleclearforge.obj;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemWhitelist
{

    public List<String> whitelistedItemIDs = new ArrayList<>();

    public ItemWhitelist()
    {}

    public List<Item> convertedWhiteListedItems()
    {
        List<Item> items = new ArrayList<>();
        for (String s:whitelistedItemIDs) {
            //pull item from registry and add to whitelist
        }
        return items;
    }
    public boolean isWhiteListed(ItemEntity itemEntity)
    {
        Item item = itemEntity.getItem().getItem();
        return convertedWhiteListedItems().contains(item);
    }
}
