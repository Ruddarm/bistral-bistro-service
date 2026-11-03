package com.bistral.app.bistral_bistro_service.repository;

import com.bistral.app.bistral_bistro_service.entity.Bistro;
import com.bistral.app.bistral_bistro_service.projection.BistroBranchContextProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface BistroRepository extends JpaRepository<Bistro, UUID> {
    @Query("""
            SELECT DISTINCT b
            FROM Bistro b
            LEFT JOIN FETCH b.branches br
            WHERE b.bistroId = :bistroId
            """)
    Optional<Bistro> findBistroWithBranches(UUID bistroId);

    //
//    @Query("""
//            Select b.bistroId, b.bistroName , branch.branchId,branch.branchName from  Bistro b Left join Fetch b.branches branch left join fetch branch.zones z where b.userId= :userId
//            """)
    @Query("""
                SELECT DISTINCT b FROM Bistro b
                LEFT JOIN FETCH b.branches br
                WHERE b.userId = :userId
            """)
    List<Bistro> findByUserId(UUID userId);

    @Query("""
                SELECT  b
                FROM Bistro b 
                LEFT JOIN FETCH b.branches 
                WHERE b.bistroId = :bistroId
            """)
    Optional<Bistro> findByBistroId(UUID bistroId);

    @Query("Select b From Bistro b Left join Fetch b.menuEntities m Where b.userId= :userId Order By b.bistroName")
    List<Bistro> findAllBistroWithMenuByUserId(UUID userId);

    @Query("""
            Select new 
            com.bistral.app.bistral_bistro_service.projection.BistroBranchContextProjection(
            b.bistroId,
            b.bistroName,
            br.branchId,
            br.branchName
            )
            From Bistro  b
            Left Join Fetch  BranchEntity br
            on b.bistroId = br.bistro.bistroId
            where b.bistroId in (:bistroIds) and
            br.branchId in (:branchIds)
            """)
    List<BistroBranchContextProjection> getBistroContext(List<UUID> bistroIds,
                                                         List<UUID> branchIds);

}
