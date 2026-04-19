package com.lms.university.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "classes")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clid;

    private String name;

    private String description;

    private String phone;

    private String email;

    @ManyToOne
    @JoinColumn(name = "cycle_id")
    private Cycle cycle;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "classRef", orphanRemoval = true)
    private Set<Genie> genies = new HashSet<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate() {
        int randomNum = (int)(Math.random() * 9000) + 1000;
        this.clid = "CL" + randomNum;
        this.createdDate = LocalDateTime.now();
    }
}
