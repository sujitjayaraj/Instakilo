package tech.sujitjayaraj.instakilo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.sujitjayaraj.instakilo.entity.Image;
import tech.sujitjayaraj.instakilo.service.ImageService;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        byte[] data = file.getBytes();
        Image image = imageService.save(data, file.getOriginalFilename());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/images/{id}")
                .buildAndExpand(image.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
