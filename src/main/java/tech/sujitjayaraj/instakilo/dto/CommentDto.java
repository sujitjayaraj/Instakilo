package tech.sujitjayaraj.instakilo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {

    @NotBlank
    @Size(max = 500)
    private String message;

    private Long postID;
}
