package dev.freelance.freeserve.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name="milestones")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int milestoneId;
    private String milestoneName;
    private String milestoneDescription;
    @ManyToOne
    @JoinColumn(name="abstractId")
    private AbstractOrder orderId;
    private boolean completed = false;
}

