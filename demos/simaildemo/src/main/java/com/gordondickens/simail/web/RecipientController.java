package com.gordondickens.simail.web;

import com.gordondickens.simail.domain.Recipient;
import com.gordondickens.simail.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RequestMapping("/recipients")
@Controller
public class RecipientController {


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
