package com.main.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table
@Data
@Entity
public class Lawyer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String lawyerId;
    private String lawyerFirstName;
    private String lawyerLastName;
    private String lawyerEmail;
    private boolean lawyerStatus;
    private String lawyerPassword;
    private String lawyerRole;
    private String lawyerPhone;
    private Date lawyerDOB;
    private String restCode;
    private LocalDateTime registerDateTime;


    @Transient
    private String firmName;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "firm_id")
    private Firm firms;

    @ToString.Exclude
    @OneToMany(mappedBy = "lawyers", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Clients> clients;


}
