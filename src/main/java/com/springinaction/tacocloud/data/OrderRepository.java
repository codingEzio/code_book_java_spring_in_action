package com.springinaction.tacocloud.data;

import java.util.Optional;

import com.springinaction.tacocloud.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
