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
@Data
@Table
@Entity
public class Firm {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String firmId;

    private  String firmName;
    private String address1;
    private String address2;
    private String managerName;
    private String managerLastName;
    private String managerEmail;
    private String managerPhone;
    private boolean firmStatus;
    private String city;
    private int totalLowyer;
    private long totalSize;
    private String managerPassword;
    private String restCode;
    private LocalDateTime registerDateTime;

    @ToString.Exclude
    @OneToMany(mappedBy = "firms", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Lawyer> lawyers;


}
