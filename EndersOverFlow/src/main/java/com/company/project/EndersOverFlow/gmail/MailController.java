package com.company.project.EndersOverFlow.gmail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.company.project.EndersOverFlow.model.Member;

// [START simple_includes]
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
// [END simple_includes]

// [START multipart_includes]
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;

import javax.activation.DataHandler;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("email")
public class MailController {
	// 기본형
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	com.company.project.EndersOverFlow.service.MemberService memberService;
	
	// 유저 인증 메일 발송 부분
	@PostMapping(path = "/doEmailAuthCheck")
	public String doPost(@Validated Member member, HttpServletRequest request) throws IOException {
		logger.info("signup 임시 유저 정보 저장 시작");
		LocalDate nowDate = LocalDate.now();
		String uuid = UUID.randomUUID().toString();
		member.setMBR_EMAIL(member.getMBR_EMAIL().split("@")[0]);
        member.setMBR_SIGNUP_DATE(nowDate);
        member.setMBR_PASSWORD_UPDATE_DATE(nowDate);
        member.setMBR_LOGINUUID(uuid);
        memberService.doSignUp(member);
		logger.info("signup 임시 유저 정보 저장 완료");

		logger.info("유저 인증 메일 발송 시작");
		String type = request.getParameter("type");
		if (type != null && type.equals("multipart")) {
			sendMultipartMail();
		} else {
			sendSimpleMail("jd363@enders.co.kr", member.getMBR_EMAIL()+"@enders.co.kr", "가입 승인 요청 메일입니다.", "권한승인을 요청합니다. 가입을 원하실 경우 아래 인증번호를 입력해주세요: \n 인증번호 : "+ uuid +"'></a>");
		}
		logger.info("유저 인증 메일 발송 종료");
    	return "redirect:/member/login";
	}
	
	
	// 일반 문서 이메일 보내는 것
	private void sendSimpleMail(String fromEmail, String toEmail, String subject, String text) {
		logger.info("sendSimpleMail 시작");
		System.out.println("유저 정보입니다. : " + fromEmail);
		System.out.println("유저 정보입니다. : " + toEmail);
		System.out.println("유저 정보입니다. : " + subject);
		System.out.println("유저 정보입니다. : " + text);
		
//		SMTP 서버 : smtp.gmail.com.
//		SMTP 이메일 : 본인 Gmail 주소
//		SMTP 비밀번호 : 본인 Gmail 비밀번호
//		SMTP 포트 (TLS) : 587.
//		SMTP 포트 (SSL) : 465.
//		SMTP TLS/SSL 필요 : 예
		
		
	    // [START simple_example]
		// Gmail Props설정
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        Authenticator auth = new MailAuth();
        
	    Session session = Session.getInstance(props, auth);
	    System.out.println("Session 통과완룡");
	    try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(fromEmail, "jd363@enders.co.kr"));
			msg.addRecipient(Message.RecipientType.TO,
		                   new InternetAddress(toEmail, "toEmail"));
			msg.setSubject(subject);
			msg.setText(text);
			
			logger.info("sendSimpleMail send하기 직전까진 완료");
			System.out.println(msg);
			Transport.send(msg);
			logger.info("sendSimpleMail 종료");
	    } catch (AddressException e) {
			logger.info("sendSimpleMail 비정상 종료 : " + e);
	    	// ...
	    } catch (MessagingException e) {
			logger.info("sendSimpleMail 비정상 종료 : " + e);
	    	// ...
	    } catch (UnsupportedEncodingException e) {
			logger.info("sendSimpleMail 비정상 종료 : " + e);
	    	// ...
	    }
	    // [END simple_example]
	}

	
	
	// 파일같은 멀티파트폼데이터로 된 이멜 보내는 것
	// TODO
	// 아래 것 사용하려면 튜닝해서 써야함
	private void sendMultipartMail() {
		logger.info("sendMultipartMail 시작");
	    Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);
	
	    String msgBody = "...";
	
	    try {
	      Message msg = new MimeMessage(session);
	      msg.setFrom(new InternetAddress("admin@example.com", "Example.com Admin"));
	      msg.addRecipient(Message.RecipientType.TO,
	                       new InternetAddress("user@example.com", "Mr. User"));
	      msg.setSubject("Your Example.com account has been activated");
	      msg.setText(msgBody);
	
	      // [START multipart_example]
	      String htmlBody = "";          // ...
	      byte[] attachmentData = null;  // ...
	      Multipart mp = new MimeMultipart();
	
	      MimeBodyPart htmlPart = new MimeBodyPart();
	      htmlPart.setContent(htmlBody, "text/html");
	      mp.addBodyPart(htmlPart);
	
	      MimeBodyPart attachment = new MimeBodyPart();
	      InputStream attachmentDataStream = new ByteArrayInputStream(attachmentData);
	      attachment.setFileName("manual.pdf");
	      attachment.setContent(attachmentDataStream, "application/pdf");
	      mp.addBodyPart(attachment);
	
	      msg.setContent(mp);

	      logger.info("sendMultipartMail send하기 직전까진 완료");
	      Transport.send(msg);
	      logger.info("sendMultipartMail 종료");
	
	    } catch (AddressException e) {
			logger.info("sendMultipartMail 비정상 종료 : " + e);
	      // ...
	    } catch (MessagingException e) {
			logger.info("sendMultipartMail 비정상 종료 : " + e);
	      // ...
	    } catch (UnsupportedEncodingException e) {
			logger.info("sendMultipartMail 비정상 종료 : " + e);
	      // ...
	    }
	}
}
