package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.config.FileConfig;
import com.itheima.reggie.enums.BizExceptionEnum;
import com.itheima.reggie.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.UUID;

/**
 * 实现图片上传/访问/下载
 */
@Slf4j
@RestController
@RequestMapping("/common")
public class FileCommonController {

    private final FileConfig fileConfig;

    private final Path uploadDir;

    @Autowired
    public FileCommonController(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
        this.uploadDir = Paths.get(fileConfig.getBasePath());
        log.info("Base path: " + fileConfig.getBasePath()); // 输出basePath检查是否为空
    }

    /**
     * 处理文件上传请求。
     * @param file 用户上传的文件
     * @return 返回文件上传的状态信息
     */
    @PostMapping("/upload")
    public R<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        //获取原始文件名。假设原始文件名：abc.jpg
        String originalFilename = file.getOriginalFilename(); //NOSONAR
        //截取文件名后缀，从最后一个点的位置开始截取(包含点)，截取到结尾的字符串
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")); //NOSONAR

        //使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String newfileName = UUID.randomUUID().toString() + suffix;//就要在生成的随机名称后加上文件后缀：xxx.jpg

        try{
            // 确保上传目录存在
            Files.createDirectories(uploadDir);

            // 构建目标文件路径
            Path targetPath = uploadDir.resolve(newfileName);

            // 将上传的文件写入磁盘
            Files.write(targetPath, file.getBytes(), StandardOpenOption.CREATE_NEW);
        }catch (IOException e){
            throw new BizException(BizExceptionEnum.FILE_UPLOAD_ERROR);
        }
        // 返回成功消息
        return R.success(newfileName);
    }

    /**
     * 提供文件下载服务。
     * @param name 文件名
     * @param response HTTP响应对象
     */
    @GetMapping("download")
    public void downloadFile(@RequestParam("name") String name, HttpServletResponse response) {
        File file = new File(fileConfig.getBasePath() + name);

        if(file.exists()){ // 如果文件存在
            try {
                // 设置响应类型为二进制流
                response.setContentType("application/octet-stream");

                // 设置Content-Disposition头，指示浏览器下载文件
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"");

                // 编码文件名以适应不同的浏览器
                String encodedFilename = UriUtils.encode(name, StandardCharsets.UTF_8);
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename*=UTF-8''" + encodedFilename);

                // 使用FileSystemResource读取文件
                FileSystemResource fileSystemResource = new FileSystemResource(file);

                // 将文件内容写入HTTP响应输出流
                FileCopyUtils.copy(fileSystemResource.getInputStream(), response.getOutputStream());

                // 完成响应
                response.flushBuffer();
            } catch (IOException e) {
                // 处理异常情况
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else { // 如果文件不存在
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 返回404 Not Found
        }
    }

    /**
     * 访问
     * @param name 文件名
     * @return 二进制
     */
    @PostMapping("/access")
    public void download(String name, HttpServletResponse response){

        try (FileInputStream fileInputStream = new FileInputStream(new File(fileConfig.getBasePath() + name));){
            //输入流，通过输入流读取文件内容


            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            //设置响应的内容类型
            response.setContentType("image/jpeg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}