package com.foodei.project.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {
    @RequestMapping(value = "getimage/{image}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("image") String image) {
        if (!image.equals("") || image != null) {
            try {
                Path fileName = Paths.get("uploads", image);
                byte [] buffer = Files.readAllBytes(fileName);
                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
                return ResponseEntity.ok().contentLength(buffer.length)
                        .contentType(MediaType.parseMediaType("image/jpeg")).body(byteArrayResource);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
