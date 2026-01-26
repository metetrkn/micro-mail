package se.hitract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.hitract.model.SentMail;

@Repository
public interface SentMailRepository extends JpaRepository<SentMail, Long> {

}