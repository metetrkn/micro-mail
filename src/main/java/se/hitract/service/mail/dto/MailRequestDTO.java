package se.hitract.service.mail.dto;

import lombok.*;
import se.hitract.model.*;
import se.hitract.model.domains.MAIL_TYPE;
import se.hitract.service.MailAttachment;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailRequestDTO {

    private MAIL_TYPE mailType;
    private String email;
    private String[] emails = new String[2];
    private Map<String, Object> propertiesData;
    private String entityType;
    private Long entityId;
    private Long studentId;
    private String fromMail;
    private String subject;
    private String contractSubject;
    private String[] contactUrl;
    private String[] fileName;
    private String content;
    private boolean internalUser;
    private String firstName;
    private String lastName;
    private String merchantCode;
    private Long userProductId;
    private Long productOfferIds;
    private String token;
    private Date fromDate;
    private Date toDate;
    private Double totalTransfer;
    private Double totalTransferFee;
    private Double totalPayout;
    private String language;
    private boolean newSite;
    private String message;
    private String hitEvent;
    private String hitEventName;
    private ImageDTO headerLogo;
    private ImageDTO logo;
    private OrderMailDTO orderMailDTO;
    private HitEventMailDTO hitEventMailDTO;
    private ImageDTO image;
    private HitMemberDTO hitMemberDTO;
    private HitMemberMailDTO hitMemberMailDTO;
    private byte[] data;
    private List<MailAttachment> mailAttachments;
    private Long transactionId;
    private String orderPaidDate;
    private String orderId;
    private String[] label;
    private String[] quantity;
    private String[] unitPrice;
    private String[] total;
    private String  totalOrderAmount;
    private ContactInfoMailDTO contactInfoMailDTO;

    // --- Validation Fields (Added) ---
    private String phoneNumber;
    private String program;
    private Integer studyPacePercentage;
    private String studyPlace;
    private String associatedHitClub;

    // Address Information
    private String addressStreet;
    private String addressCity;
    private String addressPostCode;

    private String personOfficialId;
    private String sex;
    private boolean forceManualApproval;
    private String hitClubName;

    // Nested Objects
    private MembershipOfferInfo membershipOffer;
    private PaymentOptionInfo inputPaymentOption;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MembershipOfferInfo {
        private Long membershipOfferId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PaymentOptionInfo {
        private Long paymentOptionId;
    }
}