package dev.freelance.freeserve.service;

import dev.freelance.freeserve.entity.AbstractClient;
import dev.freelance.freeserve.entity.AbstractOrder;
import dev.freelance.freeserve.entity.TakenOrders;
import dev.freelance.freeserve.inter.OrderInterface;
import dev.freelance.freeserve.repository.ClientRepository;
import dev.freelance.freeserve.repository.OrderRepository;
import dev.freelance.freeserve.repository.TakenOrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class AbstractOrderService implements OrderInterface {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TakenOrdersRepository takenOrdersRepository;

    public AbstractOrder createOrder(AbstractOrder order) {
        if (!order.getClientsId().getNickname().equals(null)) {
           var client = clientRepository.findAbstractClientByNickname(order.getClientsId().getNickname());
           var principal = (AbstractClient) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal.getNickname().equals(client.getNickname()) && client.isIndicator() == false ) {
                order.setClientsId(client);
                orderRepository.save(order);
                return order;
            }
        }
        return null;
    }

    public TakenOrders takeOrder(int id) {
        var order = orderRepository.findById(id);
        if (order.isPresent()) {
            AbstractClient obj = (AbstractClient)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(obj.isIndicator()) {
                TakenOrders taken = new TakenOrders();
                taken.setOrderId(id);
                taken.setFreelancerId(obj.getId());
                takenOrdersRepository.save(taken);
                return taken;
            }
        }
        return null;
    }

    public List<AbstractOrder> getTakenOrders(int clientId) {
        var client = clientRepository.findById(clientId);
        if (client.isPresent()) {
            var obj = (AbstractClient)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (obj.isIndicator()) {
                var taken = takenOrdersRepository.findAllTakenByClientId(clientId);
                var list = new ArrayList<AbstractOrder>();
                for(int i=0;i<taken.size();i++) {
                    list.add(orderRepository.findById(taken.get(i).getOrderId()).get());
                }
                return list;
            }
        }
        return null;
    }

    @Override
    public AbstractOrder createOrder(int clientId,String name,String description) {
        var client = clientRepository.findById(clientId).get();
        System.out.println(client.getId());
        if (client.isIndicator() == true ) {
            AbstractOrder order = new AbstractOrder();
            order.setAbstractName(name);
            order.setAbstractDescription(description);
            order.setClientsId(client);
            orderRepository.save(order);
            return order;
        }
        return new AbstractOrder();
    }

    @Override
    public AbstractOrder checkOrder(int orderId) {
        Optional<AbstractOrder> op_order =  orderRepository.findById(orderId);
        return op_order.isEmpty() ? null : op_order.get();
    }

    @Transactional
    @Override
    public int completeOrder(int orderId) {
        var op_order = orderRepository.findById(orderId);
        if (op_order.isPresent()) {
            var order = op_order.get();
            order.setCompleted(true);
            orderRepository.save(order);
            return 0;
        }
        return -1;
    }

    @Override
    public List<AbstractOrder> getAllOrdersById(int clientId) {
        var list = orderRepository.findAllOrdersById(clientId);
        return list;
    }
}
