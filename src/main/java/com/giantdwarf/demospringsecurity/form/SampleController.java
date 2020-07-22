package com.giantdwarf.demospringsecurity.form;

import com.giantdwarf.demospringsecurity.account.AccountContext;
import com.giantdwarf.demospringsecurity.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SampleController {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/")
    public String index(Model model, Principal principal){
        if(principal == null){
            model.addAttribute("message","Hello spring security");
        } else {
            model.addAttribute("message","Hello "+ principal.getName());
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
}
