package tech.sujitjayaraj.instakilo.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreatePostDto {

    @Size(max = 500)
    private String description;

    MultipartFile image;
}
