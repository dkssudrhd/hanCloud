package com.hancloud.hancloud.storage.dto.entity;

import com.hancloud.hancloud.storage.dto.enums.StorageAuth;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HanStorage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @UniqueElements
    String path;
    StorageAuth auth;
}
