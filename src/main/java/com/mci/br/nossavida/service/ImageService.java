package com.mci.br.nossavida.service;

import com.mci.br.nossavida.model.Image;
import com.mci.br.nossavida.model.Period;
import com.mci.br.nossavida.model.exception.ImageNotFoundException;
import com.mci.br.nossavida.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image addImage(Image image) {
        return imageRepository.save(image);
    }

    public List<Image> getImages() {
        return StreamSupport
                .stream(imageRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public Image getImage(Long id) {
        return imageRepository.findById(id).orElseThrow(()-> new ImageNotFoundException(id));
    }

    @Transactional
    public Image editImage(Long id, Image image) {
        Image imageToEdit = getImage(id);
        imageToEdit.setImage(image.getImage());
        imageToEdit.setPeriod(image.getPeriod());
        return imageToEdit;
    }

    public Image deleteImage(Long id) {
        Image imageToDelete = getImage(id);
        imageRepository.delete(imageToDelete);
        return imageToDelete;
    }

    public List<byte[]> getImagesByPeriod(Period period) {
        List<Image> images = imageRepository.findAllByPeriodId(period.getId());
        List<byte[]> imageByte = new ArrayList<>();
        for(Image image : images) {
            imageByte.add(image.getImage());
        }
        return imageByte;
    }

    public byte[] getFirstImageByPeriod(Period period) {
        Image image = imageRepository.findFirstByPeriodId(period.getId()).orElse(null);
        return image != null ? image.getImage() : new byte[0];
    }

    @Transactional
    public void deleteImageByPeriod(Period period) {
        imageRepository.deleteByPeriodId(period.getId());
    }
}
