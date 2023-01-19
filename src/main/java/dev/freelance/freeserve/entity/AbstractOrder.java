package dev.freelance.freeserve.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class AbstractOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "abstract_id")
    private int abstractId;
    @Column(unique = true)
    private String abstractName;
    private String abstractDescription;
    @JsonIgnore
    @OneToMany(mappedBy="orderId")
    private List<Milestone> milestones = new ArrayList<>();
    @ManyToOne
    private AbstractClient clientsId;
    private boolean completed = false;
}
