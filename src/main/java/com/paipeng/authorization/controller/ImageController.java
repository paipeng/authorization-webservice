package com.paipeng.authorization.controller;

import com.paipeng.authorization.service.ImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/images")
public class ImageController {
    private final static Logger logger = LogManager.getLogger(ImageController.class.getSimpleName());

    @Autowired
    private ImageService imageService;

    //@UserAnnotation(roles = {EnumUtils.Role.SALESPERSON, EnumUtils.Role.ADMINISTRATOR, EnumUtils.Role.DEVELOPER})
    @GetMapping(value = "/authorizations/{date}/{imageName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] getImage(@PathVariable("date") String date, @PathVariable("imageName") String imageName) throws IOException {
        return imageService.getImage(date + "/" + imageName);
    }
}
