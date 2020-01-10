package common.utils;

import com.gzcb.creditcard.gykdh.common.contants.LoanContants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * 
 * 项目：gykdh
 * 版    权: Copyright(C) 2011-2020,  gzcb. All rights reserved
 * 类名：MailSenderUtils
 * 描述：邮件发送工具类
 * 创建人：liting    创建日期：2017年2月9日   版本：
 * 历史
 * <修改人>   <修改日期 >   <修改内容>
 */
public class MailSenderUtils {
	
	private static Logger logger=LoggerFactory.getLogger(MailSenderUtils.class);
	
	
	
	/**
	 * 
	 * 描述：<功能简述>
	 * @param to  接收人
	 * @param subject 主题
	 * @param content 发送内容
	 * @return
	 *
	 */
	public static boolean send(String to, String subject, String content){		
		String smtp =PropertiesUtils.getProperty("MAIL_HOST");
		String from = (String) JedisUtil.get(LoanContants.PARAMETER_CHACHE_NAME,"PNOTE_EMAIL_FROM");
		String userName= (String) JedisUtil.get(LoanContants.PARAMETER_CHACHE_NAME,"PNOTE_EMAIL_FROM");
		String password=(String) JedisUtil.get(LoanContants.PARAMETER_CHACHE_NAME,"PNOTE_EMAIL_USER_PWD");
		return send(smtp, from, to, subject, content,userName, password);
	}
	
	/**
	 * 
	 * 描述：<功能简述>
	 * @param smtp  发送协议
	 * @param from 邮件地址
	 * @param to 接收人
	 * @param subject 标题
	 * @param content  邮件内容
	 * @param username  发送人账号
	 * @param password 发送人的密码
	 * @return
	 *
	 */
	public static boolean send(String smtp, String from, String to, String subject, String content,
			final String username, final String password) {
		boolean result=true;
		Properties props = System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", smtp); // 设置SMTP主机
		Session session = null; // 邮件会话对象
		
		try {
			logger.info("准备获取邮件会话对象！");
			props.put("mail.smtp.auth", "true");
			session = Session.getInstance(props, new Authenticator() {
				// 在session中设置账户信 息，Transport发送邮件时会使用
				@Override
                protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			// 创建邮件对象
			Message msg = new MimeMessage(session);
			// 发件人
			msg.setFrom(new InternetAddress(from));
			// 多个收件人
			msg.setRecipients(RecipientType.TO, InternetAddress.parse(to));

			// 主题
			msg.setSubject(subject);
			// HTML内容
			msg.setContent(content, "text/html;charset=utf-8");

			// 连接邮件服务器、发送邮件、关闭连接，全干了
			Transport.send(msg);
			result= true;		
			
		} catch (Exception e) {
			logger.error("邮件发送失败,to: {}",to, e);
			result=false;
		}
		logger.info("发送邮件结束！");
		return result;
	}

	/*public static void main(String[] args) {
		

		System.out.println(MailSenderUtils.send("mail.gzb.com", "chizhiwei@gzcb.com.cn", "liting@gzcb.com.cn,liangyi@gzcb.com.cn", "这是fsaf 一个测试",
				"testfsadfa<br/>点击", "chizhiwei@gzcb.com.cn", "czw123***"));
	}
*/
}
