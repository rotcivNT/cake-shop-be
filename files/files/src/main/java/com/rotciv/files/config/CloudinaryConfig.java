package com.rotciv.files.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dwt8nm72d",
                "api_key", "118398793585282",
                "api_secret", "VPqOIM8CNQEVMAj6P_vCw5jl8sQ"));
    }
}
