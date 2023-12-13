package com.main.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Documents {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String documentId;
    private String fileName;
    private String fileType;
    private String[] fileUrl;
    private LocalDateTime uploadTime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "case_number")
    private Cases cases;


}
