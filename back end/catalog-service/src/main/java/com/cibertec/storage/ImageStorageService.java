package com.cibertec.storage;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageStorageService {
    public static Path rootPizza = Paths.get("resources","uploads", "pizzas");
    public static Path rootTopping = Paths.get("resources","uploads", "toppings");
    /**
     * Guarda una imagen en el tipo de almacenamiento especificado.
     *
     * @param image           Archivo de imagen a guardar (no debe ser nulo ni vacío).
     * @param fileName        Nombre base del archivo (sin extensión).
     * @param typeStorageEnum Tipo de almacenamiento (PIZZA o TOPPING).
     * @throws IOException    Si ocurre un error al guardar la imagen.
     * @throws RuntimeException Si el tipo de almacenamiento es inválido, la imagen es vacía o no se puede leer.
     *
     * <p>
     * El método realiza lo siguiente:
     * <ul>
     *   <li>Selecciona la carpeta de almacenamiento según el tipo (pizzas o ingredientes).</li>
     *   <li>Verifica que la imagen no sea nula ni vacía.</li>
     *   <li>Crea el directorio si no existe.</li>
     *   <li>Elimina archivos previos con el mismo nombre base.</li>
     *   <li>Lee la imagen y la guarda en formato JPG.</li>
     * </ul>
     * </p>
     */
    public void saveImage(MultipartFile image, String fileName, TypeStorageEnum typeStorageEnum) throws IOException {
        Path rootDir;
        switch (typeStorageEnum) {
            case PIZZA -> rootDir = rootPizza;
            case TOPPING -> rootDir = rootTopping;
            default -> throw new RuntimeException("Invalid storage type");
        }

        if (image == null || image.isEmpty()) {
            throw new RuntimeException("Image file is empty");
        }

        if (!Files.exists(rootDir)) {
            Files.createDirectories(rootDir);
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootDir, fileName + ".jpg")) {
            for (Path oldFile : stream) {
                Files.deleteIfExists(oldFile);
            }
        }

        BufferedImage originalImage = ImageIO.read(image.getInputStream());
        if (originalImage == null) {
            throw new RuntimeException("Failed to read image file (not a valid image)");
        }

        BufferedImage jpgImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        Graphics2D g2d = jpgImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, originalImage.getWidth(), originalImage.getHeight());
        g2d.drawImage(originalImage, 0, 0, null);
        g2d.dispose();

        String fileNameWithExt = fileName + ".jpg";
        Path fullPath = rootDir.resolve(fileNameWithExt);

        boolean written = ImageIO.write(jpgImage, "jpg", fullPath.toFile());
        if (!written) {
            throw new RuntimeException("Error writing image as JPG");
        }

    }
    public ResponseEntity<?> obtainImage(String image, TypeStorageEnum typeStorageEnum) {
        Path filePath;
        switch (typeStorageEnum) {
            case PIZZA -> filePath = ImageStorageService.rootPizza.resolve(image);
            case TOPPING -> filePath = ImageStorageService.rootTopping.resolve(image);
            default -> throw new RuntimeException("Tipo de almacenamiento inválido");
        }

        Resource resource;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("No se puede leer la imagen: " + image);
        }
        String contentType = URLConnection.guessContentTypeFromName(image);
        if(contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
