package com.choice.autofarm.builder.farm;

import com.choice.autofarm.AutoFarm;
import com.choice.autofarm.builder.RecipeBuilder;
import com.choice.autofarm.data.NBTDataHandler;
import com.choice.autofarm.entity.minion.domain.MinionType;
import com.choice.autofarm.util.FarmConstants;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

import static com.choice.autofarm.config.Settings.AutoFarmSettings.StoneSettings.STONE_NAME;
import static com.choice.autofarm.config.Settings.AutoFarmSettings.StoneSettings.STONE_SKIN;
import static com.choice.autofarm.config.Settings.AutoFarmSettings.WheatSettings.WHEAT_NAME;
import static com.choice.autofarm.config.Settings.AutoFarmSettings.WheatSettings.WHEAT_SKIN;
import static com.choice.autofarm.util.FarmConstants.MINION_TAG;

public class MinionsRecipe {

    public static void recipeFarmStone(){
        ItemStack head = ItemCreator.of(CompMaterial.PLAYER_HEAD, STONE_NAME).make();
        NBTDataHandler data = new NBTDataHandler(head, MINION_TAG);
        data.setString(FarmConstants.ENTITY_MINION_TYPE, MinionType.STONE.name());
        data.setBoolean(FarmConstants.ENTITY_CRAFTED, true);
        NBTCompound skull = data.getNbtItem().addCompound("SkullOwner");
        skull.setString("Name", STONE_NAME);
        NBTListCompound textures = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        textures.setString("Value", STONE_SKIN);

        ItemCreator customItem = ItemCreator.of(data.getNbtItem().getItem());
        NamespacedKey recipeKey = new NamespacedKey(AutoFarm.getInstance(), "farm_stone_craft");

        ShapedRecipe customRecipe = new RecipeBuilder(customItem, recipeKey)
                .pattern("AAA", "ABA", "AAA")
                .ingredient('A', CompMaterial.COBBLESTONE)
                .ingredient('B', CompMaterial.STONE_PICKAXE)
                .build();

        Bukkit.getServer().addRecipe(customRecipe);

    }

    public static void recipeFarmWheat(){
        ItemStack head = ItemCreator.of(CompMaterial.PLAYER_HEAD, WHEAT_NAME).make();
        NBTDataHandler data = new NBTDataHandler(head, MINION_TAG);
        data.setString(FarmConstants.ENTITY_MINION_TYPE, MinionType.WHEAT.name());
        data.setBoolean(FarmConstants.ENTITY_CRAFTED, true);
        NBTCompound skull = data.getNbtItem().addCompound("SkullOwner");
        skull.setString("Name", WHEAT_NAME);
        NBTListCompound textures = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        textures.setString("Value", WHEAT_SKIN);

        ItemCreator customItem = ItemCreator.of(data.getNbtItem().getItem());
        NamespacedKey recipeKey = new NamespacedKey(AutoFarm.getInstance(), "farm_wheat_craft");

        ShapedRecipe customRecipe = new RecipeBuilder(customItem, recipeKey)
                .pattern("AAA", "ABA", "AAA")
                .ingredient('A', CompMaterial.WHEAT)
                .ingredient('B', CompMaterial.STONE_HOE)
                .build();

        Bukkit.getServer().addRecipe(customRecipe);

    }

}
