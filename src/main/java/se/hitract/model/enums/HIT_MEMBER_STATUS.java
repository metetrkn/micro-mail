package se.hitract.model.enums;

public enum HIT_MEMBER_STATUS {
	
	MEMBER,										// approved, paid
	NOT_MEMBER,									// no member object
	PENDING_APPROVAL,							// not approved not paid
	PENDING_APPROVAL_USER_FEEDBACK,				// not approved not paid
	CANCELLED_REQUEST_BY_USER,					// user cancelled request
	CANCELLED_REQUEST_BY_HIT_CLUB,				// hit club cancelled request
	PENDING_PAYMENT,							// approved, not paid
	PENDING_USER_FEEDBACK,						// student to reply/confirm
	CANCELLED_EXPIRED,							// was member, not anymore
	CANCELLED_DECLINED,							// approval declined 
	CANCELLED_TERMINATED_BY_HITCLUB,			// was member, but removed by hit club owner
	CANCELLED_TERMINATED_BY_USER,				// was member, but removed by hit club owner
	CANCELLED_AUTOMATIC_VERIFICATION_DECLINED,
	CANCELLED_BANNED,							// removed and not welcome again?
	PAID_NOT_MEMBER,							// paid in advance, not used yet
	PRE_PAID,									// not used anymore
	DELETED,									// removed PRE_PAID members
	PENDING_REQUIRED_MEMBERSHIP,				// 
	
}
