package eight.java.base64.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @ClassName: Base64Utils
 * @Author: WuXiangShuai
 * @Time: 13:51 2019/5/24.
 * @Description:
 */
public class Base64Utils {

    /**
     * URL和文件安全型 base 64 解码方案
     * @param src
     * @return
     */
    public byte[] urlDecoder(String src){
        return Base64.getUrlDecoder().decode(src);
    }

    /**
     * URL和文件安全型 base 64 编码方案
     * @param src
     * @return
     */
    public String urlEncoder(byte[] src){
        return Base64.getUrlEncoder().encodeToString(src);
    }

    /**
     * MIME 类型的base64编码
     * 使用指定的行长度和线分隔的MIME类型base64编码方案
     * @param src
     * @param len
     * @param cut
     * @return
     * @throws UnsupportedEncodingException
     */
    public String mimeEncoderLen(byte[] src, int len, String cut) throws UnsupportedEncodingException {
        return Base64.getMimeEncoder(len, cut.getBytes("utf-8")).encodeToString(src);
    }

    /**
     * MIME 类型的base64解码
     */
    public byte[] mimeDecoder(String src){
        return Base64.getMimeDecoder().decode(src);
    }

    /**
     * MIME 类型的base64编码
     */
    public String mimeEncoder(byte[] src){
        return Base64.getMimeEncoder().encodeToString(src);
    }


    /**
     * 基本类型的base64解码
     */
    public byte[] baseDecoder(String src){
        return Base64.getDecoder().decode(src);
    }

    /**
     * 基本类型的base64编码
     */
    public String baseEncoder(byte[] src){
        return Base64.getEncoder().encodeToString(src);
    }

}
