package com.project.controller;

import com.project.model.LoginToken;
import com.project.model.User;
import com.project.services.LoginTokenService;
import com.project.services.UserService;
import com.project.util.Constants;
import org.apache.tomcat.util.bcel.Const;
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
        } else if (!checkPassword(userByEmail.get(), email, password)) {
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

    public boolean checkPassword(User user, String email, String password) {
        return userService.findByEmail(email)
                .map(value -> value.getPassword().equals(password))
                .orElse(false);
    }
}