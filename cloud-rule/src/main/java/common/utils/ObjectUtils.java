package common.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 
 * @author Liu Fei
 * 对象序列化和反序列化实现工具类
 * @date 2018-01-22
 */
public class ObjectUtils {

    private static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);

    public static byte[] serialize(Object o){
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        byte[] bytes = null;
        try{
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            oos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            logger.error("对象序列化失败:",e);
        } finally {
            if(oos!=null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bytes;
    }

    public static Object unserialize(byte[] bytes){
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        Object o = null;
        try{
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            o = ois.readObject();
        } catch (Exception e) {
            logger.error("对象反序列化失败:",e);
        } finally {
            if (ois!=null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return o;
    }
}
