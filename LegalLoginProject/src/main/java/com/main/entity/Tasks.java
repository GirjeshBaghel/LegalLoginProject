package com.main.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
@Entity
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String taskId;
    private String taskDetails;
    private boolean taskStatus;
    private LocalDateTime registerTask;
    private LocalDateTime lastModification;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "case_number")
    private Cases cases;

}
