package com.one.exercise.utils;

import com.one.exercise.enums.ImageFormat;
import com.one.exercise.exception.NotCorrectImageFormatException;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class FileUpDownLoad {

    public static String upFile(){
        return null;
    }

    public static String[] upFiles(){

        return null;
    }

    public static String upImage(){
        return null;
    }

    /**
     * 多图片上传
     * @param files
     * @param savePath 保存的本地路径
     * @param webPrefix 网络前缀
     * @return
     * @throws NullPointerException 空参数异常
     * @throws NotCorrectImageFormatException 不符合预期的图片格式
     * @throws IOException 数据写入出错
     */
    public static List<String> upImages(MultipartFile[] files, String savePath, String webPrefix) throws NullPointerException, NotCorrectImageFormatException, IOException {
        if (files == null || files.length == 0 ||
                savePath == null || savePath.trim().length() == 0
        ){
            throw new NullPointerException("files or savePath parameter possible is null");
        }

        List<String> filenameList = new ArrayList<>();
        for (MultipartFile file : files) {

            // 获取文件原始名称
            String filename = file.getOriginalFilename();

            // 不是正确的图片格式
            if (!ImageFormat.isFormat(filename)){
                throw new NotCorrectImageFormatException();
            }

            // 创建文件对象
            String returnFilename = UUID.randomUUID().toString() + UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
            File saveFile = new File(savePath + returnFilename);

            // 将数据写入文件对象
            file.transferTo(saveFile);

            // 判断图片内容是否正确
            BufferedImage bi = ImageIO.read(saveFile);
            if(bi == null){
                saveFile.delete();
                throw new NotCorrectImageFormatException();
            }

            filenameList.add(webPrefix + returnFilename);
        }
        return filenameList;
    }
}
