package org.umc.spring.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.umc.spring.domain.common.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Region extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Builder.Default
  @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Store> stores = new HashSet<>();

  @Column(nullable = false, length = 50)
  private String name;

  public void addStore(Store store) {
      if (store == null || this.stores.contains(store)) {
          return;
      }
    this.stores.add(store);
    store.setRegion(this);
  }

  public void removeStore(Store store) {
      if (store == null || !this.stores.contains(store)) {
          return;
      }
    this.stores.remove(store);
    store.setRegion(null);
  }
}