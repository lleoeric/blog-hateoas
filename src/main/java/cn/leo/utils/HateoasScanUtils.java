package cn.leo.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class HateoasScanUtils {
    public static void main(String[] args) {
        List<String> strings = scanForDir("F:\\java\\git\\blog-hateoas\\src\\main\\java\\cn\\leo\\controller");
        for (String string : strings) {
            System.out.println(string);
        }

    }

    private static List<String> scanForDir(String dirPath) {
        List<String> classNames = new ArrayList<>();
        //根据传入文件夹路径创建File对象
        File dir = new File(dirPath);
        //检查是否为文件夹
        if (dir.isDirectory()) {
            //遍历文件夹内的文件
            for (File f : Objects.requireNonNull(dir.listFiles())) {
                //获取文件名,并删除后缀
                String fileName = f.getName();
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
                //添加到结果中
                classNames.add(fileName);
            }
        }
        return classNames;
    }

}
