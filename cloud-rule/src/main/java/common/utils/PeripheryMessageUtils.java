package common.utils;


import com.gzcb.creditcard.gykdh.web.rule.RuleExecuteController;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * Created by gcb on 2019/7/9 0009.
 */
public class PeripheryMessageUtils {
    private static Logger logger= LoggerFactory.getLogger(RuleExecuteController.class);

    private static final String FILE_NAME = "peripheryMessage.xml";
    private static final String CHARSET = "GBK";
    private static final String MSG_IP = PropertiesUtils.getProperty("PERIPHERY_MESSAGE_IP");
    private static final String MSG_PORT = PropertiesUtils.getProperty("PERIPHERY_MESSAGE_PORT");
    private static final String CONNECT_TIMEOUT = PropertiesUtils.getProperty("PERIPHERY_MESSAGE_CONNECT_TIMEOUT");
    private static final String REPLY_TIMEOUT = PropertiesUtils.getProperty("PERIPHERY_MESSAGE_REPLY_TIMEOUT");
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final String path = PeripheryMessageUtils.class.getResource("/").getPath();

    public static boolean sendMsg(String tels, String msg){
        String queryId = UUID.randomUUID().toString().replace("-", "").toUpperCase().substring(0, 24);

        String message = null;
        File file = new File( path +FILE_NAME );
        byte[] temp = new byte[(int) file.length()];
        InputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(temp, 0, temp.length);

            message = new String(temp, CHARSET);
            message = message.replaceAll("(?<=>)\\s+(?=<)", "");
        } catch (FileNotFoundException e) {
            logger.error("短信报文模板文件缺失：", e);
        } catch (IOException e) {
            logger.error("读取短信报文模板文件失败：", e);
        } catch (Exception e) {
            logger.error("短信报文模板其他异常：", e);
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    logger.error("文件关闭异常：", e);
                }
            }
        }
        if ("".equals(message)) {
            logger.info("短信报文模板为空");
            return Boolean.parseBoolean(null);
        }

        // 占位符替换
        String date = SDF.format(new Date());
        message = MessageFormat.format(message, queryId, date, tels, msg);
        // 报文头部加入长度域，长度域为6位十进制数字（不足6位左补0）
        try {
            message = String.format("%06d", message.getBytes(CHARSET).length) + message;
        } catch (UnsupportedEncodingException e) {
            logger.info("不支持" + CHARSET + "编码", e);
        }
        logger.info("短信请求报文：" + message);

        /*
		 * 连接统一查询平台调用信贷接口
		 */
        Socket socket = new Socket();
        String reply = null;
        try {
            SocketAddress socketAddress = new InetSocketAddress(MSG_IP, Integer.valueOf(MSG_PORT));
            socket.connect(socketAddress, Integer.valueOf(CONNECT_TIMEOUT));
            socket.setSoTimeout(Integer.valueOf(REPLY_TIMEOUT));

            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(message.getBytes(CHARSET));

            InputStream inputStream = socket.getInputStream();
            // 读取接口返回报文的长度（头部6位数字）
            byte[] replyLengthBytes = new byte[6];
            inputStream.read(replyLengthBytes, 0, 6);
            int replyLength = Integer.valueOf(new String(replyLengthBytes, CHARSET));
            byte[] replyBytes = new byte[replyLength];
            inputStream.read(replyBytes, 0, replyLength);

            reply = new String(replyBytes, CHARSET);
        } catch (SocketTimeoutException e) {
            logger.error("统一查询平台接口调用超时：", e);
        } catch (IOException e) {
            logger.error("统一查询平台接口IO异常：", e);
        } catch (Exception e) {
            logger.error("统一查询平台接口其他异常：", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("socket关闭异常：", e);
            }
        }

        if (reply == null) {
            logger.info("外围短信接口调用异常，接口无响应");
            return Boolean.parseBoolean(null);
        }
        logger.info("外围短信返回报文：" + reply);
        boolean result = determineMutex(reply);;
        return result;
    }

    private static boolean determineMutex(String reply)  {
        boolean flag = false;
        String reCode = "";
        String rsp = "";
        try {
            Document document = DocumentHelper.parseText(reply);
            Element body = document.getRootElement().element("body");
            // 根据返回码判断请求是否成功（成功时返回码为0000）
            reCode = body.element("re_code").getText();
            rsp = body.element("Rsp").getText();
            if ("0000".equals(reCode) && "0000".equals(rsp)) {
                flag = true;
            }
        } catch (Exception e) {
            logger.error("返回报文解析错误:", e);
        }
        return flag;
    }

    public static void main(String[] args) {
        boolean str = PeripheryMessageUtils.sendMsg("13828432341","测试市场实施方式的发生发的");
        System.out.println(str);
    }

}
