package com.gordondickens.mvcflashscope.web;

import com.gordondickens.mvcflashscope.domain.Account;
import com.gordondickens.mvcflashscope.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/accounts/{id}")
    public String accountDetails(@PathVariable("id") long id, Model model) {
        model.addAttribute("account", accountService.findAccount(id));
        return "accountDetails";
    }

    @RequestMapping("/accounts")
    public String accountList(Model model) {
        model.addAttribute("accounts", accountService.findAllAccounts());
        return "accountList";
    }


    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Account account, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, account);
            return "accounts/create";
        }
        uiModel.asMap().clear();
        accountService.saveAccount(account);
        return "redirect:/accounts/" + encodeUrlPathSegment(account.getId().toString(), httpServletRequest);
    }


    void populateEditForm(Model uiModel, Account account) {
        uiModel.addAttribute("customer", account);
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
            // What evah!
        }
        return pathSegment;
    }

}
