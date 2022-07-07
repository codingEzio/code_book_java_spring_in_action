package com.springinaction.tacocloud.data;

import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.asm.Type;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springinaction.tacocloud.IngredientRef;
import com.springinaction.tacocloud.Taco;
import com.springinaction.tacocloud.TacoOrder;

@Repository
public class OrderRepositoryImplJdbc implements OrderRepository {

    private JdbcOperations jdbcOperations;

    public OrderRepositoryImplJdbc(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public TacoOrder save(TacoOrder order) {
        PreparedStatementCreatorFactory prepStmtCrtorFctry = new PreparedStatementCreatorFactory(
                "insert into Taco_Order "
                        + "(delivery_name, delivery_street, delivery_city, "
                        + "delivery_state, delivery_zip, cc_number, "
                        + "cc_expiration, cc_cvv, placed_at) "
                        + "values (?,?,?,?,?,?,?,?,?)",
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);

        prepStmtCrtorFctry.setReturnGeneratedKeys(true);

        order.setPlacedAt(new Date());
        PreparedStatementCreator prepStmtCrtor = prepStmtCrtorFctry.newPreparedStatementCreator(
                Arrays.asList(
                        order.getDeliveryName(),
                        order.getDeliveryStreet(),
                        order.getDeliveryCity(),
                        order.getDeliveryState(),
                        order.getDeliveryZip(),
                        order.getCcNumber(),
                        order.getCcExpiration(),
                        order.getCcCVV(),
                        order.getPlacedAt()));

        GeneratedKeyHolder genKeyHolder = new GeneratedKeyHolder();

        jdbcOperations.update(prepStmtCrtor, genKeyHolder);
        long orderId = genKeyHolder.getKey().longValue();
        order.setId(orderId);

        List<Taco> tacos = order.getTacos();
        int i = 0;
        for (Taco taco : tacos) {
            saveTaco(orderId, i++, taco);
        }
    }

    public long saveTaco(Long orderId, int orderKey, Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreatorFactory prepStmtCrtorFctry = new PreparedStatementCreatorFactory(
                "insert into Taco "
                        + "(name, created_at, taco_order, taco_order_key) "
                        + "values (?,?,?,?)",
                Types.VARCHAR, Types.TIMESTAMP,
                Type.LONG,
                Type.LONG);

        prepStmtCrtorFctry.setReturnGeneratedKeys(true);

        PreparedStatementCreator prepStmtCrtor = prepStmtCrtorFctry.newPreparedStatementCreator(
                Arrays.asList(
                        taco.getName(),
                        taco.getCreatedAt(),
                        orderId,
                        orderKey));

        GeneratedKeyHolder genKeyHolder = new GeneratedKeyHolder();

        jdbcOperations.update(prepStmtCrtor, genKeyHolder);
        long tacoId = genKeyHolder.getKey().longValue();
        taco.setId(tacoId);

        saveIngredientRefs(tacoId, taco.getIngredients());

        return tacoId;
    }

    public void saveIngredientRefs(long tacoId, List<IngredientRef> ingredientRefs) {
        int key = 0;

        for (IngredientRef ingredientRef : ingredientRefs) {
            jdbcOperations.update(
                    "insert into Ingredient_Ref (ingredient, taco, taco_key) "
                            + "values (?,?,?)",
                    ingredientRef.getIngredient(), tacoId, key++);
        }
    }
}
