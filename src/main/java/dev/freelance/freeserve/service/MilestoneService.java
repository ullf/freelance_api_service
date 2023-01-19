package dev.freelance.freeserve.service;

import dev.freelance.freeserve.entity.Milestone;
import dev.freelance.freeserve.inter.MilestoneInterface;
import dev.freelance.freeserve.repository.MilestoneRepository;
import dev.freelance.freeserve.repository.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MilestoneService implements MilestoneInterface {

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Milestone createMilestone(int orderId,String name, String description) {
        var order = orderRepository.findById(orderId).get();
        if (order != null) {
            Milestone milestone = new Milestone();
            milestone.setMilestoneName(name);
            milestone.setMilestoneDescription(description);
            milestone.setOrderId(order);
            milestoneRepository.save(milestone);
            return milestone;
        }
        return null;
    }

    @Override
    public int createMilestone(Milestone milestone) {
        var order = orderRepository.findById(milestone.getOrderId().getAbstractId()).get();
        if (order != null) {
            milestoneRepository.save(milestone);
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public List<Milestone> getAllMilestonesByOrderId(int orderId) {
        var list = milestoneRepository.findAllMilestonesByOrderId(orderId);
        return list;
    }

    @Transactional
    @Override
    public int completeMilestone(int milestoneId) {
        var milestone = milestoneRepository.findById(milestoneId).get();
        if (milestone != null) {
            milestone.setCompleted(true);
            milestoneRepository.save(milestone);
            return 0;
        }
        return -1;
    }
}
