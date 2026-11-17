package com.bistral.app.bistral_bistro_service.entity;

import com.bistral.app.bistral_bistro_service.enums.SubDomainType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Subdomain for tenant and bistro is used to route customer to its respective resource. <br>
 * <note>
 * A subdomain for tenant or bistro must be unique globally.
 * A subdomain for bistro is mostly use for customer routing where as for tenant user will use tenant subdomain for there business operation. <br>
 * <b>Example</b>
 * <ul>
 *     <li>admin/abc.bistral.com <b>Here abc is tenant</b></li>
 *     <li>xyz.bistral.com <b>here xyz is bistro of abc</b></li>
 * </ul>
 * </note>
 * <notes>
 *     Pair (subDomain,DeletedAt) must be unique
 * </notes>
 *
 */
@Getter
@Setter
@Entity
@Builder
public class SubDomainMapping extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID subDomainMappingId;

    @Column(nullable = false)
    private String subDomain;

    @Enumerated(EnumType.STRING)
    private SubDomainType subDomainType;

    @OneToOne
    @JoinColumn(nullable = false, name = "tenant_id")
    private Tenant tenant;

    @OneToOne()
    @JoinColumn(name = "bistro_id")
    private Bistro bistro;

}
