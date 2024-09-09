package com.its.service.domain.classification.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@Entity
@NoArgsConstructor
@Table(name = "minor")
public class Minor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "minor_id")
    private Long minorId;

    @Column(name = "minor_name", nullable = false)
    private String minorName;

    @ManyToOne
    @JoinColumn(name = "major_id", nullable = false)
    private Major major;

}
