package com.sw.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Security;

/*字符串 DESede(3DES) 加密
 * ECB模式/使用PKCS7方式填充不足位,目前给的密钥是192位
 * 3DES（即Triple DES）是DES向AES过渡的加密算法（1999年，NIST将3-DES指定为过渡的
 * 加密标准），是DES的一个更安全的变形。它以DES为基本模块，通过组合分组方法设计出分组加
 * 密算法，其具体实现如下：设Ek()和Dk()代表DES算法的加密和解密过程，K代表DES算法使用的
 * 密钥，P代表明文，C代表密表，这样，
 * 3DES加密过程为：C=Ek3(Dk2(Ek1(P)))
 * 3DES解密过程为：P=Dk1((EK2(Dk3(C)))
 * */
public class DecryptUtils {

    /**
     * @param args在java中调用sun公司提供的3DES加密解密算法时，需要使
     * 用到$JAVA_HOME/jre/lib/目录下如下的4个jar包：
     *jce.jar
     *security/US_export_policy.jar
     *security/local_policy.jar
     *ext/sunjce_provider.jar
     */

    private static final String Algorithm = "DESede"; //定义加密算法,可用 DES,DESede,Blowfish
    //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）
    public static byte[] encryptMode(byte[] keybyte,byte[] src){
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);//在单一方面的加密或解密
        } catch (java.security.NoSuchAlgorithmException e1) {
            // TODO: handle exception
            e1.printStackTrace();
        }catch(javax.crypto.NoSuchPaddingException e2){
            e2.printStackTrace();
        }catch(Exception e3){
            e3.printStackTrace();
        }
        return null;
    }

    //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    public static byte[] decryptMode(byte[] keybyte,byte[] src){
        try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            //解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            // TODO: handle exception
            e1.printStackTrace();
        }catch(javax.crypto.NoSuchPaddingException e2){
            e2.printStackTrace();
        }catch(Exception e3){
            e3.printStackTrace();
        }
        return null;
    }

    //转换成十六进制字符串
    public static String byte2Hex(byte[] b){
        String hs="";
        String stmp="";
        for(int n=0; n<b.length; n++){
            stmp = (Integer.toHexString(b[n]& 0XFF));
            if(stmp.length()==1){
                hs = hs + "0" + stmp;
            }else{
                hs = hs + stmp;
            }
            if(n<b.length-1)hs=hs+":";
        }
        return hs.toUpperCase();
    }
    public static byte[] hex(String key){
        String f = DigestUtils.md5Hex(key);
        byte[] bkeys = new String(f).getBytes();
        byte[] enk = new byte[24];
        for (int i=0;i<24;i++){
            enk[i] = bkeys[i];
        }
        return enk;
    }

    // 加密
    public static String encode(String src, String key) {
        byte[] enk = hex(key);
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        byte[] srcArrEnc = encryptMode(enk,src.getBytes());
//        String srcResult = Base64.encode(srcArrEnc);
        String srcResult = new Base64().encodeToString(srcArrEnc);
        return srcResult;
    }

    // 解密
    public static String decode(String src, String key) throws IOException {
        byte[] enk = hex(key);
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
//        byte[] srcDecodeArr = Base64.decode(src);
//        byte[] srcDecodeArr = new BASE64Decoder().decodeBuffer(src);
        byte[] srcDecodeArr = new Base64().decode(src);
        byte[] srcDecodeDec = decryptMode(enk, srcDecodeArr);
        String strResult = new String(srcDecodeDec);
        return strResult;
    }


    public static void main(String[] args) throws IOException {
        //添加新安全算法,如果用JCE就要把它添加进去
        byte[] enk = hex("YzQyNzIzYjEtMGQ4Ni00ZGExLTlhMTktNDAxYzhmZTY3NjNj");//用户名
        System.out.println(new String(enk));
        String password = "asdfasetawegaersdraesfesryeargaer4tsdfardgaretwasfartaweawtfwaetgawefgawtawerfgarwea";
        System.out.println("加密前的字符串:" + password);
        byte[] encoded = encryptMode(enk,password.getBytes());
//        String pword = new BASE64Encoder().encode(encoded);
        String pword = new Base64().encodeAsString(encoded);
        System.out.println("加密后的字符串:" + pword);

//        byte[] reqPassword = new BASE64Decoder().decodeBuffer("6Enb8hsfvil6qZQsmjZZLHFL6YYUqzmlgeJSjk5veJLH9YkOqIoR0I4YM12UCbO/zdoQJ6JoSS/KRrvc0j+XgpThAs5W+zmSrPUBUsof5AXZA9HvwrhN1g==");
        byte[] reqPassword = new Base64().decodeBase64("DV7I0ywtKoZ20s8JSnRVof+IAvWCZnvIFPTWc43llMT18jFikr2sgk69WhSbbGLK/r78kxjqYMiuytwUqrCrtyykHATke5VikcCOtuXJJbFne0R4TYzqJUEiG88Jzr1njhM3F719dz9FxmUPdgfILCX6j1zJG3ysABkcNLpv3B1Tz87xkdiFDh3qidTjacouGS4pXb8jk/zew07ttHNEp46G8Eni9kudESy/YP0dVk3IrEWrTyPFFI4TNxe9fXc/RcZlD3YHyCylI8GeYdTthAAZHDS6b9wdU8/O8ZHYhQ4d6onU42nKLjf6TstXrm9E3sNO7bRzRKeOhvBJ4vZLnREsv2D9HVZNPWRFyfaZrZgIeVdT1W+MJGxFzAyNqZPF2YrF8TyLNMfo/gzHxRLjUqDDH5yDfp96");
        byte[] srcBytes = decryptMode(enk,reqPassword);
        System.out.println("解密后的字符串:" + (new String(srcBytes)));


        //String field_encrypt_key = "IZ0Vqouwik1ufyE5y92CwvTdeKnGeLZqyXOp2RfaXWNzYh3RhgZS68OlwGx0TDwE";
        String field_encrypt_key = "Y6vAERDpwTNrrGHb7jZ0rXcLzvibR3m0Sm7d9a3bXrl8xedQjlOzusXQy31tf6Vt";
        //加密
        String encode = DecryptUtils.encode("18310843610", field_encrypt_key);
        //解密
        String decode = DecryptUtils.decode(encode,field_encrypt_key);
        System.out.println(encode);
        System.out.println(decode);
    }
}