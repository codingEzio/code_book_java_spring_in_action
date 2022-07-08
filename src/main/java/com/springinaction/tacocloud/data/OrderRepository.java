package com.springinaction.tacocloud.data;

import org.springframework.data.repository.CrudRepository;

import com.springinaction.tacocloud.TacoOrder;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
}