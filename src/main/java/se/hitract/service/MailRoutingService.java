package se.hitract.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.hitract.service.mail.dto.MailRequestDTO;

@Component
@Slf4j
public class MailRoutingService {

    private final MailSenderService mailSenderService;
    public MailRoutingService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    public void routeEmail(@org.checkerframework.checker.nullness.qual.MonotonicNonNull MailRequestDTO emailDto) {
        switch (emailDto.getMailType()) {
            case SPECIAL_M1 -> mailSenderService.sendM1(emailDto);
            case STUDENT_SIGNIN -> mailSenderService.sendStudentSigInInMail(emailDto);
            default -> log.error("No logic implemented for MAIL_TYPE: {}", emailDto.toString());
//            case COMPANY_SIGNIN -> handleCompanySignIn(email);
//            case COMPANY_SIGNUP -> handleCompanySignUp(email);
//            case STATUS_MAIL -> handleStatusMail(email);
//            case HITCLUB_SIGNIN -> handleHitClubSignIn(email);
//            case MAIL_SIGNIN -> handleMailSignIn(email);
//            case HIT_CLUB_INVITE -> handleHitClubInvite(email);
//            case HIT_MEMBER_MANUAL_CREATED -> handleManualMemberCreated(email);
//            case PAYMENT_REPORT -> handlePaymentReport(email);
//            case SPECIAL -> handleSpecialMail(email);
//            case ORDER_PAYED -> handleOrderPayed(email);
//            case WEB_ORDER_PAYED -> handleWebOrderPayed(email);
//            case USER_PRODUCT_USED -> handleProductUsed(email);
//            case USER_PRODUCT_UN_USED -> handleProductUnused(email);
//            case CONTACT_INFO -> handleContactInfo(email);
//            case WEB_BECOME_MEMBER -> handleWebBecomeMember(email);
//            case WEB_MEMBER_STATUS_CHANGED -> handleWebMemberStatusChanged(email);
//            case ERROR -> handleErrorMessage(email);
//            case HIT_CLUB_MEMBER_PAY -> handleHitClubMemberPay(email);
//            case CONFIRM_EMAIL -> handleConfirmEmail(email);
//            case JONKOPING_PAY_MEMBERSHIP_MAIL -> handleJonkopingMembership(email);
//            case RECEIPT -> handleReceipt(email);
//            case PAYPOUT_REPORT_NOT_MATCH -> handlePayoutReportMismatch(email);
        }
    }
}