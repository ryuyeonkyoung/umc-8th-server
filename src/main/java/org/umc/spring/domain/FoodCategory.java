package org.umc.spring.domain;

import jakarta.persistence.*;
import lombok.*;
import org.umc.spring.domain.mapping.MemberPrefer;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FoodCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "foodCategory", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<MemberPrefer> memberPrefers = new HashSet<>();

    @Column(nullable = false, length = 50)
    private String name;
}