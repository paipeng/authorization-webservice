package com.paipeng.authorization.service;

import com.paipeng.authorization.util.CommonUtil;
import com.paipeng.authorization.util.ImageUtil;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ImageService extends BaseService {
    private final static Logger logger = LogManager.getLogger(ImageService.class.getSimpleName());
    private static final String IMAGE_PATH = "/authorizations/";

    public byte[] getImage(String imageName) throws IOException {
        String savePath = CommonUtil.getProjHomePath() + IMAGE_PATH + imageName;
        logger.info("getImageFullPath: " + imageName);
        File file = new File(savePath);
        InputStream is = new FileInputStream(file);
        return IOUtils.toByteArray(is);
    }

    public String saveImage(String imageBase64) throws Exception {
        String dateString = CommonUtil.getSystemTime("yyyyMM");
        String savePath = CommonUtil.getProjHomePath() + IMAGE_PATH + dateString;
        String filePath = ImageUtil.saveBase64ImageToLocal(imageBase64, savePath);
        return dateString + "/" + filePath;
    }


}
