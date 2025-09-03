package com.bistral.app.bistral_bistro_service.dtos;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

@Builder
@Data
public class BistroRequest {

    @NotEmpty
    private  String bistroName;

    private String logoUrl;

    private String address;
}
