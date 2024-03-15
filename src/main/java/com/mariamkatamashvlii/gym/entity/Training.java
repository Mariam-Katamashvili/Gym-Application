package com.mariamkatamashvlii.gym.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "traineeId", referencedColumnName = "traineeId")
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainerId", referencedColumnName = "trainerId")
    private Trainer trainer;

    @Column(nullable = false, unique = true)
    private String trainingName;

    @ManyToOne
    @JoinColumn(name = "training_typeId", referencedColumnName = "training_typeId")
    private TrainingType trainingType;

    @Column( nullable = false)
    private Date trainingDate;

    @Column(nullable = false)
    private Number duration;

}