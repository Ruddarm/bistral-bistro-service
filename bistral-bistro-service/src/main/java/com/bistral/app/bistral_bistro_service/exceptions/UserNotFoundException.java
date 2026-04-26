package com.bistral.app.bistral_bistro_service.exceptions;

import java.util.UUID;



public class UserNotFoundException extends  Exception{
    private  UUID userId;

    public UserNotFoundException(UUID userId,String message)
    {
        super(message);
        this.userId= userId;
    }

}
