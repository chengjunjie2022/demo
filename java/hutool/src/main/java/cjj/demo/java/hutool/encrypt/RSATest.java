package cjj.demo.java.hutool.encrypt;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

import java.util.Base64;

public class RSATest {
    /**
     * 获取公钥私钥密钥对
     * */
    static String getKeyPair(RSA rsa){
        StringBuilder rtnStb=new StringBuilder();
        //获得私钥
        //System.out.println(rsa.getPrivateKey());
        rtnStb.append("privateKey: ");
        rtnStb.append(rsa.getPrivateKeyBase64());
        rtnStb.append("\n");
        //获得公钥
        //System.out.println(rsa.getPublicKey());
        rtnStb.append("publicKey: ");
        rtnStb.append(rsa.getPublicKeyBase64());
        return rtnStb.toString();
    }

    //加密
    public static String getEncryptString(String str, RSA rsa){
        byte[] encrypt = rsa.encrypt(StrUtil.bytes(str, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        return Base64.getEncoder().encodeToString(encrypt);
    }

    //解密
    public static String getDecryptString(String str, RSA rsa){
        byte[] aByte = Base64.getDecoder().decode(str);
        byte[] decrypt = rsa.decrypt(aByte, KeyType.PrivateKey);
        return new String(decrypt, CharsetUtil.CHARSET_UTF_8);
    }

    public static void main(String[] args) {
        String priKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALihlKU/0qVevy8w39i+7/tG+e7yaQuuDKur6OcQq+yl/QPqdw5jChcPsGr0y8Y/3BgI/BHoUMcPSLhD6ywMa6/HZyObuWaVVE+liShTeOGYGQu0HE557RG7oaLv/QlW3JzSiCVnKDMDbjlKn/1uRJMGvkznliS2E6JLVUF4jwnnAgMBAAECgYAY051l1R2xn/psUZGgFxi0/LfJ8i2+5NIOWYjxXTQJF87ccnA87oNiU3lBAJbQGzEwyDCBBfCqu7rnEDb0ztzB5TFzE/Kkc5Rpo0uxrPNLt2VnAGnsWZYXQi70ny4G1atNZ02pyQOAfKBWQtGGIoMSSi9arWY9b+3aJ3HepMKPbQJBAOPWWRp5AkfC7K3ETGnP5P/oioRczLpvq0pUSP+vZfEEVi4LHRy5125DwAZgE4K7MA1SqXUPMOGZAfMNJ//37AMCQQDPdAYllDetIXO1e4nfUbsyuuol8i0gSPSePFkxqSE0xqe85xcOrqFMUIR5qecZ6I8bl9skiADERoJFFEIBja9NAkB6BF9v2AKqn+/VF928hUQk8UrNnE1gQ1G+2ujFVhAIRcRJoFYU/kormUkLoQSR7OHNIMn0ERCioSj0G8WiRgo1AkEAxp/z8q8Cz8UjoDN0QllivqOv5D0Hb83qSEbHr4CbxogdugQYU3pn/UVqUYKXPnsn6edvbn65HTPVLobC2/HoUQJBAOOtLN1BWlQGorbwhNlPB2tXwVAC6U0M5cbbP5GJD97Rq+K3mawjpK9NEs+P01WubNWLm8F0gbMJhMsJ6NbBfPU=";
        String pubKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4oZSlP9KlXr8vMN/Yvu/7Rvnu8mkLrgyrq+jnEKvspf0D6ncOYwoXD7Bq9MvGP9wYCPwR6FDHD0i4Q+ssDGuvx2cjm7lmlVRPpYkoU3jhmBkLtBxOee0Ru6Gi7/0JVtyc0oglZygzA245Sp/9bkSTBr5M55YkthOiS1VBeI8J5wIDAQAB";
        RSA rsa = new RSA(priKey, pubKey);
        String plainTxt="这是个test字符串,这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串这是个test字符串";
        String encryptTxt=getEncryptString(plainTxt, rsa);
        System.out.println(encryptTxt);
        String decryptTxt=getDecryptString(encryptTxt, rsa);
        System.out.println(decryptTxt);
    }
}
