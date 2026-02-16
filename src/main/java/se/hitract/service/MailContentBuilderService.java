package se.hitract.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import se.hitract.model.HitEventMailDTO;
import se.hitract.model.HitMemberDTO;
import se.hitract.model.OrderMailDTO;
import se.hitract.model.enums.PRODUCT_TYPE;
import se.hitract.service.mail.dto.MailRequestDTO;
import se.hitract.service.util.HashIdUtil;

import java.util.Date;
import java.util.Map;

@Service
public class MailContentBuilderService {

    @Autowired private TemplateEngine templateEngine;
    @Autowired private PropertiesService propertiesService;
    @Autowired private QrCodeService qrCodeService;

    public String build() {
        Context context = new Context();
        return templateEngine.process("mail/signup", context);
    }


    public String sendSpecialMail(String firstName) {
        Context context = new Context();
        context.setVariable("firstName", firstName);

        return templateEngine.process("mail/specialMail", context);
    }

    public String sendM1() {
        Context context = new Context();
        return templateEngine.process("mail/sendM1", context);
    }

    public String sendUserProductUsed(Long userProductId) {
        Context context = new Context();
        context.setVariable("userProduct", Map.of("userProductId", userProductId));

        return templateEngine.process("mail/userProductUsed", context);
    }

    public String sendUserProductUnUsed(Long userProductId) {
        Context context = new Context();
        context.setVariable("userProduct", Map.of("userProductId", userProductId));

        return templateEngine.process("mail/userProductUnUsed", context);
    }


	public String confirmEmail(String token) {
		Context context = new Context();
		context.setVariable("token", token);
		context.setVariable("environment", propertiesService.getEnvironment());
        return templateEngine.process("mail/confirmEmail", context);
	}

	public String paymentReport(Date fromDate, Date toDate) {
		Context context = new Context();
		context.setVariable("fromDate", fromDate);
		context.setVariable("toDate", toDate);

        return templateEngine.process("mail/paymentReportMail", context);
	}

    public String paymentReportNoData(Date fromDate, Date toDate) {
        Context context = new Context();
        context.setVariable("fromDate", fromDate);
        context.setVariable("toDate", toDate);

        return templateEngine.process("mail/paymentReportMailNoData", context);
    }

    public String studentSignInContent(MailRequestDTO request) {
        Context context = new Context();
        context.setVariable("email", request.getEmail());
        context.setVariable("token", request.getToken());
        Object lang = request.getLanguage();
        context.setVariable("language", lang != null ? lang.toString() : "sv");
        context.setVariable("environment", propertiesService.getEnvironment());
        return templateEngine.process("mail/studentSignInMail", context);
    }

    public String studentSignUpContent(MailRequestDTO request) {
        Context context = new Context();
        context.setVariable("email", request.getEmail());
        context.setVariable("token", request.getToken());
        context.setVariable("language", request.getLanguage());
        context.setVariable("environment", propertiesService.getEnvironment());
        return templateEngine.process("mail/studentSignUpMail", context);
    }

    public String hitClubSignInContent(MailRequestDTO request) {
        Context context = new Context();
        context.setVariable("token", request.getToken());
        context.setVariable("email", request.getEmail());
        context.setVariable("environment", propertiesService.getEnvironment());
        return templateEngine.process("mail/hitClubSignInMail", context);
    }

    public String companySignInContent(MailRequestDTO request) {
        Context context = new Context();
        context.setVariable("token", request.getToken());
        context.setVariable("email", request.getEmail());
        context.setVariable("newSite", request.isNewSite());
        context.setVariable("language", request.getLanguage() == null ? "sv" : request.getLanguage());
        context.setVariable("environment", propertiesService.getEnvironment());
        return templateEngine.process("mail/companySignInMail", context);
    }

    public String companySignUpContent(MailRequestDTO request) {
        Context context = new Context();
        context.setVariable("email", request.getEmail());
        context.setVariable("environment", propertiesService.getEnvironment());
        return templateEngine.process("mail/companySignUpMail", context);
    }

    public String sendError(String msg) {
        Context context = new Context();
        context.setVariable("msg", msg);

        return templateEngine.process("mail/error", context);
    }


    public String jonkopingMail() {
        Context context = new Context();
        return templateEngine.process("mail/jonkopingMail", context);
    }

    public String sendWebMemberStatusChanged(MailRequestDTO request) {
        Context context = new Context();
        context.setVariable("hitMember", request.getHitMemberMailDTO());
        context.setVariable("paymentOptionId", request.getHitMemberMailDTO().getPaymentOptionId()!= null ? request.getHitMemberMailDTO().getPaymentOptionId() : null);
        context.setVariable("hitMemberIdHash",request.getHitMemberMailDTO().getHitMemberIdHash() );
        context.setVariable("isProd", propertiesService.isProd());

        return templateEngine.process("mail/webMemberStatusChanged", context);
    }

    public String hitClubInviteMail(HitMemberDTO hitMember) {
        Context context = new Context();

        String data = HashIdUtil.encode(hitMember.getHitMemberId());

        context.setVariable("data", data);
        context.setVariable("hitClub", hitMember.getHitClub());
        context.setVariable("hitMember", hitMember);
        context.setVariable("isProd", propertiesService.isProd());

        return templateEngine.process("mail/hitClubInviteMail", context);
    }

    public String sendWebOrderPayed(OrderMailDTO order, HitEventMailDTO hitEvent) {
        Context context = new Context();

        order.getOrderItems().stream().forEach(oi -> oi.getUserProducts().stream().forEach(up -> {
            String qrCode = qrCodeService.createQrCode(up.getIdentifier());
            up.setQrCode(qrCode);
        }));

        boolean orderIncludesNonTickets = order.getOrderItems().stream().anyMatch(oi -> !oi.getProductOffer().getProductBase().getProductType().equals(PRODUCT_TYPE.TICKET));

        context.setVariable("orderIncludesNonTickets", true);
        context.setVariable("order", order);
        context.setVariable("hitEvent", hitEvent);
        context.setVariable("hitClub", hitEvent.getHitClubName());

        return templateEngine.process("mail/webOrderPayed", context);
    }

    public String sendPayoutReportNotMatch(String merchantCode, Double totalTransfer, Double totalTransferFee, Double totalPayout) {
        Context context = new Context();
        context.setVariable("merchantCode", merchantCode);
        context.setVariable("totalTransfer", totalTransfer);
        context.setVariable("totalTransferFee", totalTransferFee);
        context.setVariable("totalPayout", totalPayout);

        return templateEngine.process("mail/payoutReportNotMatch", context);
    }
}
