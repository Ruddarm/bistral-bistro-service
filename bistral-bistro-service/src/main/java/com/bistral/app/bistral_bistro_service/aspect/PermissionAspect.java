package com.bistral.app.bistral_bistro_service.aspect;

import com.bistral.app.bistral_bistro_service.annotation.HasPermission;
import com.bistral.app.bistral_bistro_service.contexts.AuthContext;
import com.bistral.app.bistral_bistro_service.contexts.UserContextHolder;
import com.bistral.app.bistral_bistro_service.exceptions.UnauthorizedException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {

    @Before("@annotation(hasPermission)")
    public void checkPermission(HasPermission hasPermission) {
        System.err.println("Inside aspect");
        AuthContext authContext = UserContextHolder.getAuthContext();
        if (authContext == null) {
            throw new UnauthorizedException("Auth Context Not found..! Please Login and Try Again");
        }
        if (!authContext.getPermissions().contains(hasPermission.value())) {
            throw new UnauthorizedException("Does not have enough permission  " + hasPermission.value());
        }
    }
}
