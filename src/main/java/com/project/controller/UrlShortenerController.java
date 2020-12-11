package com.project.controller;

import com.project.model.LoginToken;
import com.project.services.UrlShortenerService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UrlShortenerController extends AbstractController{

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping(value = "/shorten")
    public String shorten(RedirectAttributes redirectAttributes, @RequestParam String longUrl){

        if (!UrlValidator.getInstance().isValid(longUrl)){
            redirectAttributes.addFlashAttribute("longUrl", longUrl);
            redirectAttributes.addFlashAttribute("longUrlError", "Geçerli Bir Url Adresi Girmelisiniz.");
            return "redirect:/";
        }

        LoginToken loginToken = getLoginToken();
        String shortUrl = urlShortenerService.shorten(longUrl, loginToken);

        redirectAttributes.addFlashAttribute("newUrl", true);
        redirectAttributes.addFlashAttribute("shortUrl", shortUrl);

        return "redirect:/";
    }
}
