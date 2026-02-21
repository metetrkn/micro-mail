package se.hitract.service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import se.hitract.model.*;
import se.hitract.model.domains.MAIL_TYPE;
import se.hitract.model.enums.EntityType;
import se.hitract.model.enums.LANGUAGE;
import se.hitract.model.enums.PRODUCT_TYPE;
import se.hitract.repository.SendMailRepository;
import se.hitract.service.mail.dto.MailRequestDTO;
import se.hitract.repository.SentMailRepository;

@Slf4j
@Service
public class MailSenderService {

	@Autowired private MailgunHttpService mailgunHttpService;
	@Autowired private MailContentBuilderService mailContentBuilderService;
	@Autowired private SendMailRepository sendMailRepository;
	@Autowired private SentMailRepository sentMailRepository;

	private static final Logger logger = LoggerFactory.getLogger(MailSenderService.class);

	public void sendM1(MailRequestDTO request) {
		try {
			String content = mailContentBuilderService.sendM1();
			request.setContent(content);
			request.setFromMail("noreply@hitract.se");
			request.setSubject("Uppdatering av våra Användarvillkor");
			mailgunHttpService.send(request, "Hitract");

			log.info("Mail successfully processed for: {}", request.getEmail());
		} catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}

	public void sendSpecialMail(MailRequestDTO request) {

		try {
			String content = mailContentBuilderService.sendSpecialMail(request.getFirstName());
			request.setContent(content);
			request.setFromMail("noreply@hitract.se");
			request.setSubject("Mailutskick");
			mailgunHttpService.send(request, "Hitract");
			log.info("Mail successfully processed for: {}", request.getEmail());
		} catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}

	@Async
	public void sendUserProductUsed(MailRequestDTO request) {
		try {
			request.setEntityType("USER_PRODUCT");
			request.setFromMail("noreply@hitract.se");
			String content = mailContentBuilderService.sendUserProductUsed(request.getUserProductId());
			request.setContent(content);
			request.setSubject("hitract - din biljett har checkats in");
			mailgunHttpService.send(request, "Hitract");
			log.info("Mail successfully processed for: {}", request.getEmail());
		} catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}

	@Async
	public void sendUserProductUnUsed(MailRequestDTO request) {
		try {
			request.setEntityType("USER_PRODUCT");
			request.setFromMail("noreply@hitract.se");
			String content = mailContentBuilderService.sendUserProductUnUsed(request.getUserProductId());
			request.setContent(content);
			request.setSubject("hitract - din biljett har checkats ut");
			mailgunHttpService.send(request, "Hitract");
			log.info("Mail successfully processed for: {}", request.getEmail());
		} catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}

	public void confirmEmail(MailRequestDTO request) {
		try {
			request.setFromMail("noreply@hitract.se");
			String content = mailContentBuilderService.confirmEmail(request.getToken());
			request.setContent(content);
			request.setSubject("Bekräfta din mail");
			mailgunHttpService.send(request, "Hitract");
			log.info("Mail successfully processed for: {}", request.getEmail());
		} catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}

	public void sendPaymentReport(MailRequestDTO request) {
		try {
			request.setFromMail("noreply@hitract.se");
			String content = mailContentBuilderService.paymentReport(request.getFromDate(), request.getToDate());
			request.setContent(content);
			request.setSubject("Rapport hitract - ny rapport skapad");
			mailgunHttpService.send(request, "Hitract");
			log.info("Mail successfully processed for: {}", request.getEmail());
		} catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}

	public void sendPaymentReportNoData(MailRequestDTO request) {
		try {
			String content = mailContentBuilderService.paymentReportNoData(request.getFromDate(), request.getToDate());
			request.setContent(content);
			request.setFromMail("noreply@hitract.se");
			request.setSubject("Rapport hitract - ingen data för perioden");
			mailgunHttpService.send(request, "Hitract");
			log.info("Mail successfully processed for: {}", request.getEmail());
		} catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}
	public void sendStudentSigInInMail(MailRequestDTO request) {

		request.setFromMail("login@hitract.se");
		request.setSubject(request.getLanguage().equals("sv") ? "Logga in på hitract" : "Login to hitract");
		String content=buildContent(()->mailContentBuilderService.studentSignInContent(request), request.getEmail());
		request.setContent(content);
		mailgunHttpService.send(request, "Hitract");

	}

	public void sendStudentSignUpMail(MailRequestDTO request) {
		request.setFromMail("login@hitract.se");
		request.setSubject(request.getLanguage().equals("sv") ? "Nytt konto på hitract" : "New account on hitract");
		String content=buildContent(()->mailContentBuilderService.studentSignUpContent(request), request.getEmail());
		mailgunHttpService.send(request, "Hitract");
	}

	public void sendHitClubSignupInMail(MailRequestDTO request) {
		request.setFromMail("login@hitract.se");
		request.setSubject(request.getLanguage().equals("sv") ? "Logga in på hitract" : "Login to hitract");
		String content=buildContent(()->mailContentBuilderService.hitClubSignInContent(request), request.getEmail());
		request.setContent(content);
		mailgunHttpService.send(request, "Hitract");

	}

	public void sendCompanySignInMail(MailRequestDTO request) {
		request.setFromMail("login@hitract.se");
		request.setSubject(request.getLanguage().equals("sv") ? "Logga in på hitract" : "Login to hitract");
		String content=buildContent(()->mailContentBuilderService.companySignInContent(request), request.getEmail());
		request.setContent(content);
		mailgunHttpService.send(request, "Hitract");

	}

	public void sendCompanySignupUpMail(MailRequestDTO request) {
		request.setFromMail("login@hitract.se");
		request.setSubject(request.getLanguage().equals("sv") ? "Nytt konto på hitract" : "New account on hitract");
		String content=buildContent(()->mailContentBuilderService.companySignUpContent(request), request.getEmail());
		request.setContent(content);
		mailgunHttpService.send(request, "Hitract");

	}

	private String buildContent(Supplier<String> contentSupplier, String mail) {
		String content;
		try {
			content = contentSupplier.get();
		} catch (Exception e) {
			log.error("Template rendering failed for {}: {}", mail, e.getMessage());
			throw new RuntimeException("Template Rendering failed: " + e.getMessage(), e);
		}

		return content;
	}

	public void sendErrorMail(MailRequestDTO request) {
		try {
			String content=mailContentBuilderService.sendError(request.getMessage());
			request.setContent(content);
			request.setFromMail("noreply@hitract.se");
			request.setSubject("hitract error");
			mailgunHttpService.send(request, "");
			log.info("Mail successfully processed for: {}", request.getEmail());
		} catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}

	@Async
	public void sendJonkopingMail() {

		List<String> emails = sendMailRepository.findPendingEmails();

		if (emails == null || emails.isEmpty()) {
			log.info("There is no mail in sendMail for sendJonkopingMail. Stopping execution.");
			return;
		}

		for (String email : emails) {
			try {
				MailRequestDTO request = new MailRequestDTO();
				request.setEmail(email);
				request.setFromMail("noreply@hitract.se");
				String content = mailContentBuilderService.jonkopingMail();
				request.setContent(content);
				request.setSubject("JSU membership payment");
				request.setMailType(MAIL_TYPE.JONKOPING_PAY_MEMBERSHIP_MAIL);
				mailgunHttpService.send(request, "JSU");
				log.info("Mail successfully processed for: {}", request.getEmail());
			} catch (Exception e) {
				log.error("FAILED to process mail: {}", e.getMessage());
				throw new RuntimeException("Email delivery failed", e);
			}
		}
	}

	public void sendHitClubInviteMail(MailRequestDTO request) {
		try {
			HitMemberDTO hitMember = request.getHitMemberDTO();
			HitClubSmallPushDTO hitClub = hitMember.getHitClub();
			String hitClubName = hitClub.getHitClubName();

			String content = mailContentBuilderService.hitClubInviteMail(hitMember);
			request.setContent(content);

			String subject = hitClubName + " || " + "Din aktiveringskod " + hitMember.getActivationCode();
			request.setSubject(subject);

			String fromMail = (hitClub.getHitClubMailName() != null ? hitClub.getHitClubMailName() : "noreply") + "@hitract.se";
			request.setFromMail(fromMail);

			mailgunHttpService.send(request, hitClubName);
			log.info("Mail successfully processed for: {}", request.getEmail());
		} catch (Exception e) {
			log.error("FAILED to process mail: {}", e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}

	}

	public void sendWebMemberStatusChanged(MailRequestDTO request) {

		String subject;

		if(request.getHitMemberMailDTO().getPaymentOptionId() == null) {
			subject = "hitract - din medlemsansökan ["+request.getHitMemberMailDTO().getHitClubName()+"]";
		} else {
			if(request.getHitMemberMailDTO().getHitMemberStatus().equals("MEMBER")) {
				subject = "hitract - du är medlem ["+request.getHitMemberMailDTO().getHitClubName()+"]";
			} else if(request.getHitMemberMailDTO().getHitMemberStatus().equals("PENDING_PAYMENT")) {
				subject = "hitract - betala medlemsavgift [" +request.getHitMemberMailDTO().getHitClubName() + "]";
			} else {
				throw new RuntimeException("Subject Couldnt resolve for WebMemberStatusChanged");
			}
		}

		try{
			String content=mailContentBuilderService.sendWebMemberStatusChanged(request);
			request.setContent(content);
			request.setFromMail("noreply@hitract.se");
			request.setSubject(subject);
			mailgunHttpService.send(request,"Hitract");
		}catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}

	public void sendNewContactInfoMail(MailRequestDTO request) {
		try {
			request.setFromMail("info@hitract.se");
			String content = mailContentBuilderService.sendContactInfo(request.getContactInfoMailDTO());
			request.setContent(content);
			String[] cc =  {"info@hitract.se", "robel@hitract.se", "robert@hitract.se", "philip@hitract.com", "benti.zekarias@gmail.com"};
			String replyTo = request.getContactInfoMailDTO().getEmail();

			for (String email: request.getEmails()) {
				System.out.println("!!!!!!!");
				System.out.println(email);
			}

			mailgunHttpService.send(request,"", cc, replyTo);
		}catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}

	@Async
	public void sendOrderPayed(MailRequestDTO request) {
		try{
			request.setFromMail("noreply@hitract.se");
			String content = mailContentBuilderService.sendOrderPayed(request);
			request.setContent(content);
			request.setSubject("ENGÅNGSKÖP: hitract - Tack för ditt köp [" + request.getOrderId() + "]");

			List<MailAttachment> attachments = new ArrayList<MailAttachment>();
			if (request.getData() != null) {
				MailAttachment mailAttachment = new MailAttachment();
				mailAttachment.setFilename("Kvitto.pdf");
				mailAttachment.setData(request.getData());
				attachments.add(mailAttachment);
			}
			request.setMailAttachments(attachments);
			request.setEntityType("ORDER");

			mailgunHttpService.send(request,"");
		}catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}


	public void sendReceipt(MailRequestDTO request) {
		try {
			LANGUAGE language = LANGUAGE.sv;

			request.setFromMail("noreply@hitract.se");
			request.setSubject(language.equals(LANGUAGE.sv) ? "Kvitto" : "Receipt");
			String content = mailContentBuilderService.sendReceipt(language);
			request.setContent(content);


			List<MailAttachment> attachments = new ArrayList<MailAttachment>();

			MailAttachment mailAttachment = new MailAttachment();
			mailAttachment.setFilename((language.equals(LANGUAGE.sv) ? "Kvitto" : "Receipt") + "_" + request.getTransactionId() + ".pdf");
			mailAttachment.setData(request.getData());
			attachments.add(mailAttachment);
			request.setMailAttachments(attachments);
			request.setEntityType("TRANSACTION");

			mailgunHttpService.send(request, "");
		} catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}


	public void sendPayoutReportNotMatch(MailRequestDTO request) {
		try {
			request.setFromMail("noreply@hitract.se");
			String content = mailContentBuilderService.sendPayoutReportNotMatch(
					request.getMerchantCode(),
					request.getTotalTransfer(),
					request.getTotalTransferFee(),
					request.getTotalPayout());
			request.setContent(content);
			request.setSubject("Felaktig rapport");
			request.setMailType(MAIL_TYPE.PAYPOUT_REPORT_NOT_MATCH);

			List<MailAttachment> attachments = new ArrayList<MailAttachment>();
			MailAttachment mailAttachment = new MailAttachment();
			mailAttachment.setFilename("Felaktig rapport " + request.getMerchantCode() + ".pdf");
			mailAttachment.setData(request.getData());
			attachments.add(mailAttachment);

			mailgunHttpService.send(request,"");
			log.info("Mail successfully processed for mail type: {}", MAIL_TYPE.PAYPOUT_REPORT_NOT_MATCH);
		} catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}

	public void sendWebOrderPayed(MailRequestDTO request) {
		try {
			OrderMailDTO order = request.getOrderMailDTO();
			HitEventMailDTO hitEvent = request.getHitEventMailDTO();

			if (order == null || hitEvent == null) {
				log.error("Missing order or event data");
				return;
			}

			boolean isAlreadySent = sentMailRepository.existsByMailTypeAndEntityTypeAndEntityId
					(MAIL_TYPE.WEB_ORDER_PAYED, EntityType.ORDER, order.getOrderId());

			if (isAlreadySent) {
				logger.debug("Mail webOrderPayed already sent");
				return;
			}

			request.setFromMail("noreply@hitract.se");
			String content = mailContentBuilderService.sendWebOrderPayed(order, hitEvent);
			request.setContent(content);

			request.setSubject("Din beställning - " + hitEvent.getHitClubName());

			List<MailAttachment> attachments = new ArrayList<>();
			for (OrderMailDTO.OrderItemDto orderItem : order.getOrderItems()) {

				if (!orderItem.getProductOffer().getProductBase().getProductType().equals(PRODUCT_TYPE.TICKET)) {
					continue;
				}

				for (OrderMailDTO.UserProductDto userProduct : orderItem.getUserProducts()) {
					MailAttachment mailAttachment = new MailAttachment();
					mailAttachment.setFilename(orderItem.getProductOffer().getLabel() + "_" + userProduct.getUserProductId() + ".pdf");
					mailAttachment.setData(getPdf(userProduct.getQrCode(), orderItem.getProductOffer().getLabel(), userProduct.getUserProductId().toString(), hitEvent));
					attachments.add(mailAttachment);
				}
				mailgunHttpService.send(request,"");
			}
		} catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			throw new RuntimeException("Email delivery failed", e);
		}
	}

	public byte[] getPdf(String qrCode, String ticketName, String id, HitEventMailDTO hitEvent) throws IOException {
		ByteArrayOutputStream pdfOutputFile = new ByteArrayOutputStream();

		Document document = new Document(PageSize.A4);
		PdfWriter writer = PdfWriter.getInstance(document, pdfOutputFile);
		document.setFooter(getHeaderFooter());

		document.open();

		String logoUrl = hitEvent.getHitClubLogo().getMedium();

		if (!logoUrl.toLowerCase().startsWith("http")) {
			String baseUrl = hitEvent.getHitClubHeaderLogo().getBaseUrl();
			if (baseUrl != null) {
				logoUrl = baseUrl + logoUrl;
			}
		}

		Image topLogo = Image.getInstance(new URL(logoUrl));

		topLogo.setAbsolutePosition(37, 740);
		topLogo.scaleToFit(70, 70);
		document.add(topLogo);

		Paragraph name = PdfContent.getParagraph(hitEvent.getHitClubName(), 16);
		name.setSpacingBefore(60);
		name.setSpacingAfter(70);
		document.add(name);

		int size = 12;
		int spacing = 6;

		Color blueBorderColor = new Color(165, 207, 231);

		PdfPTable table = new PdfPTable(2);
		table.setWidthPercentage(100);
		table.setWidths(new int[]{1, 1});

		PdfPCell cell1 = new PdfPCell();
		cell1.setPadding(6);
		cell1.setBorderWidth(0);

		cell1.addElement(new Paragraph(40, "\u00a0"));
		cell1.addElement(PdfContent.getParagraph("Event: " + hitEvent.getName(), size, 8, spacing));
		cell1.addElement(PdfContent.getParagraph("Biljett: " + ticketName, size, spacing));
		cell1.addElement(PdfContent.getParagraph("Biljettnummer: " + id, size, spacing));

		table.addCell(cell1);

		Image image2;
		try {
			image2 = Image.getInstance(DatatypeConverter.parseBase64Binary(qrCode));
			//image2.setAbsolutePosition(37, 740);
			//image2.scaleToFit(70, 70);

			PdfPCell cell2 = new PdfPCell();
			cell2.setBorderWidth(0);
			cell2.addElement(image2);
			table.addCell(cell2);


		} catch (BadElementException | IOException e) {
			e.printStackTrace();
		}

		document.add(table);

		document.close();
		writer.flush();

		return pdfOutputFile.toByteArray();

	}

	private static HeaderFooter getHeaderFooter() {
		HeaderFooter footer = new HeaderFooter(new Phrase("Hitract AB\nOrg-nr: 559210-3260\nMail: info@hitract.se", new com.lowagie.text.Font(Font.HELVETICA, 9)), false);
		footer.setBorderWidthBottom(0);
		return footer;
	}
}
