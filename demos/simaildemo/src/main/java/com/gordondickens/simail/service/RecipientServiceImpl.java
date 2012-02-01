package com.gordondickens.simail.service;

import com.gordondickens.simail.domain.Recipient;
import com.gordondickens.simail.integration.MailGateway;
import com.gordondickens.simail.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class RecipientServiceImpl implements RecipientService {

    @Autowired
    RecipientRepository recipientRepository;

    @Autowired
    MailGateway mailGateway;

    public long countAllRecipients() {
        return recipientRepository.count();
    }

    public void deleteRecipient(Recipient recipient) {
        recipientRepository.delete(recipient);
    }

    public Recipient findRecipient(Long id) {
        return recipientRepository.findOne(id);
    }

    public List<Recipient> findAllRecipients() {
        return recipientRepository.findAll();
    }

    public List<Recipient> findRecipientEntries(int firstResult, int maxResults) {
        return recipientRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
    }

    public void saveRecipient(Recipient recipient) {
        recipientRepository.save(recipient);
        //HERE Is the Mail Sender
        mailGateway.sendMail(recipient);
    }

    public Recipient updateRecipient(Recipient recipient) {
        return recipientRepository.save(recipient);
    }
}
