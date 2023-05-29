package com.tripbuddy.util;

import java.security.SecureRandom;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public String getRandomPassword(int size) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            idx = sr.nextInt(len);
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }
	
	@Value("${spring.mail.username}") String mailhost;
    public String sendMail(String userEmail) {
    	MimeMessage message = javaMailSender.createMimeMessage();
    	
    	String subject = "[TripBuddy] 비밀번호 재설정 안내";
    	String newPwd = getRandomPassword(8);
    	StringBuilder contentSb = new StringBuilder("");
    	contentSb.append("<html><body>");
    	contentSb.append("<meta http-equiv='Content-Type' content='text/html; charset=utf-8'>");
    	contentSb.append("<div style=\"margin: 15px\">\r\n").append("<div style=\"background-color: rgba(0, 0, 0, 0.05); padding: 5px 20px\">\r\n").append("<h1 style=\"color: #118c8c; display: inline-block; margin-right: 25px\">\r\n");
    	contentSb.append("<span>\r\n").append("TripBuddy\r\n").append("</span>\r\n").append("</h1>\r\n").append("비밀번호 재설정 안내\r\n").append("</div>\r\n");
    	contentSb.append("\r\n").append("<div>\r\n<br/><br/>").append("<p>안녕하세요, 고객님.<br />즐거운 여행을 도와드리는 <b>TripBuddy</b>입니다.</p>\r\n").append("<br />요청하신 비밀번호 재설정 방법을 안내드립니다.<br />아래에 나온 임시 비밀번호를 입력해\r\n").append("로그인하신 후, 마이페이지에서 비밀번호를 변경해주세요. <br /><br />감사합니다.\r\n"); 
    	contentSb.append("</div>\r\n").append("\r\n").append("<div\r\n").append("style=\"\r\n").append("display: inline-block;\r\n").append("width: 400px;\r\n").append("background-color: rgba(0, 0, 0, 0.05);\r\n").append("padding: 35px 35px;\r\n"); 
    	contentSb.append("margin-top: 50px;\r\n").append("text-align: center;\r\n").append("font-size: larger;\r\n").append("\"\r\n").append(">\r\n"); 
    	contentSb.append("임시 비밀번호 : <b>").append(newPwd).append("</b>\r\n").append("</div>\r\n").append("</div>");
    	contentSb.append("</body></html>");
    	
    	//contentSb.append(newPwd);
    	String content = contentSb.toString();
    	MimeMessageHelper messageHelper;
		try {
			messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			messageHelper.setFrom(mailhost, "TripBuddy");
			messageHelper.setTo(userEmail);
			messageHelper.setSubject(subject);
			messageHelper.setText(content, true);
			javaMailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return newPwd;
    }
    
}
