package com.bistral.app.bistral_bistro_service.entity;


import com.bistral.app.bistral_bistro_service.exceptions.ResourceNotFoundException;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

/**
 *
 */
@Getter
@MappedSuperclass
public abstract class SoftDeletableEntity extends BaseEntity {

    @Column()
    private Instant deletedAt;

    @Column
    private UUID deletedBy;

    public void markDeleted(Instant at, UUID actorId) {
        if (this.deletedAt != null)
            throw new IllegalStateException("Resource is already deleted");
        this.deletedAt = at;
        this.deletedBy = actorId;
    }



}
