package com.giantdwarf.demospringsecurity.form;

import com.giantdwarf.demospringsecurity.account.Account;
import com.giantdwarf.demospringsecurity.account.AccountContext;
import com.giantdwarf.demospringsecurity.account.AccountRepository;
import com.giantdwarf.demospringsecurity.account.UserAccount;
import com.giantdwarf.demospringsecurity.book.BookRepository;
import com.giantdwarf.demospringsecurity.common.CurrentUser;
import com.giantdwarf.demospringsecurity.common.SecurityLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.Callable;

@Controller
public class SampleController {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/")
    public String index(Model model, @CurrentUser Account account){

        if(account == null){
            model.addAttribute("message","Hello spring security");
        } else {
            model.addAttribute("message","Hello "+ account.getUsername());
        }

        return "index";
    }
    @GetMapping("/info")
    public String info(Model model){
        model.addAttribute("message","info");
        return "info";
    }
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        model.addAttribute("message","Hello "+principal.getName());
//        AccountContext.setAccount(accountRepository.findByUsername(principal.getName()));
        sampleService.dashboard();
        return "dashboard";
    }
    @GetMapping("/admin")
    public String admin(Model model, Principal principal){
        model.addAttribute("message","Hello admin "+principal.getName());
        return "admin";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal){
        model.addAttribute("message","Hello user "+principal.getName());
        model.addAttribute("books",bookRepository.findCurrentUserBooks());
        return "user";
    }

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler() {
        SecurityLogger.log("MVC");
        return () -> {
            SecurityLogger.log("Callable");
            return "Async Handler";
        };
    }

    @GetMapping("/async-service")
    @ResponseBody
    public String asyncService() {
        SecurityLogger.log("MVC, before async service");
        sampleService.asyncService();
        SecurityLogger.log("MVC, after async service");
        return "Async Service";
    }
}
