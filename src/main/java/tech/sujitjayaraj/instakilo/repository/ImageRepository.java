package tech.sujitjayaraj.instakilo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.sujitjayaraj.instakilo.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
