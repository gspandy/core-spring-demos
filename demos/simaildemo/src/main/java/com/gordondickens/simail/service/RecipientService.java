package com.gordondickens.simail.service;

import com.gordondickens.simail.domain.Recipient;
import com.gordondickens.simail.integration.MailGateway;
import com.gordondickens.simail.repository.RecipientRepository;

import java.util.List;

public interface RecipientService {
    public abstract long countAllRecipients();

    public abstract void deleteRecipient(Recipient recipient);

    public abstract Recipient findRecipient(Long id);

    public abstract List<Recipient> findAllRecipients();

    public abstract List<Recipient> findRecipientEntries(int firstResult, int maxResults);

    public abstract void saveRecipient(Recipient recipient);

    public abstract Recipient updateRecipient(Recipient recipient);

    public abstract void setRecipientRepository(RecipientRepository recipientRepository);

    public abstract void setMailGateway(MailGateway mailGateway);
}
