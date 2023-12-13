package com.main.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table
@Data
@Entity
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String clientId;
    private String clientEmail;
    private String clientName;
    private String clientPhoneNumber;
    private String clientAddress;
    private boolean clientStatus;
    private LocalDateTime registerDate;


    @ToString.Exclude
    @OneToMany(mappedBy = "clients", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Cases> cases;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lawyer_id")
    private Lawyer lawyers;
}

