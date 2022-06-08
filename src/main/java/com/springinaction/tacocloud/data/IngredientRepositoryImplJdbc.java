package com.springinaction.tacocloud.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.springinaction.tacocloud.Ingredient;

@Repository
public class IngredientRepositoryImplJdbc implements IngredientRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public IngredientRepositoryImplJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Ingredient mapRowToIngredient(ResultSet row, int rowNum) throws SQLException {
        // So basically it converts data returned from database into an object
        // that could be used in the view template (for Thymeleaf in this case).
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type")));
    }
}