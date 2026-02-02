package se.hitract.service.mail.dto;

import lombok.*;
import se.hitract.model.domains.MAIL_TYPE;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MailRequestDTO {

    private MAIL_TYPE mailType;
    private String email;
    private Map<String, Object> propertiesData;
    private String entityType;
    private Long entityId;
    private Long studentId;
    private String fromMail;
    private String subject;
    private String content;
    private boolean internalUser;
    private String firstName;
    private String lastName; // Required
    private Long userProductId;
    private Long productOfferIds;
    private String token;
    private Date fromDate;
    private Date toDate;
    private String language;
    private boolean newSite;
    private String message;

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