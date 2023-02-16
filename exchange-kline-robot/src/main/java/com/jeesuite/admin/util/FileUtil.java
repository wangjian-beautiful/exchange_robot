package com.jeesuite.admin.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;

/**
 * 文件管理
 *
 * @author kew
 * @create 2018-08-18 下午5:45
 **/
@Slf4j
@Service
public class FileUtil {

    @Value("save.path")
    private String savePath;

    public void writeFile(String pexPath, String fileName, String value) {
        FileOutputStream out = null;
        try {
            File pex = new File(pexPath);
            if (!pex.exists()) {
                if (!pex.mkdirs()) {
                    throw new Exception("创建报文失败,请检查路径");
                }
            }
            if (StringUtils.isEmpty(fileName)) {
                return;
            }
            File file = new File(pexPath + fileName);
            if (file.exists()) {
                file.delete();
            }
            if (!file.createNewFile()) {
                throw new Exception("创建报文失败,请检查路径");
            }
            out = new FileOutputStream(file, true);
            out.write(value.getBytes("UTF-8"));
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("保存或创建文件失败", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new RuntimeException("创建报文失败,请检查路径", e);
                }
            }
        }
    }

    public String readFile(String fileName) throws FileNotFoundException {
        InputStream in=new FileInputStream(new File(savePath+fileName));
        BufferedReader br = null;
        StringBuffer sb = new StringBuffer();
        try {
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String temp = null;
            while ((temp = br.readLine()) != null) {
                sb.append(temp + "\r\n");
            }
        } catch (FileNotFoundException e) {
            log.error("读取文件失败，文件不存在:", e);
        } catch (IOException e) {
            log.error("读取文件失败", e);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("关闭文件", e);
                }
            }
        }
        return sb.toString();
    }
}
