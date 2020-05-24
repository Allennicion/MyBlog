package com.cy.blog.util;

import com.cy.blog.vo.AjaxJson;
import com.cy.blog.vo.Global;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class UploadUtil {

    /**
     * 将接收的base64转换成图片保存
     *
     * @param imgByte
     *            base64数据
     * @return 成功返回图片保存路径，失败返回false
     */
    public static AjaxJson saveToImgByStr(String imgByte) {

        AjaxJson aj = new AjaxJson();

        String uploadPath = Global.UPLOADPATH;
        imgByte=imgByte.replaceAll("data:image/jpeg;base64,","");
        BASE64Decoder decoder =  new BASE64Decoder();
        byte[] imageByte = null;
        try{
            imageByte = decoder.decodeBuffer(imgByte);
            for (int i = 0; i < imageByte.length; ++i) {
                if (imageByte[i] < 0) {// 调整异常数据
                    imageByte[i] += 256;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (imageByte.length>0) {
            try {
                //获取文件上传的真实路径
                //String uploadPath = request.getSession().getServletContext().getRealPath("/");

                //保存文件的路径
                String filepath = "/images/upload/" + createNewDir();
                File destfile = new File(uploadPath + filepath);
                if (!destfile.exists()) {
                    destfile.mkdirs();
                }
                //文件新名称
                String fileNameNew = getFileNameNew() + ".jpg";
                aj.setData(filepath+"/"+fileNameNew);
                File f = new File(destfile.getAbsoluteFile() + File.separator + fileNameNew);
                // 将字符串转换成二进制，用于显示图片
                // 将上面生成的图片格式字符串 imgStr，还原成图片显示
                InputStream in = new ByteArrayInputStream(imageByte);
                FileOutputStream fos = new FileOutputStream(f);
                // BufferedOutputStream bos = new BufferedOutputStream(fos);
                byte[] buf = new byte[1024];
                int length;
                length = in.read(buf, 0, buf.length);

                while (length != -1) {
                    fos.write(buf,0,length);
                    length = in.read(buf);
                }
                fos.flush();
                fos.close();
                in.close();
                String lastpath = filepath + File.separator + fileNameNew;
                System.out.println("返回图片路径：" + lastpath);
                aj.setStatus(200);

                return aj;

            } catch (Exception e) {
                aj.setStatus(201);
                e.printStackTrace();
            } finally {
            }
        }
        return aj;
    }


    /**
     * 为文件重新命名，命名规则为当前系统时间毫秒数
     */
    private static String getFileNameNew() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return fmt.format(new Date());
    }

    /**
     * 以当前日期为名，创建新文件夹
     */
    private static String createNewDir() {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(new Date());
    }


}
