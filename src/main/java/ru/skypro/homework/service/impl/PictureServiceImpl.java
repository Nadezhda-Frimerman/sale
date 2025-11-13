package ru.skypro.homework.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.entity.Picture;
import ru.skypro.homework.entity.PictureOwner;
import org.apache.commons.io.FilenameUtils;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.PictureRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.PictureService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import javax.transaction.Transactional;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    @Value("${path.to.picture.folder}")
    private String pictureDir;

    private final PictureRepository pictureRepository;
    private final UserRepository userRepository;
    private final AdRepository adRepository;

    public PictureServiceImpl(PictureRepository pictureRepository, UserRepository userRepository, AdRepository adRepository) {
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
        this.adRepository = adRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

//    TODO: unique naming
    public Picture uploadPicture(Integer ownerId, PictureOwner pictureOwner, MultipartFile file) throws IOException {
        logger.info("Was invoked method for uploading a picture");

        Path filePath = Path.of(pictureDir, String.valueOf(pictureOwner) + ownerId + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
            bis.transferTo(bos);
        }

        logger.info("Picture was transferred");
        Picture picture = new Picture();
        picture.setPictureOwner(pictureOwner);
        picture.setFilePath(filePath.toString());
        picture.setFileSize(file.getSize());
        picture.setMediaType(file.getContentType());
        picture.setData(generateImagePreview(filePath));

        switch (pictureOwner) {
            case USER -> picture.setUser(userRepository.findById(ownerId).orElseThrow());
            case AD -> picture.setAd(adRepository.findById(ownerId).orElseThrow());
        }

        logger.info("Picture was uploaded");
        return pictureRepository.save(picture);
    }

    public byte[] generateImagePreview(Path filePath) throws IOException {
        logger.info("Was invoked method for generating image preview");
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(preview, FilenameUtils.getExtension(filePath.getFileName().toString()), baos);
            logger.info("Image preview was generated");
            return baos.toByteArray();
        }
    }

    public Collection<Picture> getAll() {
        logger.info("Was invoked method for getting all pictures");
        return pictureRepository.findAll();
    }

    public Collection<Picture> getAllByPage(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method for getting all pictures by page");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return pictureRepository.findAll(pageRequest).getContent();
    }

    public void deleteByAdId(Integer adId) {
        pictureRepository.deleteByAdId(adId);
    }
}
