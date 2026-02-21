package se.hitract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.hitract.model.SentMail;
import se.hitract.model.domains.MAIL_TYPE;
import se.hitract.model.enums.EntityType;

@Repository
public interface SentMailRepository extends JpaRepository<SentMail, Long> {

    boolean existsByMailTypeAndEntityTypeAndEntityId(MAIL_TYPE mailType, EntityType entityType, Long orderId);
}