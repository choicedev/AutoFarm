package com.choice.autofarm.builder;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;

public class RecipeBuilder {
    private final ItemCreator result;
    private final NamespacedKey key;
    private final ShapedRecipe recipe;

    public RecipeBuilder(ItemCreator result, NamespacedKey key) {
        this.result = result;
        this.key = key;
        this.recipe = new ShapedRecipe(key, result.make());
    }

    public RecipeBuilder pattern(String... shape) {
        recipe.shape(shape);
        return this;
    }

    public RecipeBuilder ingredient(char symbol, CompMaterial material) {
        recipe.setIngredient(symbol, material.toMaterial());
        return this;
    }

    public ShapedRecipe build() {
        return recipe;
    }
}