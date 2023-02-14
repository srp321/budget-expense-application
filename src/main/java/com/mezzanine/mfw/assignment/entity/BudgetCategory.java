package com.mezzanine.mfw.assignment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamicUpdate
public class BudgetCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    private BigDecimal amount;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime insertDateTime;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime updateDateTime;
}
