package ufcg.edu.br.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class helpController {

    @RequestMapping("/")
    public String voltar(){
        return "index";
    }
}
