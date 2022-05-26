package com.springinaction.tacocloud.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.springinaction.tacocloud.Ingredient;
import com.springinaction.tacocloud.Ingredient.Type;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

	private Map<String, Ingredient> ingreMap = new HashMap<>();

	public IngredientByIdConverter() {
		ingreMap.put("FLTO", new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
		ingreMap.put("COTO", new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
		ingreMap.put("GRBF", new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
		ingreMap.put("CARN", new Ingredient("CARN", "Carnitas", Type.PROTEIN));
		ingreMap.put("TMTO", new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
		ingreMap.put("LETC", new Ingredient("LETC", "Lettuce", Type.VEGGIES));
		ingreMap.put("CHED", new Ingredient("CHED", "Cheddar", Type.CHEESE));
		ingreMap.put("JACK", new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
		ingreMap.put("SLSA", new Ingredient("SLSA", "Salsa", Type.SAUCE));
		ingreMap.put("SRCR", new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
	}

	@Override
	public Ingredient convert(String id) {
		return ingreMap.get(id);
	}
}