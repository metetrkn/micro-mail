package se.hitract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import se.hitract.model.SendMail;
import se.hitract.model.SentMail;

import java.util.List;

@Repository
public interface SendMailRepository extends JpaRepository<SentMail, Long> {

    @Query("SELECT s.email FROM SendMail s WHERE s.mailSent = false")
    List<String> findUnsentEmails();
}
