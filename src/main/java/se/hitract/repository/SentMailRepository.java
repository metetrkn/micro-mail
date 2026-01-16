package se.hitract.repository;

import org.springframework.stereotype.Repository;
import se.hitract.model.enums.EntityType;
import se.hitract.model.SentMail;
import se.hitract.model.enums.MAIL_TYPE;

@Repository
public interface SentMailRepository extends CommonRepository<SentMail> {

    boolean existsByMailTypeAndEntityTypeAndEntityId(MAIL_TYPE mailType, EntityType entityType, Long entityId);
}
