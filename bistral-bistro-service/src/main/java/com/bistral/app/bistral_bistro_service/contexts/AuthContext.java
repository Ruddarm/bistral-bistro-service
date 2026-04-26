package com.bistral.app.bistral_bistro_service.contexts;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
public class AuthContext {

    private UUID userId;
    private UUID bistroId;
    private UUID branchId;
    private UUID roleId;
    private Set<String> permissions;
}
