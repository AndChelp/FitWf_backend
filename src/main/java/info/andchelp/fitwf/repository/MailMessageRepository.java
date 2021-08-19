package info.andchelp.fitwf.repository;

import info.andchelp.fitwf.model.MailMessage;
import info.andchelp.fitwf.model.User;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface MailMessageRepository extends AbstractRepository<MailMessage> {

    int countByUserAndCreatedAtAfter(User user, Date date);

}
