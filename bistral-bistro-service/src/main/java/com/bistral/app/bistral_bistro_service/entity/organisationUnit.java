package com.bistral.app.bistral_bistro_service.entity;


import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class organisationUnit {

    private UUID organisationUnitId;

}
