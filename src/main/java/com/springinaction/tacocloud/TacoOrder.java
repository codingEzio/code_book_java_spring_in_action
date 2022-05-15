package com.springinaction.tacocloud;

import java.util.List;
import java.util.ArrayList;
import lombok.Data;

@Data
public class TacoOrder {
    private String deliveryName;
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;
    private String ccExpiration;
    private String ccCVV;

    // An order could contain multiple tacos
    private List<Taco> tacos = new ArrayList<>();

    // An order is dynamic
    // You could add more tacos anytime
    public void addTaco(Taco taco) {
        this.tacos.add(taco);
    }
}