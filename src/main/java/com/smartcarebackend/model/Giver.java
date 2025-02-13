package com.smartcarebackend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private User user;

    @OneToMany(mappedBy="giver",cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
    private List<Resident> residents;

    @OneToMany(mappedBy="giver",cascade=CascadeType.MERGE)
    private List<Cist> cists;

    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Activity> acts;

    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Meal> meals;

    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Visit> visits;

    @OneToMany(mappedBy = "giver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notice> notice;

}
