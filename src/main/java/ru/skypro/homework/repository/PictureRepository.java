package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.entity.Picture;

import java.util.Optional;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {
    Optional<Picture> findByFilePath(String filePath);
    void deleteByAdId(Integer adId);
}
