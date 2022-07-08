package com.springinaction.tacocloud.data;

import org.springframework.data.repository.CrudRepository;

import com.springinaction.tacocloud.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}