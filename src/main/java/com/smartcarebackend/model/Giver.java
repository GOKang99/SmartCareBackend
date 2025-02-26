package com.smartcarebackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "givers")
public class Giver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long giverId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    @JsonManagedReference
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy="giver",cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private List<Resident> residents;

    @OneToMany(mappedBy="giver",cascade=CascadeType.MERGE)
    @JsonBackReference
    @ToString.Exclude
    private List<Cist> cists;

    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private List<Activity> acts;

    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private List<Meal> meals;

    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private List<Visit> visits;

    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private List<Notice> notice;

    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    @ToString.Exclude
    private List<Composition> compositions;
}
