package com.myinsurance.www.personalhealthcareportal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO a todo message to demo Jenkins source code analysis capabilities
 */
@RestController
public class PersonalHealthcarePortalController {
    @RequestMapping("/")
    public String index() {
        return "Greetings from Personal Healthcare Portal PHP-18";
    }
}
