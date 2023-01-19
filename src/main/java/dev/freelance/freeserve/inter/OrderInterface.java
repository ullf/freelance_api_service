package dev.freelance.freeserve.inter;


import java.util.List;

import dev.freelance.freeserve.entity.AbstractOrder;

public interface OrderInterface {
    AbstractOrder createOrder(int clientId,String name, String description);
    AbstractOrder checkOrder(int orderId);
    int completeOrder(int orderId);
    List<AbstractOrder> getAllOrdersById(int clientId);
}
