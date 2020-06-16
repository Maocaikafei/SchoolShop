package com.mckf.myshop.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-11 14:36
 **/
public class ImageUtil {
    //basePath用于项目根路径，来获取放在resources下的水印图片
    public static String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    /**
     * 将CommonsMultipartFile转换成File类
     *
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IllegalStateException e) {
            logger.error(e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }
    /**
    * @Description:处理（或者说生成）缩略图，并返回新生成图片的相对值路径
    * @Param: [thumbnail, targetAddr]targetAddr是文件存储路径，CommonsMultipartFile是spring自带的文件处理对象，
    * @return: java.lang.String
    * @Author: 冒菜咖啡
    * @Date: 2020/6/11 15:08
    */
    public static String generateThumbnail(File thumbnail,String targetAddr)
    {
        // 获取不重复的随机名
        String realFileName = getRandomFileName();
        // 获取文件的扩展名如png,jpg等
        String extension = getFileExtension(thumbnail);
        // 如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        // 获取文件存储的相对路径(带文件名)
        String relativeAddr=targetAddr+realFileName+extension;
        // 获取文件要保存到的目标路径，Java文件类以抽象的方式代表文件名和目录路径名
        File dest=new File(PathUtil.getImgBasePath()+relativeAddr);
        logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("basePath is :" + basePath);
        try
        {
            // 调用Thumbnails生成带有水印的图片
            Thumbnails.of(thumbnail)
                    .size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File(basePath+"/watermark.jpg")),
                            0.25f).outputQuality(0.8f)
                    .toFile(dest);
        }
        catch (IOException e)
        {
            logger.error(e.toString());
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        // 返回图片相对路径地址，选择相对路径是考虑到程序的移植性，因为这个数据会存在数据库中
        return relativeAddr;
    }
    /**
     * 创建目标路径所涉及到的目录，即/home/work/xiangze/xxx.jpg, 那么home work xiangze
     * 这三个文件夹都得自动创建
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     *
     * @param
     * @return
     */
    private static String getFileExtension(File cFile) {
        String originalFileName=cFile.getName();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }
    /**
     * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
     *
     * @return
     */
    public static String getRandomFileName() {
        // 获取随机的五位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }
    public static void main(String[] args) throws IOException {
        //此函数将输出一张缩略图
        Thumbnails.of(new File("F:/PS4/Horizon01.png"))
                .size(200,200)
                .watermark(Positions.BOTTOM_RIGHT,
                        ImageIO.read(new File(basePath+"/watermark.jpg")),
                        0.25f).outputQuality(0.8f)
                .toFile("F:/PS4/newHorizon01.png");
    }
}
