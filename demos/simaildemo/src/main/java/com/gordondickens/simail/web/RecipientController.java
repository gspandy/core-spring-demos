package com.gordondickens.simail.web;

import com.gordondickens.simail.domain.Recipient;
import com.gordondickens.simail.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RequestMapping("/recipients")
@Controller
public class RecipientController {

    @RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> showJson(@PathVariable("id") Long id) {
        Recipient recipient = itemService.findRecipient(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (recipient == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(recipient.toJson(), headers, HttpStatus.OK);
    }

    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<Recipient> result = itemService.findAllRecipients();
        return new ResponseEntity<String>(Recipient.toJsonArray(result), headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJson(@RequestBody String json) {
        Recipient recipient = Recipient.fromJsonToRecipient(json);
        itemService.saveRecipient(recipient);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> createFromJsonArray(@RequestBody String json) {
        for (Recipient recipient : Recipient.fromJsonArrayToRecipients(json)) {
            itemService.saveRecipient(recipient);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        Recipient recipient = Recipient.fromJsonToRecipient(json);
        if (itemService.updateRecipient(recipient) == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (Recipient recipient : Recipient.fromJsonArrayToRecipients(json)) {
            if (itemService.updateRecipient(recipient) == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> deleteFromJson(@PathVariable("id") Long id) {
        Recipient recipient = itemService.findRecipient(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (recipient == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        itemService.deleteRecipient(recipient);
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @Autowired
    RecipientService itemService;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Recipient recipient, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, recipient);
            return "recipients/create";
        }
        uiModel.asMap().clear();
        itemService.saveRecipient(recipient);
        return "redirect:/recipients/" + encodeUrlPathSegment(recipient.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Recipient());
        return "recipients/create";
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("recipient", itemService.findRecipient(id));
        uiModel.addAttribute("itemId", id);
        return "recipients/show";
    }

    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("recipients", itemService.findRecipientEntries(firstResult, sizeNo));
            float nrOfPages = (float) itemService.countAllRecipients() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("recipients", itemService.findAllRecipients());
        }
        return "recipients/list";
    }

    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Recipient recipient, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, recipient);
            return "recipients/update";
        }
        uiModel.asMap().clear();
        itemService.updateRecipient(recipient);
        return "redirect:/recipients/" + encodeUrlPathSegment(recipient.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, itemService.findRecipient(id));
        return "recipients/update";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Recipient recipient = itemService.findRecipient(id);
        itemService.deleteRecipient(recipient);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/recipients";
    }

    void populateEditForm(Model uiModel, Recipient recipient) {
        uiModel.addAttribute("recipient", recipient);
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }

// public class RecipientController {
// @Autowired
// MailGateway mailGateway;
//
// @RequestMapping(method = RequestMethod.POST)
// public String create(@Valid Recipient recipient, BindingResult result,
// ModelMap modelMap) {
// if (result.hasErrors()) {
// modelMap.addAttribute("recipient", recipient);
// return "recipients/create";
// }
// recipient.persist();
// mailGateway.sendMail(recipient);
// return "redirect:/recipients/" + recipient.getId();
// }
// }


}
