package se.hitract.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.hitract.service.mail.dto.MailRequestDTO;

@Component
@Slf4j
public class MailRoutingService {

    private final MailContentBuilderService mailContentBuilderService;
    private MailSenderService mailSenderService;
    public MailRoutingService(MailSenderService mailSenderService, MailContentBuilderService mailContentBuilderService) {
        this.mailSenderService = mailSenderService;
        this.mailContentBuilderService = mailContentBuilderService;
    }

    public void routeEmail(@org.checkerframework.checker.nullness.qual.MonotonicNonNull MailRequestDTO emailDto) {
        switch (emailDto.getMailType()) {
            case SPECIAL_M1 -> mailSenderService.sendM1(emailDto);
            case SPECIAL -> mailSenderService.sendSpecialMail(emailDto);
            case USER_PRODUCT_USED -> mailSenderService.sendUserProductUsed(emailDto);
            case USER_PRODUCT_UN_USED -> mailSenderService.sendUserProductUnUsed(emailDto);
            case CONFIRM_EMAIL -> mailSenderService.confirmEmail(emailDto);
            case PAYMENT_REPORT -> mailSenderService.sendPaymentReport(emailDto);
            case PAYMENT_REPORT_NO_DATA -> mailSenderService.sendPaymentReportNoData(emailDto);
            case STUDENT_SIGNIN -> mailSenderService.sendStudentSigInInMail(emailDto);
            case STUDENT_SIGNUP -> mailSenderService.sendStudentSignUpMail(emailDto);
            case COMPANY_SIGNIN -> mailSenderService.sendCompanySignInMail(emailDto);
            case COMPANY_SIGNUP -> mailSenderService.sendCompanySignupUpMail(emailDto);
            case HITCLUB_SIGNIN -> mailSenderService.sendHitClubSignupInMail(emailDto);
            case JONKOPING_PAY_MEMBERSHIP_MAIL -> mailSenderService.sendJonkopingMail();
            case HIT_CLUB_INVITE -> mailSenderService.sendHitClubInviteMail(emailDto);
            case WEB_MEMBER_STATUS_CHANGED -> mailSenderService.sendWebMemberStatusChanged(emailDto);
            case WEB_ORDER_PAYED -> mailSenderService.sendWebOrderPayed(emailDto);
            case ORDER_PAYED -> mailSenderService.sendOrderPayed(emailDto);
            case PAYPOUT_REPORT_NOT_MATCH -> mailSenderService.sendPayoutReportNotMatch(emailDto);
            case RECEIPT -> mailSenderService.sendReceipt(emailDto);
            case CONTACT_INFO -> mailSenderService.sendNewContactInfoMail(emailDto);
            case ERROR -> mailSenderService.sendErrorMail(emailDto);
            default -> log.error("No logic implemented for MAIL_TYPE: {}", emailDto.toString());
        }
    }
}