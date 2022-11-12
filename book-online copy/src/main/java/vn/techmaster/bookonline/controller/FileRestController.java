package vn.techmaster.bookonline.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import vn.techmaster.bookonline.service.FileService;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class FileRestController {
    private FileService fileService;

    @GetMapping(value = "upload/book-thumbnails/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] readBookThumbnail(@PathVariable("imageName") String imageName) {
        Path path = Paths.get("upload/book-thumbnails").resolve(imageName);
        return fileService.readImage(path);
    }

    @GetMapping(value = "upload/user-avatars/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] readUserAvatar(@PathVariable("imageName") String imageName) {
        Path path = Paths.get("upload/user-avatars").resolve(imageName);
        System.out.println("Success");
        return fileService.readImage(path);
    }
}
