package com.gordondickens.simail.domain;

import com.gordondickens.simail.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Component
@Configurable
public class RecipientDataOnDemand {

    private Random rnd = new SecureRandom();

    private List<Recipient> data;

    @Autowired
    RecipientService itemService;

    @Autowired
    com.gordondickens.simail.repository.RecipientRepository recipientRepository;

    public Recipient getNewTransientRecipient(int index) {
        Recipient obj = new Recipient();
        setMessageBody(obj, index);
        setRecipientEmail(obj, index);
        setSubject(obj, index);
        return obj;
    }

    public void setMessageBody(Recipient obj, int index) {
        String messageBody = "messageBody_" + index;
        obj.setMessageBody(messageBody);
    }

    public void setRecipientEmail(Recipient obj, int index) {
        String recipientEmail = "foo" + index + "@bar.com";
        obj.setRecipientEmail(recipientEmail);
    }

    public void setSubject(Recipient obj, int index) {
        String subject = "subject_" + index;
        obj.setSubject(subject);
    }

    public Recipient getSpecificRecipient(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Recipient obj = data.get(index);
        Long id = obj.getId();
        return itemService.findRecipient(id);
    }

    public Recipient getRandomRecipient() {
        init();
        Recipient obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return itemService.findRecipient(id);
    }

    public boolean modifyRecipient(Recipient obj) {
        return false;
    }

    public void init() {
        int from = 0;
        int to = 10;
        data = itemService.findRecipientEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Recipient' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }

        data = new ArrayList<Recipient>();
        for (int i = 0; i < 10; i++) {
            Recipient obj = getNewTransientRecipient(i);
            try {
                itemService.saveRecipient(obj);
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext(); ) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            recipientRepository.flush();
            data.add(obj);
        }
    }
}
