package TomcatDemoV2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 文件工具类：专门读取静态资源
 */
public class FileUtils {


    public static  String getFileContent(String path) throws  Exception {
        StringBuffer sbu = new  StringBuffer();
        FileReader fileReader = null;
        BufferedReader bufRed = null;
        //字符流
        try {
            fileReader = new FileReader(path);
            bufRed =new BufferedReader(fileReader);
            String line = null;

            while ((line=bufRed.readLine())!=null){
                sbu.append(line);
            }
        } finally {
            bufRed.close();
            fileReader.close();
        }


        return sbu.toString();
    }

}
