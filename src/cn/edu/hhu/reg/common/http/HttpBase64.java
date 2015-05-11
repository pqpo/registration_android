package cn.edu.hhu.reg.common.http;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class HttpBase64 {
    private final static char[] ALPHABET = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/',
    };
    
    private final static int[] INV = new int[256];
    
    static {
        Arrays.fill(INV, -1);
        for(int i = 0; i < ALPHABET.length;i ++) {
            INV[ALPHABET[i]] = i;
        }
    }

    /**
     * 改进的Base64编码
     * @param data 需要编码的字符串
     * @return 编码后的字符串
     */
    public static String encode(String s) {
        try {
            return encode(s.getBytes("UTF-8"), true);
        } catch (UnsupportedEncodingException ignore) {
            return "";
        }
    }
    
    /**
     * 改进的Base64编码
     * @param data 需要编码的字符串
     * @param separator 是否换行
     * @return 编码后的字符串
     */
    public static String encode(String s, boolean separator) {
        try {
            return encode(s.getBytes("UTF-8"), separator);
        } catch (UnsupportedEncodingException ignore) {
            return "";
        }
    }
    
    /**
     * 改进的Base64编码
     * @param data 需要编码的字节组
     * @param separator 是否换行
     * @return 编码后的字符串
     */
    public static String encode(byte[] data, boolean separator) {
        if(data == null) return "";
        int len = data.length;
        int even = (len / 3) * 3;
        int cnt = even / 3 * 4;
        int left = len - even; // 0 - 2.
        int destLen = cnt + (separator ? (cnt - 1) / 64 * 2 : 0) + (left > 0 ? left + 1: 0);
        char[] dest = new char[destLen];
        
        for(int s = 0, d = 0, cc = 0; s < even;) {
            int i = (data[s ++] & 0xff) << 16 | (data[s++] & 0xff) << 8 | (data[s ++] & 0xff);
            dest[d ++] = ALPHABET[(i >>> 18) & 0x3f];
            dest[d ++] = ALPHABET[(i >>> 12) & 0x3f];
            dest[d ++] = ALPHABET[(i >>> 6) & 0x3f];
            dest[d ++] = ALPHABET[i & 0x3f];
            if(separator && (++ cc == 16) && (d < (destLen - 2))) {
                dest[d ++] = '\r';
                dest[d ++] = '\n';
                cc = 0;
            }
        }

        if(left > 0) {
            int i = ((data[even] & 0xff) << 10) | (left == 2 ? ((data[len - 1] & 0xff) << 2) : 0);
            if(left == 1) {
                dest[destLen - 2] = ALPHABET[i >> 12];
                dest[destLen - 1] = ALPHABET[(i >>> 6) & 0x3f];
            } else {
                dest[destLen - 3] = ALPHABET[i >> 12];
                dest[destLen - 2] = ALPHABET[(i >>> 6) & 0x3f];
                dest[destLen - 1] = ALPHABET[i & 0x3f];
            }
        }
        return new String(dest);
    }
    
    /**
     * 改进的Base64解码
     * @param string 需要解码的字符串
     * @return 解码后的字符串
     */
    public static String decodeToString(String string) {
        if(string == null) return "";
        return new String(decode(string));
    }
    
    /**
     * 改进的Base64解码
     * @param string 需要解码的字符串
     * @return 解码后的字节组
     */
    public static byte[] decode(String string) {
        if(string == null) return new byte[0];
        return decode(string.getBytes());
    }
    
    /**
     * 改进的Base64解码
     * @param data 需要解码的字节组
     * @return 解码后的字节组
     */
    public static byte[] decode(byte[] data) {
        if(data == null || data.length == 0) return new byte[0];
        int length = data.length;

         int sndx = 0, endx = length - 1;
         int cnt = endx - sndx + 1;
         int sepCnt = length > 64 ? (data[64] == '\r' ? cnt / 64 : 0) * 2 : 0;
         int len = ((cnt - sepCnt) * 6 / 8);
         byte[] dest = new byte[len];
         
         int d = 0;
         for(int cc = 0, eLen = (len / 3) * 3; d < eLen;) {
             int i = INV[data[sndx ++]] << 18 | INV[data[sndx ++]] << 12 | INV[data[sndx ++]] << 6
                     | INV[data[sndx ++]];
             dest[d ++] = (byte) (i >> 16);
             dest[d ++] = (byte) (i >> 8);
             dest[d ++] = (byte) i;
             if(sepCnt > 0 && ++ cc == 16) {
                 sndx += 2;
                 cc = 0;
             }
         }
         if(d < len) {
             int i = 0;
             for(int j = 0; sndx <= endx; j++) {
                 i |= INV[data[sndx++]] << (18 - j * 6);
             }
             for(int r = 16; d < len; r -= 8) {
                 dest[d++] = (byte) (i >> r);
             }
         }
         return dest;
     }
}