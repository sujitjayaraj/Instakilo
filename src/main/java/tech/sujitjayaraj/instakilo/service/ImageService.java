package tech.sujitjayaraj.instakilo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.sujitjayaraj.instakilo.entity.Image;
import tech.sujitjayaraj.instakilo.repository.ImageRepository;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image save(byte[] data, String filename) {
        Image image = new Image();
        image.setData(data);
        image.setFilename(filename);
        image = imageRepository.save(image);

        return image;
    }
}
