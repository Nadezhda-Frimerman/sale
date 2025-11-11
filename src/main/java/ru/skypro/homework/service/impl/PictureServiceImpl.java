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
import ru.skypro.homework.repository.PictureRepository;

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
public class PictureServiceImpl {
    @Value("${path.to.picture.folder}")
    private String pictureDir;

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

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

        Picture picture = new Picture();
        picture.setPictureOwner(pictureOwner);
        picture.setFilePath(filePath.toString());
        picture.setFileSize(file.getSize());
        picture.setMediaType(file.getContentType());
        picture.setData(generateImagePreview(filePath));
        return pictureRepository.save(picture);

//        switch (pictureOwner) {
//            case USER -> {
//                User owner = userServiceImpl.findUserById(ownerId);
//                owner.setImage(picture);
//            }
//            case AD -> {
//                Ad owner = adServiceImpl.findAdById(ownerId);
//                owner.setImage(picture);
//            }
//        }
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
}