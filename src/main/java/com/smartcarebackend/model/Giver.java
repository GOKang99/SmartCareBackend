package com.smartcarebackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Giver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long giverId;

    @NotBlank
    @Size(max = 20)
    @Column(name = "givername")
    private String giverName;

    @NotBlank
    @Size(max = 50)
    @Column(name = "giverlogin")
    private String giverLogin;

    @Size(max = 120)
    @Column(name = "giverpwd")
    private String giverPwd;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @JsonBackReference
    @ToString.Exclude
    private Role role;
}
