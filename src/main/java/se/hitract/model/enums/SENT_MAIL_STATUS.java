package se.hitract.model.enums;

public enum SENT_MAIL_STATUS {
    PENDING,   // Request received but not yet processed
    SUCCESS,   // SMTP server accepted the message
    ERROR,     // SMTP failed or Template rendering failed
    REJECTED   // Blocked by internal filters (if any exist in the worker)
}