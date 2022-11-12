package com.foodei.project.config;


import org.hibernate.bytecode.internal.bytebuddy.PassThroughInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    @Value("${photo.path}")
    private String photoPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        Path imageUploadDir = Paths.get("./uploads");
//        String imageUploadPath = imageUploadDir.toFile().getAbsolutePath();
//        registry.addResourceHandler("uploads/**").addResourceLocations("file:" + imageUploadPath + "/");
        registry.addResourceHandler("/" + photoPath + "/**").addResourceLocations("file:" + uploadPath + "/");
    }

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        exposeDirectory("uploads", registry);
//
//    }
//    private void exposeDirectory(String pathPattern, ResourceHandlerRegistry registry) {
//        Path path = Paths.get(pathPattern) ;
//        String absolutePath = path.toFile().getAbsolutePath() ;
//        String logicalPath = pathPattern.replace("../" , "") + "/**" ;
//        registry.addResourceHandler(logicalPath)
//                .addResourceLocations("file:/" + absolutePath + "/") ;
//    }
}
