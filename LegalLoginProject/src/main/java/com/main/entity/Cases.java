package com.main.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Table
@Data
@Entity
public class Cases {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String caseNumber;
    private String caseName;
    private String caseDescriptions;
    private String addNotes;
    private boolean caseStatus;
    private LocalDateTime registerDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Clients clients;


    @JsonIgnore
    @OneToMany(mappedBy = "cases", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tasks> tasks;


    @JsonIgnore
    @OneToMany(mappedBy = "cases",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Documents> documents;

}
