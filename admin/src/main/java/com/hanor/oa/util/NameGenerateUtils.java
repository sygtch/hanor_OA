package com.hanor.oa.util;

import java.util.Random;


public class NameGenerateUtils {

    //生成随机文件名，防止上传文件后文件名重复
    public static String generateRandomFilename(String fileName){

        String RandomFilename = "";
        Random rand = new Random();//生成随机数
        int random = rand.nextInt();
        RandomFilename = fileName.hashCode() + System.currentTimeMillis() + String.valueOf(random > 0 ? random : ( -1) * random);

        return RandomFilename;
    }
}