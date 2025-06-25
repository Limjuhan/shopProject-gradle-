package gradleProject.shop3.util;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

public class CipherUtil {
    private static byte[] randomKey;

    private final static byte[] iv = new byte[] {
            (byte)0x8E,0x12,0x39,(byte)0x90,
            0x07,0x72,0x6F,(byte)0x5A,
            (byte)0x8E,0x12,0x39,(byte)0x90,
            0x07,0x72,0x6F,(byte)0x5A,
    };

    static Cipher cipher;
    static {
        try {
            /*
            AES : 암호화알고리즘
             */
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static byte[] getRandomKey(String algo) throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algo);
        keyGen.init(128);
        SecretKey key = keyGen.generateKey();
        return key.getEncoded();
    }

    //평문->암호문
    public static String encrypt(String plain) {
        byte[] cipherMsg = new byte[1024];
        try {
            //randomKey : AES용 128비트 키값 저장
            randomKey = getRandomKey("AES");
            Key key = new SecretKeySpec(randomKey, "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            //Cipher.ENCRYPT_MODE ㅣ 암호화가능
            //key : 대칭키
            //paramSpec : 초기화벡터
            cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            //plain.getBytes : 평문
            cipherMsg = cipher.doFinal(plain.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteToHex(cipherMsg).trim();
    }

    public static String decrypt(String cipherMsg) {
        byte[] plainMsg = new byte[1024];
        try {
            Key key = new SecretKeySpec(randomKey, "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            //Cipher.DECRYPT_MODE : 복호화모드
            cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            plainMsg= cipher.doFinal(hexToByte(cipherMsg.trim()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(plainMsg).trim(); //byte[]형태의평문->문자열
    }

    private static String byteToHex(byte[] cipherMsg) {
        if(cipherMsg == null) {
            return null;
        }
        String str = "";
        for(byte b : cipherMsg) {
            str += String.format("%02x", b);
        }
        return str;
    }

    //16진수형태 문자열 - > 비트 형태 데이터
    private static byte[] hexToByte(String str) {
        if(str== null || str.length() <2) {
            return null;
        }
        int len = str.length() /2 ; //2개의문자 => 1byte
        byte[] buf = new byte[len];
        for(int i = 0; i <len; i++) {
            //16진수임을 명시
            //ex) subString의 결과가 A1 이라면 161로 변환할것임
            buf[i] = (byte)Integer.parseInt(str.substring(i *2, i *2 +2), 16);

        }
        return buf;
    }

    private static byte[] makeKey(String key) {
        //key : "abc123456"
        int len = key.length();
        char ch = 'A';
        for (int i=len ; i<16;i++){
            key += ch++;
        }
        //key : abc123ABCDEF
        //15자리 문자들 각각을 byte[]로 변경후 반환
        System.out.println("본래 key : "+key);
        System.out.println("byte[] : "+Arrays.toString(key.substring(0,16).getBytes()));//검증
        return key.substring(0,16).getBytes();
    }
    //key를 이용한 암호화
    public static String encrypt(String plain, String key) {
        byte[] cipherMsg = new byte[1024];
        try {
            Key genKey = new SecretKeySpec(makeKey(key), "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE,genKey,paramSpec);
            cipherMsg = cipher.doFinal(plain.getBytes());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //byte를 String으로변경
        return byteToHex(cipherMsg);

    }
    public static String decrypt(String cipher1, String key) {
        byte[] plainMsg = new byte[1024];
        try {
            Key genKey = new SecretKeySpec(makeKey(key), "AES");
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, genKey, paramSpec);
            plainMsg= cipher.doFinal(hexToByte(cipher1.trim()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(plainMsg).trim();
    }

    public static void encryptFile(String plainFile, String cipherFile, String strkey) {
        try {
            getKey(strkey); //strKey를 기반으로만든 key.ser(암호화 객체)
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("key.ser"));
            Key key = (Key) ois.readObject();//key.ser을 읽어서 key에 저장
            ois.close();
            AlgorithmParameterSpec sparamSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE,key,sparamSpec);//암호화객체초기화
            FileInputStream fis = new FileInputStream(plainFile);//평문
            FileOutputStream fos = new FileOutputStream(cipherFile);//암호문파일
            //CipherOutputStream : 암호화스트림(fos를 암호화해줌)
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);
            byte[] buf = new byte[1024];
            int len;
            while((len=fis.read(buf)) != -1){ // 평문읽기
                cos.write(buf, 0, len);//평문을 암호화해 fos에 저장하는과정
            }
             fis.close(); cos.flush(); fos.flush();
             cos.close(); fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void getKey(String key)  throws Exception{
        Key genKey = new SecretKeySpec(makeKey(key), "AES");
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream("key.ser"));
        out.writeObject(genKey);
        out.flush(); out.close();
    }

    public static void decryptFile(String cipherFile, String plainFile, String strKey) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("key.ser"));
            Key key = (Key) ois.readObject();
            ois.close();
            IvParameterSpec paramSpec = new IvParameterSpec(iv);
            //Cipher.DECRYPT_MODE : 복호화
            cipher.init(Cipher.DECRYPT_MODE,key,paramSpec); //복호화는 암호화객체 부분만 다르다!!
            FileInputStream fis = new FileInputStream(cipherFile); //암호화파일 읽기
            FileOutputStream fos = new FileOutputStream(plainFile); //복호화된 평문
            CipherOutputStream cos = new CipherOutputStream(fos, cipher);//평문으로 fos에 저장시켜줄것임
            byte[] buf = new byte[1024];
            int len;
            while((len=fis.read(buf)) != -1){
                cos.write(buf, 0, len);
            }
            fis.close(); cos.flush(); fos.flush();
            fos.close(); cos.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String makehash(String userid) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] plain = userid.getBytes();
        byte[] hash = md.digest(plain);
        return byteToHex(hash);
    }


}