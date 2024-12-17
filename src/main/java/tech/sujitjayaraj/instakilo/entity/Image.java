package tech.sujitjayaraj.instakilo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String filename;

    @Lob
    private byte[] data;

    Instant createdAt;

    @PrePersist
    public void setCreated() {
        this.createdAt = Instant.now();
    }
}
