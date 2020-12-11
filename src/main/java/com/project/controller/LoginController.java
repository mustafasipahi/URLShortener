package com.project.controller;

import com.project.model.LoginToken;
import com.project.model.User;
import com.project.services.LoginTokenService;
import com.project.services.UserService;
import com.project.util.Constants;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

@Controller
public class LoginController extends AbstractController {

    @Autowired
    private UserService userService;
    @Autowired
    private LoginTokenService loginTokenService;

    @PostMapping("/login")
    public String login(
            RedirectAttributes redirectAttributes,
            HttpServletResponse response,
            HttpSession session,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) String remember) {

        Optional<User> userByEmail = userService.findByEmail(email);
        if (!userByEmail.isPresent()) {
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("emailError", "Bu E-posta Adresiyle Kayıtlı Bir Kullanıcı Bulunamadı");
        } else if (!userByEmail.get().checkPassword(password)) {
            redirectAttributes.addFlashAttribute("email", email);
            redirectAttributes.addFlashAttribute("passwordError", "Şifrenizi Yanlış Girdiniz");
        } else {
            LoginToken loginToken = loginTokenService.saveUserLoginToken(userByEmail.get());
            boolean rememberMe = "on".equals(remember);
            Cookie cookie = new Cookie(Constants.LOGGIN_TOKEN_COOKIE_NAME, loginToken.getUUID());
            cookie.setMaxAge(rememberMe ? (int) (loginToken.getExpiration_duration() / 1000) : -1);
            response.addCookie(cookie);
            session.invalidate();
        }
        return "redirect:/";
    }

    @PostMapping("/register")
    public String register(RedirectAttributes redirectAttributes,
                           HttpServletResponse response,
                           @RequestParam String registerEmail,
                           @RequestParam String registerPassword,
                           @RequestParam String registerPasswordAgain){

        redirectAttributes.addFlashAttribute("register",true);

        if (!isValidEmail(registerEmail)){
            redirectAttributes.addFlashAttribute("registerEmail", registerEmail);
            redirectAttributes.addFlashAttribute("registerEmailError", "Lütfen Geçerli Bir Email Adresi Giriniz");
            return "redirect:/";
        }
        try {
            isValidPassword(registerPassword);
        }catch (IllegalArgumentException exception){
            redirectAttributes.addFlashAttribute("registerPasswordError", exception.getMessage());
            return "redirect:/";
        }
        if (!registerPassword.equals(registerPasswordAgain)){
            redirectAttributes.addFlashAttribute("registerPassword",registerPassword);
            redirectAttributes.addFlashAttribute("registerPasswordError", "Şifre Uyuşmuyor");
            return "redirect:/";
        }

        Optional<User> byEmail = userService.findByEmail(registerEmail);
        if (byEmail.isPresent()){
            redirectAttributes.addFlashAttribute("registerEmail", registerEmail);
            redirectAttributes.addFlashAttribute("registerEmailError", "Bu Email İle Kayıtlı Bir Kullanıcı Vardır");
            return "redirect:/";
        }
        User user = new User();
        user.setEmail(registerEmail);
        user.setDateCreated(new Date());
        user.bcryptAndSetPassword(registerPassword);
        userService.save(user);
        LoginToken loginToken = loginTokenService.saveUserLoginToken(user);
        Cookie cookie = new Cookie(Constants.LOGGIN_TOKEN_COOKIE_NAME,loginToken.getUUID());
        cookie.setMaxAge((int)(loginToken.getExpiration_duration()/1000));
        response.addCookie(cookie);
        return "redirect:/";
    }

    private boolean isValidEmail(String email){
        return EmailValidator.getInstance(false).isValid(email);
    }

    private boolean isValidPassword(String password){
        password = password.trim();
        if (password.length() < 5){
            throw new IllegalArgumentException("Şifreniz En Az 5 Karakterli Olmalıdır");
        }
        if (password.length() > 16){
            throw new IllegalArgumentException("Şifreniz En Fazla 16 Karakterli Olmalıdır");
        }
        boolean containsDigitAndLetter = containsDigitAndLetter(password);
        if (!containsDigitAndLetter){
            throw new IllegalArgumentException("Şifreniz En Az 1 Karakter ve Harf İçermelidir");
        }
        return true;
    }

    private boolean containsDigitAndLetter(String password){
        boolean containsDigit = false;
        boolean containsLetter = false;
        for(Character c : password.toCharArray()){
            if (Character.isDigit(c)) containsDigit = true;
            if (Character.isLetter(c)) containsLetter = true;
        }
        return containsDigit && containsLetter;
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session, HttpServletResponse response){
        LoginToken loginToken = getLoginToken();
        loginToken.setDateLoggedOut(new Date());
        loginTokenService.save(loginToken);

        Cookie cookie = new Cookie(Constants.LOGGIN_TOKEN_COOKIE_NAME,"");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        session.invalidate();
        return "redirect:/";
    }
}