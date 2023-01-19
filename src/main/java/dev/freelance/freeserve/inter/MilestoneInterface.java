package dev.freelance.freeserve.inter;

import java.util.List;

import dev.freelance.freeserve.entity.Milestone;

public interface MilestoneInterface {

    int createMilestone(Milestone milestone);
    Milestone createMilestone(int orderId,String name, String description);
    int completeMilestone(int milestoneId);
    List<Milestone> getAllMilestonesByOrderId(int orderId);
}
