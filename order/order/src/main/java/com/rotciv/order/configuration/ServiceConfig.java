package com.rotciv.order.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Data
@NoArgsConstructor
public class ServiceConfig {
    public static String productServiceUrl = "https://product-service-production-f217.up.railway.app/v1/api";

    public static String authServiceUrl = "https://auth-service-production-58c5.up.railway.app/v1/api/auth";

}
