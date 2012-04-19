package com.gordondickens.mvcflashscope.web;

import com.gordondickens.mvcflashscope.domain.Account;
import com.gordondickens.mvcflashscope.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String accountList(Model model) {
        logger.debug("************** ***************************** **************");
        logger.debug("************** RM=/ - GET - Finding Accounts **************");
        logger.debug("************** ***************************** **************");
        model.addAttribute("accounts", accountService.findAllAccounts());
        return "accountList";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String accountDetails(@PathVariable("id") long id, Model model) {
        logger.debug("************** *********************************** **************");
        logger.debug("************** RM=/id, GET - Finding Account By Id **************");
        logger.debug("************** *********************************** **************");
        model.addAttribute("account", accountService.findAccount(id));
        return "accountEdit";
//
//        return "accountDetails";
    }

//    @RequestMapping(value = "/{id}", params = "form", produces = "text/html", method = RequestMethod.GET)
//    public String accountEdit(@PathVariable("id") long id, Model model) {
//        model.addAttribute("account", accountService.findAccount(id));
//        return "accountEdit";
//    }


    @RequestMapping(value = "/{id}", produces = "text/html", method = RequestMethod.POST)
    public String save(@Valid Account account, BindingResult bindingResult, Model uiModel,
                         HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {
        logger.debug("************** *********************************** **************");
        logger.debug("************** RM=/id, POST - SAVING Account By Id **************");
        logger.debug("************** *********************************** **************");
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, account);
            return "accountEdit";
        }
        uiModel.asMap().clear();
        accountService.saveAccount(account);


        redirectAttributes.addAttribute("successResults", "ACCOUNT SAVED --- WOO HOO!");

        String redirectURL = "redirect:/accounts/" +
                encodeUrlPathSegment(account.getId().toString(), httpServletRequest);

//        logHttpHeaders(httpServletRequest);

        logger.debug("Redirecting to URL: {}", redirectURL);

        return redirectURL;
    }






    void populateEditForm(Model uiModel, Account account) {
        uiModel.addAttribute("account", account);
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
            logger.error("Oh Snap!", uee);
        }
        return pathSegment;
    }


    //TODO Fix the Stack-tastic-overflow code below
    private void logHttpHeaders(HttpServletRequest httpServletRequest) {
        Enumeration enumeration = httpServletRequest.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            Object header = httpServletRequest.getHeaderNames().nextElement();
            if (header instanceof String) {
                String hdr = (String) header;
                String value = httpServletRequest.getHeader(hdr);
                logger.debug("************ HTTP Header: {}={} ************", header, value);
            } else {
                logger.debug("************ Skipping Header {} ************", header.toString());
            }
        }
    }


}
