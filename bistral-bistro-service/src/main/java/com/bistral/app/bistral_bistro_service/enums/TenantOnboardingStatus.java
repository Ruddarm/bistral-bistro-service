package com.bistral.app.bistral_bistro_service.enums;


/**
 * Stage of tenant onboarding.
 * <ol>
 *     <li>TENANT_CREATED : Tenant created by A system user to onboard tenant</li>
 *     <li>USER_CREATED : After tenant got create System user will create first User in Bistral-auth-service</li>
 *     <li>USER_INVITED : Once user is created he will get invitation link activate tenant , and his account.</li>
 *     <li>ONBOARDED : Specify tenant onboarded  successfully</li>
 * </ol>
 *
 *
 */
public enum TenantOnboardingStatus {
    TENANT_CREATED,
    USER_CREATED,
    USER_INVITED,
    ONBOARDED
}
