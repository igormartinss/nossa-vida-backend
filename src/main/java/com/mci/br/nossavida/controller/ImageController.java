package com.mci.br.nossavida.controller;

import com.mci.br.nossavida.model.Image;
import com.mci.br.nossavida.model.Period;
import com.mci.br.nossavida.service.ImageService;
import com.mci.br.nossavida.service.PeriodService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    private final PeriodService periodService;

    @Autowired
    public ImageController(ImageService imageService, PeriodService periodService) {
        this.imageService = imageService;
        this.periodService = periodService;
    }

    @GetMapping
    public ResponseEntity<List<Image>> getImages() {
        return new ResponseEntity<>(imageService.getImages(), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Image> getImage(@PathVariable final Long id) {
        return new ResponseEntity<>(imageService.getImage(id), HttpStatus.OK);
    }

    @PostMapping(path = "/{periodId}")
    public ResponseEntity<Image> addPhoto(@PathVariable Long periodId, @RequestParam("photo") Part photo) {
        Period period = periodService.getPeriod(periodId);
        Image imageToAdd = new Image();
        imageToAdd.setPeriod(period);
        try {
            InputStream is = photo.getInputStream();
            byte[] bytes = new byte[(int)photo.getSize()];
            IOUtils.readFully(is, bytes);
            imageToAdd.setImage(bytes);
            imageService.addImage(imageToAdd);
            is.close();
            return new ResponseEntity<>(imageToAdd, HttpStatus.OK);
        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Image> deleteImage(@PathVariable final Long id) {
        Image image = imageService.deleteImage(id);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<Image> editImage(@PathVariable final Long id, @RequestBody final Image image) {
        Image imageToEdit = imageService.editImage(id, image);
        return new ResponseEntity<>(imageToEdit, HttpStatus.OK);
    }

}
