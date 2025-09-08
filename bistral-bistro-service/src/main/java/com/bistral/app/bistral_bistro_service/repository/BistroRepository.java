package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.dtos.BistroResponse;
import com.bistral.app.bistral_bistro_service.entity.BistroEntity;
import com.bistral.app.bistral_bistro_service.entity.BranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface BistroRepository extends JpaRepository<BistroEntity, UUID> {
    @Query("""
            SELECT DISTINCT b
            FROM BistroEntity b
            LEFT JOIN FETCH b.branches br
            WHERE b.bistroId = :bistroId
            """)
    Optional<BistroEntity> findBistroWithBranches(UUID bistroId);

    List<BistroEntity> findByUserId(UUID userId);

    @Query("""
                SELECT new com.bistral.app.bistral_bistro_service.dtos.BistroResponse(
                    b.bistroId, b.bistroName
                )
                FROM BistroEntity b
                WHERE b.bistroId = :bistroId
            """)
    Optional<BistroResponse> findByBistroId(UUID bistroId);

//    public Optional<BistroEntity> findBistroWithBranches(@Param("bistroId") UUID bistroId);
}
