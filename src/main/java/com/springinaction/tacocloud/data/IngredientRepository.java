package com.springinaction.tacocloud.data;

import java.util.Optional;

import com.springinaction.tacocloud.Ingredient;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();

    Optional<Ingredient> findById(String id);

    Ingredient save(Ingredient ingredient);
}