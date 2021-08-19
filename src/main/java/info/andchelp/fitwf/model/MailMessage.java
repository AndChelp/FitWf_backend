package info.andchelp.fitwf.model;

import info.andchelp.fitwf.model.enums.MailMessageType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mail_message")
public class MailMessage extends AbstractIdentityEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false, length = 10000)
    private String text;

    @Enumerated(EnumType.STRING)
    private MailMessageType type;

}
