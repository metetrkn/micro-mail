package se.hitract.service;

import java.util.List;
import java.util.function.Supplier;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import se.hitract.model.SendMail;
import se.hitract.model.domains.MAIL_TYPE;
import se.hitract.model.enums.EntityType;
import se.hitract.repository.SendMailRepository;
import se.hitract.service.mail.dto.MailRequestDTO;

@Slf4j
@Service
public class MailSenderService {

	@Autowired private MailgunHttpService mailgunHttpService;
	@Autowired private MailContentBuilderService mailContentBuilderService;
	@Autowired private SendMailRepository sendMailRepository;


	private static final Logger logger = LoggerFactory.getLogger(MailSenderService.class);

//	public void sendMail(String toMail, String fromMail, String personal, String content, String subject, MAIL_TYPE mailType) {
//		sendMail(new String[] {toMail}, null, fromMail, personal, content, subject, mailType, null, null, null, null);
//	}
//
//	public void sendMail(String toMail, String fromMail, String personal, String content, String subject, MAIL_TYPE mailType, EntityType entityType, Long entityId) {
//		sendMail(new Strisng[] {toMail}, null, fromMail, personal, content, subject, mailType, null, entityType, entityId, null);
//	}
//
//	public void sendMail(String toMail, String fromMail, String personal, String content, String subject, MAIL_TYPE mailType, List<MailAttachment> mailAttachments, EntityType entityType, Long entityId) {
//		sendMail(new String[] {toMail}, null, fromMail, personal, content, subject, mailType, mailAttachments, entityType, entityId, null);
//	}
//
//	private void sendMail(MailRequestDTO dto) {
//		System.out.println("!!!! another one worked");
//		String sendToEmail = dto.getToMail();
//
//		MimeMessagePreparator messagePreparator = mimeMessage -> {
//			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
//			messageHelper.setFrom(dto.getFromMail(), "hitract");
//			messageHelper.setTo(sendToEmail);
//			messageHelper.setSubject(dto.getSubject());
//			messageHelper.setText(dto.getContent(), true);
//		};
//
//		try {
//			boolean isWhitelisted = sendToEmail.contains("hitract.se")
//					|| sendToEmail.contains("hitract.com")
//					|| sendToEmail.contains("nordstrom.robert@gmail.com");
//
//			if ("PROD".equals(propertiesService.getEnvironment()) || dto.isInternalUser() || isWhitelisted) {
//
//				//javaMailSender.send(messagePreparator);
//
//				// Matches your new SentMail constructor exactly
//				sentMailRepository.save(new SentMail(
//						SENT_MAIL_STATUS.SUCCESS,
//						sendToEmail,
//						dto.getId(),      // This is the 'toStudent' Long
//						dto.getMailType(),
//						null,             // error
//						null,             // entityType (if not in DTO)
//						null              // entityId (if not in DTO)
//				));
//			}
//		} catch (Exception e) {
//			logger.error("Failed to send mail: {}", e.getMessage());
//
//			sentMailRepository.save(new SentMail(
//					SENT_MAIL_STATUS.ERROR,
//					sendToEmail,
//					dto.getId(),
//					dto.getMailType(),
//					e.toString(),
//					null,
//					null
//			));
//		}
//	}
//
//	private void sendMail(String[] toMails, String[] cc, String fromMail, String personal, String content, String subject, MAIL_TYPE mailType, List<MailAttachment> mailAttachments, EntityType entityType, Long entityId, String replyTo) {
//
//		System.out.println("!!!! send mail worked sb");
//		MimeMessagePreparator messagePreparator = mimeMessage -> {
//			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
//			messageHelper.setFrom(fromMail, personal);
//			if(cc != null) {
//				messageHelper.setCc(cc);
//			}
//
//			messageHelper.setTo(toMails);
//			messageHelper.setSubject(subject);
//			if(replyTo != null) {
//				messageHelper.setReplyTo(replyTo);
//			}
//			//mimeMessage.addHeader("List-Unsubscribe", "<mailto: unsubscribe@hitract.se?subject=Unsubscribe>");
//			messageHelper.setText(content, true);
//			if(mailAttachments != null) {
//				for(MailAttachment mailAttachment : mailAttachments) {
//					messageHelper.addAttachment(mailAttachment.getFilename(), new ByteArrayResource(mailAttachment.getData()));
//				}
//			}
//		};
//
//		boolean isHitractMail = Arrays.stream(toMails).anyMatch(toMail -> toMail.contains("hitract.se") || toMail.contains("hitract.com") || toMail.contains("nordstrom.robert@gmail.com"));
//		String mails = String.join(",", toMails);
//		try {
//			if (propertiesService.getEnvironment().equals("PROD") || isHitractMail) {
//				logger.debug("Sending mailType: " + mailType + " to email: " + mails);
//				//javaMailSender.send(messagePreparator);
//				sentMailRepository.save(new SentMail(SENT_MAIL_STATUS.SUCCESS, mails , null, mailType, null, entityType, entityId));
//			} else {
//				logger.debug("User don't want emails (or non prod environment)");
//			}
//		} catch (Exception e) {
//			logger.error("Could not send chat group not read mail: " + e);
//			sentMailRepository.save(new SentMail(SENT_MAIL_STATUS.ERROR, mails, null, mailType, e.toString(), entityType, entityId));
//		}
	//	}
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
	}    public void sendErrorMail(MailRequestDTO request) {
		try {
			String content=mailContentBuilderService.sendError(request.getMessage());
			request.setContent(content);
			request.setFromMail("noreply@hitract.se");
			request.setSubject("hitract error");
			mailgunHttpService.send(request, "Hitract");

			log.info("Mail successfully processed for: {}", request.getEmail());
		} catch (Exception e) {
			log.error("FAILED to process mail for {}: {}", request.getEmail(), e.getMessage());
			// THROW the exception so Rqueue knows to retry!
			throw new RuntimeException("Email delivery failed", e);
		}
	}

	@Async
	public void sendJonkopingMail() {

		List<String> emails = sendMailRepository.findAllEmails();

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

//	//@Scheduled(fixedRate=30000)
//	//@RqueueListener(value = "sendChatGroupNotReadMails", numRetries = "0")
//	public void sendChatGroupNotReadMails() {
//
//		List<ChatGroupParticipant> chatGroupParticipants = null; //chatGroupParticipantRepository.findChatGroupParticipantsNotReadOlderThan2min(new Date());
//
//		for(ChatGroupParticipant chatGroupParticipant : chatGroupParticipants) {
//			if(chatGroupParticipant.getStudent().getEmail() != null) {
//				sendChatGroupNotReadMail(chatGroupParticipant);
//			}
//			chatGroupParticipant.setMailSent(true);
//			chatGroupParticipantRepository.save(chatGroupParticipant);
//		}
//	}
//
//	public void sendStatusMail(Student toStudent) {
//
//		String fromMail = "noreply@hitract.se";
//		String content = mailContentBuilderService.statusMailContent(toStudent);
//		String subject = "Din profil på hitract!";
//
//		sendMail(toStudent, fromMail, content, subject, MAIL_TYPE.STATUS_MAIL);
//	}
//
//
//	@Async
//	public void sendCompanySignInMail(Student toStudent, String token, boolean newSite) {
//
//		String fromMail = "login@hitract.se";
//		String content = mailContentBuilderService.companySignInContent(toStudent.getLanguage(), toStudent.getIdentifier(), token, newSite);
//		String subject = "Logga in på hitract";
//		sendMail(toStudent, fromMail, content, subject, MAIL_TYPE.MAIL_SIGNIN);
//	}
//
//	@Async
//	public void sendStudentSignUpMail(String email, String token, LANGUAGE language) {
//
//		LANGUAGE languageNullSafe = language == null ? LANGUAGE.sv : language;
//
//		String fromMail = "login@hitract.se";
//		String content = mailContentBuilderService.studentSignUpContent(email, token, languageNullSafe);
//		String subject = languageNullSafe.equals(LANGUAGE.sv) ? "Nytt konto på hitract" : "New account on hitract";
//		sendMail(email, fromMail, "", content, subject, MAIL_TYPE.COMPANY_SIGNUP);
//	}

//	@Async
//	public void sendHitClubSignupInMail(Student toStudent, String token) {
//
//		String fromMail = "login@hitract.se";
//		String content = mailContentBuilderService.hitClubSignInContent(toStudent.getEmail(), token);
//		String subject = "Logga in på hitract";
//		sendMail(toStudent, fromMail, content, subject, MAIL_TYPE.HITCLUB_SIGNIN);
//	}
//
//	@Async
//	public void sendCompanySignupUpMail(Student toStudent) {
//
//		String fromMail = "login@hitract.se";
//		String content = mailContentBuilderService.companySignUpContent(toStudent.getEmail());
//		String subject = "Nytt konto på hitract";
//		sendMail(toStudent, fromMail, content, subject, MAIL_TYPE.COMPANY_SIGNUP);
//	}
//
//	@Async
//	public void sendJonkopingMail(String email) {
//
//		String fromMail = "noreply@hitract.se";
//		String content = mailContentBuilderService.jonkopingMail();
//		String subject = "JSU membership payment";
//		sendMail(email, fromMail, "JSU", content, subject, MAIL_TYPE.JONKOPING_PAY_MEMBERSHIP_MAIL);
//	}
//
//	public void sendChatGroupNotReadMail(ChatGroupParticipant chatGroupParticipant) {
//
//		Student toStudent = chatGroupParticipant.getStudent();
//		Student fromStudent = new Student(29L); //chatGroupParticipant.getOldestChatMessageNotClicked().getStudent();
//		String fromMail = "noreply@hitract.se";
//		String content = mailContentBuilderService.chatGroupNotSeenContent(chatGroupParticipant);
//		String subject = "Oläst meddelande på Hitract från " + fromStudent.getFirstName();
//
//		sendMail(toStudent, fromMail, content, subject, MAIL_TYPE.CHAT_NOT_READ);
//	}
//
//	//@RqueueListener(value = "sendStatusMail", numRetries = "0")
//	public void sendEmail(Long studentId) {
//		sendStatusMail(studentService.findOne(studentId));
//	}

//	public void sendHitClubInviteMail(HitMemberDTO hitMember) {
//
//		HitClubSmallPushDTO hitClub = hitMember.getHitClub();
//		String hitClubName = hitClub.getHitClubName();
//
//		String fromMail = (hitClub.getHitClubMailName() != null ? hitClub.getHitClubMailName() : "noreply") + "@hitract.se";
//		String content = mailContentBuilderService.hitClubInviteMail(hitMember);
//		String subject = hitClubName + " || " + "Din aktiveringskod " + hitMember.getActivationCode();
//		sendMail(hitMember.getEmail(), fromMail, hitClubName, content, subject, MAIL_TYPE.HIT_CLUB_INVITE);
//	}
//
//	public void sendHitClubMemberPayMail(HitMemberDTO hitMember) {
//
//		HitClubSmallPushDTO hitClub = hitMember.getHitClub();
//		String hitClubName = hitClub.getHitClubName();
//
//		String fromMail = (hitClub.getHitClubMailName() != null ? hitClub.getHitClubMailName() : "noreply") + "@hitract.se";
//		String content = mailContentBuilderService.sendHitClubMemberPayMail(hitMember);
//		String subject = hitClubName + " || " + "Betala medlemskap";
//		sendMail(hitMember.getEmail(), fromMail, hitClubName, content, subject, MAIL_TYPE.HIT_CLUB_MEMBER_PAY);
//	}







//	@RqueueListener(value = "sendWebOrderPayed", numRetries = "0", concurrency = "1", active = "${queue.enable}")
//	public void sendWebOrderPayed(OrderDTO orderDTO) throws IOException {
//
//		if(alreadySent(MAIL_TYPE.WEB_ORDER_PAYED, EntityType.ORDER, orderDTO.getOrderId())) {
//			logger.debug("Mail webOrderPayed already sent");
//			return;
//		}
//
//		HitEvent hitEvent = hitEventService.findByIdCache(orderDTO.getEntityId());
//
//		Orderr order = BeanUtil.getBean(OrderService.class).getWithChildren(orderDTO.getOrderId());
//
//		String fromMail = "noreply@hitract.se";
//		String content = mailContentBuilderService.sendWebOrderPayed(order, hitEvent);
//		String subject = "Din beställning - " + hitEvent.getName();
//
//		List<MailAttachment> attachments = new ArrayList<MailAttachment>();
//
//		for(OrderItem orderItem : order.getOrderItems()) {
//
//			if(!orderItem.getProductOffer().getProductBase().getProductType().equals(PRODUCT_TYPE.TICKET)) {
//				continue; //skip non tickets
//			}
//
//			for(UserProduct userProduct : orderItem.getUserProducts()) {
//				MailAttachment mailAttachment = new MailAttachment();
//				mailAttachment.setFilename(orderItem.getProductOffer().getLabel() + "_" + userProduct.getUserProductId()+".pdf");
//				mailAttachment.setData(getPdf(userProduct.getQrCode(), orderItem.getProductOffer().getLabel(), userProduct.getUserProductId().toString(), hitEvent));
//				attachments.add(mailAttachment);
//			}
//		}
//
//		sendMail(order.getStudent().getEmail(), fromMail, "", content, subject, MAIL_TYPE.WEB_ORDER_PAYED, attachments, EntityType.ORDER, order.getOrderId());
//
//	}
//
//	public byte[] getPdf(String qrCode, String ticketName, String id, HitEvent hitEvent) throws IOException {
//		ByteArrayOutputStream pdfOutputFile = new ByteArrayOutputStream();
//
//		Document document = new Document(PageSize.A4);
//		PdfWriter writer = PdfWriter.getInstance(document, pdfOutputFile);
//		document.setFooter(getHeaderFooter());
//
//		document.open();
//
//		HitClub hitClub = hitEvent.getHitClub();
//		Image topLogo = Image.getInstance(new URL(hitClub.getHeaderLogo().getBaseUrl() + hitClub.getLogo().getMedium()));
//		topLogo.setAbsolutePosition(37, 740);
//		topLogo.scaleToFit(70, 70);
//		document.add(topLogo);
//
//		Paragraph name = PdfContent.getParagraph(hitClub.getHitClubName(), 16);
//		name.setSpacingBefore(60);
//		name.setSpacingAfter(70);
//		document.add(name);
//
//		int size = 12;
//		int spacing = 6;
//
//		Color blueBorderColor = new Color(165, 207, 231);
//
//		PdfPTable table = new PdfPTable(2);
//		table.setWidthPercentage(100);
//		table.setWidths(new int[]{1, 1});
//
//		PdfPCell cell1 = new PdfPCell();
//		cell1.setPadding(6);
//		cell1.setBorderWidth(0);
//
//		cell1.addElement(new Paragraph(40, "\u00a0"));
//		cell1.addElement(PdfContent.getParagraph("Event: " + hitEvent.getName(), size, 8, spacing));
//		cell1.addElement(PdfContent.getParagraph("Biljett: " + ticketName, size, spacing));
//		cell1.addElement(PdfContent.getParagraph("Biljettnummer: " + id, size, spacing));
//
//		table.addCell(cell1);
//
//		Image image2;
//		try {
//			image2 = Image.getInstance(DatatypeConverter.parseBase64Binary(qrCode));
//			//image2.setAbsolutePosition(37, 740);
//			//image2.scaleToFit(70, 70);
//
//			PdfPCell cell2 = new PdfPCell();
//			cell2.setBorderWidth(0);
//			cell2.addElement(image2);
//			table.addCell(cell2);
//
//
//		} catch (BadElementException | IOException e) {
//			e.printStackTrace();
//		}
//
//		document.add(table);
//
//		document.close();
//		writer.flush();
//
//		return pdfOutputFile.toByteArray();
//
//	}
//
//	private static HeaderFooter getHeaderFooter() {
//		HeaderFooter footer = new HeaderFooter(new Phrase("Hitract AB\nOrg-nr: 559210-3260\nMail: info@hitract.se", new Font(Font.HELVETICA, 9)), false);
//		footer.setBorderWidthBottom(0);
//		return footer;
//	}



//	public void sendNewContactInfoMail(ContactInfo contactInfo) {
//
//		String fromMail = "info@hitract.se";
//		String content = mailContentBuilderService.sendContactInfo(contactInfo);
//		String subject = (propertiesService.isProd() ? "" : "*** TEST environment ***") + getSubject(contactInfo) + " [#" + contactInfo.getContactInfoId() + "]";
//
//		if(propertiesService.isProd()) {
//			sendMail(new String[] {"mito@hitract.se", "eric@hitract.com", "jesper@hitract.com", "adonai@hitract.com"}, new String[] {"info@hitract.se", "robel@hitract.se", "robert@hitract.se", "philip@hitract.com", "benti.zekarias@gmail.com"}, fromMail, "", content, subject, MAIL_TYPE.CONTACT_INFO, null, null, null, contactInfo.getEmail());
//		} else {
//			sendMail(new String[] {"info@hitract.se"}, null, fromMail, "", content, subject, MAIL_TYPE.CONTACT_INFO, null, null, null, contactInfo.getEmail());
//		}
//
//	}
//
//	private String getSubject(ContactInfo contactInfo) {
//		if(contactInfo.getSubject() != null) {
//			return contactInfo.getSubject();
//		}
//		return "No subject";
//	}
//
//	@Async
//	public void sendOrderPayed(Orderr order, byte[] receiptData) {
//
//		String fromMail = "noreply@hitract.se";
//		String content = mailContentBuilderService.sendOrderPayed(order);
//		String subject = "ENGÅNGSKÖP: hitract - Tack för ditt köp ["+order.getOrderId()+"]";
//
//		List<MailAttachment> attachments = new ArrayList<MailAttachment>();
//
//		if(receiptData != null) {
//			MailAttachment mailAttachment = new MailAttachment();
//			mailAttachment.setFilename("Kvitto.pdf");
//			mailAttachment.setData(receiptData);
//			attachments.add(mailAttachment);
//		}
//
//		sendMail(order.getStudent().getEmail(), fromMail, "", content, subject, MAIL_TYPE.ORDER_PAYED, attachments, EntityType.ORDER, order.getOrderId());
//	}
//
//	public void sendWebMemberStatusChanged(HitMember hitMember) {
//
//		String fromMail = "noreply@hitract.se";
//		String content = mailContentBuilderService.sendWebMemberStatusChanged(BeanUtil.getBean(ModelMapper.class).map(hitMember, HitMemberDTO.class));
//
//		String subject;
//		if(hitMember.getPaymentOption() == null) {
//			subject = "hitract - din medlemsansökan ["+hitMember.getHitClub().getHitClubName()+"]";
//		} else {
//			if(hitMember.getHitMemberStatus().equals(HIT_MEMBER_STATUS.MEMBER)) {
//				subject = "hitract - du är medlem ["+hitMember.getHitClub().getHitClubName()+"]";
//			} else if(hitMember.getHitMemberStatus().equals(HIT_MEMBER_STATUS.PENDING_PAYMENT)) {
//				subject = "hitract - betala medlemsavgift [" + hitMember.getHitClub().getHitClubName() + "]";
//			} else {
//				throw new RuntimeException("Not implemented");
//			}
//		}
//
//		sendMail(hitMember.getEmail(), fromMail, "", content, subject, MAIL_TYPE.WEB_MEMBER_STATUS_CHANGED);
//
//	}
//
//	public void sendErrorMail(String msg) {
//
//		String fromMail = "noreply@hitract.se";
//		String content = mailContentBuilderService.sendError(msg);
//		String subject = "hitract error";
//		sendMail("robert@hitract.se", fromMail, "", content, subject, MAIL_TYPE.ERROR);
//
//	}

//	public boolean alreadySent(MAIL_TYPE mailType, EntityType entityType, Long orderId) {
//		return sentMailRepository.existsByMailTypeAndEntityTypeAndEntityId(mailType, entityType, orderId);
//	}
//
//	public List<SendMail> findSendMail() {
//		return sendMailRepository.findAll();
//	}
//
//	public void sendReceipt(Transaction transaction, String email, byte[] data) {
//
//		LANGUAGE language = LANGUAGE.sv;
//
//		String fromMail = "noreply@hitract.se";
//		String content = mailContentBuilderService.sendReceipt(language);
//		String subject = language.equals(LANGUAGE.sv) ? "Kvitto" : "Receipt";
//
//		List<MailAttachment> attachments = new ArrayList<MailAttachment>();
//
//		MailAttachment mailAttachment = new MailAttachment();
//		mailAttachment.setFilename((language.equals(LANGUAGE.sv) ? "Kvitto" : "Receipt") + "_"+transaction.getTransactionId()+".pdf");
//		mailAttachment.setData(data);
//		attachments.add(mailAttachment);
//
//		sendMail(email, fromMail, "", content, subject, MAIL_TYPE.RECEIPT, attachments,EntityType.TRANSACTION, transaction.getTransactionId());
//	}
//
//	public void sendPayoutReportNotMatch(String merchantCode, byte[] data, Double totalTransfer, Double totalTransferFee, Double totalPayout) {
//
//		String fromMail = "noreply@hitract.se";
//		String content = mailContentBuilderService.sendPayoutReportNotMatch(merchantCode, totalTransfer, totalTransferFee, totalPayout);
//
//		List<MailAttachment> attachments = new ArrayList<MailAttachment>();
//
//		MailAttachment mailAttachment = new MailAttachment();
//		mailAttachment.setFilename("Felaktig rapport " + merchantCode + ".pdf");
//		mailAttachment.setData(data);
//		attachments.add(mailAttachment);
//
//		sendMail(new String[] {"robert@hitract.se", "eric@hitract.se"}, null, fromMail, "", content, "Felaktig rapport", MAIL_TYPE.PAYPOUT_REPORT_NOT_MATCH, attachments, null, null, null);
//
//	}

	/*
	public void sendManualMemberCreated(HitMemberDTO hitMember) {

		HitClubSmallDTO hitClub = hitMember.getHitClub();
		String hitClubName = hitClub.getHitClubName();

		String fromMail = (hitClub.getHitClubMailName() != null ? hitClub.getHitClubMailName() : "noreply") + "@hitract.se";
		String content = mailContentBuilderService.manualMemberMail(hitMember);
    	String subject = "Du är upplagd som medlem hos " + hitClubName;
        sendMail(hitMember.getEmail(), fromMail, hitClubName, content, subject, MAIL_TYPE.HIT_MEMBER_MANUAL_CREATED);

	}
	*/

}
