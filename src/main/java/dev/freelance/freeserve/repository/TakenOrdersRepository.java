package dev.freelance.freeserve.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.freelance.freeserve.entity.TakenOrders;

@Repository
public interface TakenOrdersRepository extends CrudRepository<TakenOrders,Integer> {

    @Query("select order from TakenOrders order where order.freelancerId = :id")
    public List<TakenOrders> findAllTakenByClientId(@Param("id") int id);
    
}
