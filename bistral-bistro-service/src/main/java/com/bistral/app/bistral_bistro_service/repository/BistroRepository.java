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

    //
//    @Query("""
//            Select b.bistroId, b.bistroName , branch.branchId,branch.branchName from  BistroEntity b Left join Fetch b.branches branch left join fetch branch.zones z where b.userId= :userId
//            """)
    @Query("""
                SELECT DISTINCT b FROM BistroEntity b
                LEFT JOIN FETCH b.branches br
                WHERE b.userId = :userId
            """)
    List<BistroEntity> findByUserId(UUID userId);

    @Query("""
                SELECT  b
                FROM BistroEntity b 
                LEFT JOIN FETCH b.branches 
                WHERE b.bistroId = :bistroId
            """)
    Optional<BistroEntity> findByBistroId(UUID bistroId);

    @Query("Select b From BistroEntity b Left join Fetch b.menuEntities m Where b.userId= :userId Order By b.bistroName")
    List<BistroEntity> findAllBistroWithMenuByUserId(UUID userId);


//    public Optional<BistroEntity> findBistroWithBranches(@Param("bistroId") UUID bistroId);
}
