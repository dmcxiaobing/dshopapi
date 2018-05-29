package com.david.dshopapi.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加解密工具
 */
public class EncryptUtil {

    private EncryptUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 二进位组转十六进制字符串
     *
     * @param buf 二进位组
     * @return 十六进制字符串
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buf) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转二进位组
     *
     * @param hexStr 十六进制字符串
     * @return 二进位组
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];

        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * MD5加密
     *
     * @param string 加密字符串
     * @param slat   加密盐值key
     * @return 加密结果字符串
     */
    public static String md5(String string, String slat) {
        if (StringUtils.isEmpty(string)) return "";

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest((string + slat).getBytes("UTF-8"));
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * MD5加密
     *
     * @param string 加密字符串
     * @return 加密结果字符串
     * @see #md5(String, String)
     */
    public static String md5(String string) {
        return StringUtils.isEmpty(string) ? "" : md5(string, "");
    }

    /**
     * MD5的IO文件加密类型
     */
    public static final int MD5_TYPE_IO = 0;

    /**
     * MD5的NIO文件加密类型
     */
    public static final int MD5_TYPE_NIO = 1;

    /**
     * MD5加密
     *
     * @param file  加密文件
     * @param style 加密文件类型：{@link #MD5_TYPE_NIO} ，{@link #MD5_TYPE_IO}
     * @return 加密结果字符串
     */
    public static String md5(File file, int style) {
        if (file == null || !file.isFile() || !file.exists()) return "";

        FileInputStream in = null;
        String result = "";
        byte buffer[] = new byte[8192];
        int len;

        try {
            in = new FileInputStream(file);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest();

            if (style == MD5_TYPE_IO) {
                while ((len = in.read(buffer)) != -1) {
                    md5.update(buffer, 0, len);
                }
            } else {
                MappedByteBuffer byteBuffer = in.getChannel().map(
                        FileChannel.MapMode.READ_ONLY, 0, file.length());
                md5.update(byteBuffer);
            }

            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * MD5加密
     *
     * @param string 加密字符串
     * @param times  重复加密次数
     * @return 加密结果字符串
     */
    public static String md5(String string, int times) {
        if (StringUtils.isEmpty(string)) return "";

        String md5 = md5(string);
        for (int i = 0; i < times; i++) md5 = md5(md5);
        return md5;
    }

    /**
     * Base64加密
     *
     * @param string 加密字符串
     * @return 加密结果字符串
     */
    public static String base64EncodeStr(String string) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(string)) return "";
        return Base64Utils.encodeToString(string.getBytes("UTF-8"));
    }

    /**
     * Base64解密
     *
     * @param string 解密字符串
     * @return 解密结果字符串
     */
    public static String base64DecodedStr(String string) {
        if (StringUtils.isEmpty(string)) return "";
        return new String(Base64Utils.decodeFromString(string));
    }

    /**
     * Base64加密
     *
     * @param file 加密文件
     * @return 加密结果字符串
     */
    public static String base64EncodeFile(File file) {
        if (null == file) return "";

        try {
            FileInputStream inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64Utils.encodeToString(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Base64解密
     *
     * @param filePath 解密文件路径
     * @param code     解密文件编码
     * @return 解密结果文件
     */
    public static File base64DecodedFile(String filePath, String code) {
        if (StringUtils.isEmpty(filePath) || StringUtils.isEmpty(code)) {
            return null;
        }

        File desFile = new File(filePath);
        try {
            byte[] decodeBytes = Base64Utils.decode(code.getBytes("UTF-8"));
            FileOutputStream fos = new FileOutputStream(desFile);
            fos.write(decodeBytes);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return desFile;
    }

    /**
     * Des加密/解密
     *
     * @param content  字符串内容
     * @param password 密钥
     * @param type     加密：{@link Cipher#ENCRYPT_MODE}，解密：{@link Cipher#DECRYPT_MODE}
     * @return 加密/解密结果
     */
    public static String des(String content, String password, int type) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(type, keyFactory.generateSecret(desKey), random);

            if (type == Cipher.ENCRYPT_MODE) {
                byte[] byteContent = content.getBytes("utf-8");
                return parseByte2HexStr(cipher.doFinal(byteContent));
            } else {
                byte[] byteContent = parseHexStr2Byte(content);
                return new String(cipher.doFinal(byteContent));
            }
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException |
                UnsupportedEncodingException | InvalidKeyException | NoSuchPaddingException |
                InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sha加密类型
     */
    public final static String SHA224 = "sha-224";

    /**
     * Sha加密类型
     */
    public final static String SHA256 = "sha-256";

    /**
     * Sha加密类型
     */
    public final static String SHA384 = "sha-384";

    /**
     * Sha加密类型
     */
    public final static String SHA512 = "sha-512";

    /**
     * Sha加密
     *
     * @param string 加密字符串
     * @param type   加密类型 ：{@link #SHA224}，{@link #SHA256}，{@link #SHA384}，{@link #SHA512}
     * @return SHA加密结果字符串
     */
    public static String sha(String string, String type) {
        if (StringUtils.isEmpty(string)) return "";
        if (StringUtils.isEmpty(type)) type = SHA256;

        try {
            MessageDigest md5 = MessageDigest.getInstance(type);
            byte[] bytes = md5.digest((string).getBytes("UTF-8"));
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 随机获取密钥(公钥和私钥), 客户端公钥加密，服务器私钥解密
     *
     * @return 结果密钥对
     * @throws Exception
     */
    public static Map<String, Object> getKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<>(2);
        keyMap.put("publicKey", publicKey);
        keyMap.put("privateKey", privateKey);
        return keyMap;
    }

    /**
     * 获取公钥/私钥。。这里是将公钥和私钥对写死了
     *
     * @param keyMap      密钥对
     * @param isPublicKey true：获取公钥，false：获取私钥
     * @return 获取密钥字符串
     */
    public static String getKey(Map<String, Object> keyMap, boolean isPublicKey) {
        Key key = (Key) keyMap.get(isPublicKey ? "publicKey" : "privateKey");
        return new String(Base64Utils.encodeToString(key.getEncoded()));
    }

    /**
     * 获取数字签名
     *
     * @param data       二进制位
     * @param privateKey 私钥(BASE64编码)
     * @return 数字签名结果字符串
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64Utils.decode(privateKey.getBytes("UTF-8"));
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);

        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(privateK);
        signature.update(data);
        return new String(Base64Utils.encodeToString(signature.sign()));
    }

    /**
     * 数字签名校验
     *
     * @param data      二进位组
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名字符串
     * @return true：校验成功，false：校验失败
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64Utils.decode(publicKey.getBytes("UTF-8"));

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
        PublicKey publicK = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign.getBytes("UTF-8")));
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        // MD5 加密字符串加密
        String str = "123456";
        System.out.println("一次加密：" + EncryptUtil.md5(str));
        System.out.println("二次加密：" + EncryptUtil.md5(str, 2));
        System.out.println("盐值加密：" + EncryptUtil.md5(str, "78"));

        // Base64加密解密
        String baseStr = EncryptUtil.base64EncodeStr("123456");
        System.out.println("base64字符串加密：" + baseStr);
        System.out.println("base64字符串解密：" + EncryptUtil.base64DecodedStr(baseStr));

        // AES对称加解密
        // javax.crypto.BadPaddingException: pad block corrupted，出现该异常的主导原因可能如下：密钥key长度不足16位
        String key = "1234567890112345";

        // AES对象加密
        byte[] encrypt = EncryptUtil.aesEncrypt(str, key);
        System.out.println("AES加密后的内容：" + new String(encrypt));

        // AES对象解密
        byte[] decrypt = EncryptUtil.aesDecrypt(encrypt, key);
        System.out.println("AES解密后的内容：" + new String(decrypt));

        // DES对称加密
        String desStr = EncryptUtil.des("davidqq986945193", key, Cipher.ENCRYPT_MODE);
        System.out.println("des对称加密： " + desStr);
        System.out.println("des对称解密： " + EncryptUtil.des(desStr, key, Cipher.DECRYPT_MODE));

        // SHA对称加密
        String shaStr = "davidqq986945193";
        System.out.println("sha_SHA224对象加密： " + EncryptUtil.sha(shaStr, EncryptUtil.SHA224));
        System.out.println("sha_SHA256对象加密： " + EncryptUtil.sha(shaStr, EncryptUtil.SHA256));
        System.out.println("sha_SHA384对象加密： " + EncryptUtil.sha(shaStr, EncryptUtil.SHA384));
        System.out.println("sha_SHA512对象加密： " + EncryptUtil.sha(shaStr, EncryptUtil.SHA512));


    }


    /**
     * AES加密字符串
     *
     * @param content  需要被加密的字符串
     * @param password 加密需要的密码
     * @return 密文
     */
    public static byte[] aesEncrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者

            kgen.init(128, new SecureRandom(password.getBytes()));// 利用用户密码作为随机数初始化出
            // 128位的key生产者
            //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行

            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥

            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
            // null。

            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥

            Cipher cipher = Cipher.getInstance("AES");// 创建密码器

            byte[] byteContent = content.getBytes("utf-8");

            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器

            byte[] result = cipher.doFinal(byteContent);// 加密

            return result;

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密AES加密过的字符串
     *
     * @param content  AES加密过过的内容
     * @param password 加密时的密码
     * @return 明文
     */
    public static byte[] aesDecrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
            byte[] result = cipher.doFinal(content);
            return result; // 明文

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
}