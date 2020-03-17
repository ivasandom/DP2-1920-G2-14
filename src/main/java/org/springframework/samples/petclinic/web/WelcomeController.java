package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WelcomeController {
	
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    

	    return "welcome";
	  }
	  
	 @GetMapping("login")
	 public String login(@RequestParam(value = "error", required = false) String error,
			 Map<String, Object> model) {
		 if (error != null) {
			 model.put("error", "Invalid username or password");
		 }
		 return "users/login";
	 }
}
