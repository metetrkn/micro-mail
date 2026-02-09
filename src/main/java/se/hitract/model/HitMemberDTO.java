package se.hitract.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import se.hitract.model.enums.HIT_MEMBER_STATUS;
import se.hitract.model.enums.SEX;

import java.util.Date;
import java.util.List;

public class HitMemberDTO implements java.io.Serializable {

    private Long hitMemberId;

    private HIT_MEMBER_STATUS hitMemberStatus;
    
    private String externalMemberId;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private boolean hasApp;
    
    private HitClubSmallPushDTO hitClub;
    
    private String personOfficialId;
    
    private String phoneNumber;
    
    private String program;
    
    private String studyPlace;
    
    private Integer studyPacePercentage;
    
    private String address;
    
    private String cancelledReason;
    
    private String feedbackReason;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expireDate;
    
    private SEX sex;
    
    private String coAddress;
    
    private String addressStreet;

    private String addressCity;
    
    private String addressPostCode;
    
    private boolean forceManualApproval;
    
    private String inputMembershipCode;
    
    private Date notInLadokSinceDate;

    private Date approvedDate;
    
    private String activationCode;
    
    private boolean forceReplace;
    
    private int numberOfComments;
    
    private String associatedHitClub;
    
    private boolean membershipIsFree;
    
    private int numberOfDaysMember;
    
    private int numberOfSemesters;
    
    private String paymentInfo;
    
    private Integer price;

	private PaymentOptionIdDTO paymentOption;

	private List<AttachmentDTO> attachments;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<InputAttachmentDTO> inputAttachments;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Long chatGroupId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean chatGroupSeen;

	private boolean renewable;

	private Date latestApplyDate;

	private boolean fromWeb;

    public Long getHitMemberId() {
        return hitMemberId;
    }

    public void setHitMemberId(Long hitMemberId) {
        this.hitMemberId = hitMemberId;
    }

    public HIT_MEMBER_STATUS getHitMemberStatus() {
        return hitMemberStatus;
    }

    public void setHitMemberStatus(HIT_MEMBER_STATUS hitMemberStatus) {
        this.hitMemberStatus = hitMemberStatus;
    }

    public String getExternalMemberId() {
        return externalMemberId;
    }

    public void setExternalMemberId(String externalMemberId) {
        this.externalMemberId = externalMemberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHasApp() {
        return hasApp;
    }

    public void setHasApp(boolean hasApp) {
        this.hasApp = hasApp;
    }

    public HitClubSmallPushDTO getHitClub() {
        return hitClub;
    }

    public void setHitClub(HitClubSmallPushDTO hitClub) {
        this.hitClub = hitClub;
    }

    public String getPersonOfficialId() {
        return personOfficialId;
    }

    public void setPersonOfficialId(String personOfficialId) {
        this.personOfficialId = personOfficialId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getStudyPlace() {
        return studyPlace;
    }

    public void setStudyPlace(String studyPlace) {
        this.studyPlace = studyPlace;
    }

    public Integer getStudyPacePercentage() {
        return studyPacePercentage;
    }

    public void setStudyPacePercentage(Integer studyPacePercentage) {
        this.studyPacePercentage = studyPacePercentage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCancelledReason() {
        return cancelledReason;
    }

    public void setCancelledReason(String cancelledReason) {
        this.cancelledReason = cancelledReason;
    }

    public String getFeedbackReason() {
        return feedbackReason;
    }

    public void setFeedbackReason(String feedbackReason) {
        this.feedbackReason = feedbackReason;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public SEX getSex() {
        return sex;
    }

    public void setSex(SEX sex) {
        this.sex = sex;
    }

    public String getCoAddress() {
        return coAddress;
    }

    public void setCoAddress(String coAddress) {
        this.coAddress = coAddress;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressPostCode() {
        return addressPostCode;
    }

    public void setAddressPostCode(String addressPostCode) {
        this.addressPostCode = addressPostCode;
    }

    public boolean isForceManualApproval() {
        return forceManualApproval;
    }

    public void setForceManualApproval(boolean forceManualApproval) {
        this.forceManualApproval = forceManualApproval;
    }

    public String getInputMembershipCode() {
        return inputMembershipCode;
    }

    public void setInputMembershipCode(String inputMembershipCode) {
        this.inputMembershipCode = inputMembershipCode;
    }

    public Date getNotInLadokSinceDate() {
        return notInLadokSinceDate;
    }

    public void setNotInLadokSinceDate(Date notInLadokSinceDate) {
        this.notInLadokSinceDate = notInLadokSinceDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public boolean isForceReplace() {
        return forceReplace;
    }

    public void setForceReplace(boolean forceReplace) {
        this.forceReplace = forceReplace;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public String getAssociatedHitClub() {
        return associatedHitClub;
    }

    public void setAssociatedHitClub(String associatedHitClub) {
        this.associatedHitClub = associatedHitClub;
    }

    public boolean isMembershipIsFree() {
        return membershipIsFree;
    }

    public void setMembershipIsFree(boolean membershipIsFree) {
        this.membershipIsFree = membershipIsFree;
    }

    public int getNumberOfDaysMember() {
        return numberOfDaysMember;
    }

    public void setNumberOfDaysMember(int numberOfDaysMember) {
        this.numberOfDaysMember = numberOfDaysMember;
    }

    public int getNumberOfSemesters() {
        return numberOfSemesters;
    }

    public void setNumberOfSemesters(int numberOfSemesters) {
        this.numberOfSemesters = numberOfSemesters;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public PaymentOptionIdDTO getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(PaymentOptionIdDTO paymentOption) {
        this.paymentOption = paymentOption;
    }

    public List<AttachmentDTO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<AttachmentDTO> attachments) {
        this.attachments = attachments;
    }

    public List<InputAttachmentDTO> getInputAttachments() {
        return inputAttachments;
    }

    public void setInputAttachments(List<InputAttachmentDTO> inputAttachments) {
        this.inputAttachments = inputAttachments;
    }

    public Long getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(Long chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public Boolean getChatGroupSeen() {
        return chatGroupSeen;
    }

    public void setChatGroupSeen(Boolean chatGroupSeen) {
        this.chatGroupSeen = chatGroupSeen;
    }

    public boolean isRenewable() {
        return renewable;
    }

    public void setRenewable(boolean renewable) {
        this.renewable = renewable;
    }

    public Date getLatestApplyDate() {
        return latestApplyDate;
    }

    public void setLatestApplyDate(Date latestApplyDate) {
        this.latestApplyDate = latestApplyDate;
    }

    public boolean isFromWeb() {
        return fromWeb;
    }

    public void setFromWeb(boolean fromWeb) {
        this.fromWeb = fromWeb;
    }
}
