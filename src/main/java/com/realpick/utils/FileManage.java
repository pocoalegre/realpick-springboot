package com.realpick.utils;

import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

/**
 * 文件相关操作工具类
 */
public class FileManage {

    /**
     * 使用uuid给文件更名，防止重复
     * @param fileName 文件名
     * @return 更名后字符串
     */
    public static String fileRename(String fileName){

        //生成uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");

        //拼接文件名
        String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        return uuid + suffix;
    }

    /**
     * 图片上传
     * @param file 图片
     * @param fileDir 上传目录
     * @return 执行结果
     * @throws IOException 异常
     */
    public static ResultVO fileUpload(MultipartFile file, String fileDir){
        try {
            //图片项目路径
            String targetPath = ClassUtils.getDefaultClassLoader().getResource(fileDir).getPath();

            //判断是否为图片格式
            String fileRealName = file.getOriginalFilename();

            //是图片，则重命名
            String fileRename = FileManage.fileRename(fileRealName);

            //完整路径
            File ToFilepath = new File(targetPath + "/" + fileRename);
            file.transferTo(ToFilepath);

            //返回
            return new ResultVO(StatusCode.OK, "上传成功！", fileRename);
        } catch (IOException e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "上传失败！", null);
        }
    }

    /**
     * 文件删除
     * @param FileName 文件名
     * @param fileDir 文件目录
     */
    public static ResultVO fileDelete(String FileName, String fileDir){

        //文件路径
        String targetPath = ClassUtils.getDefaultClassLoader().getResource(fileDir).getPath();

        //删除文件
        File file = new File(targetPath + "/" + FileName);

        try {
            file.delete();
            return new ResultVO(StatusCode.NO, "删除成功！", null);
        }catch (Exception e){
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "删除失败！", null);
        }

    }

    /**
     * 文件复制
     * @param FileName 文件名
     * @param fromFileDir 复制源路径
     * @param toFileDir 复制路径
     * @return 执行结果
     */
    public static ResultVO fileCopy(String FileName, String fromFileDir, String toFileDir){

        //输入流输出流
        FileInputStream fis = null;
        FileOutputStream fos = null;

        //文件路径
        String fromPath = ClassUtils.getDefaultClassLoader().getResource(fromFileDir).getPath() + "/" + FileName;
        String toPath = ClassUtils.getDefaultClassLoader().getResource(toFileDir).getPath() + "/" + FileName;

        try {
            //输入输出文件
            fis = new FileInputStream(fromPath);
            fos = new FileOutputStream(toPath);

            //判定器，是否读取完毕
            int temp = 0;
            while ((temp = fis.read()) != -1){
                //写入
                fos.write(temp);
            }
            return new ResultVO(StatusCode.OK, "复制成功！", null);
        } catch (IOException e) {
            System.out.println(e);
            return new ResultVO(StatusCode.NO, "复制异常！", null);
        }
    }

}
