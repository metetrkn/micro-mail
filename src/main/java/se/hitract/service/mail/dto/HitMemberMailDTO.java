package se.hitract.service.mail.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HitMemberMailDTO {
    private String firstName;
    private String hitMemberStatus;
    private String activationCode;
    private String cancelledReason;
    private String feedbackReason;
    private String hitClubName;
    private String logoSmall;
    private String membershipOfferName;
    private String membershipOfferType;
    private String hitMemberIdHash;
    private String paymentOptionId;
}
