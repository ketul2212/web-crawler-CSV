package com.example.demo.controller;

import com.example.demo.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/")
    public String welcome() {
        return "index.jsp";
    }

    @PostMapping("/serach")
    public ModelAndView serachResult(String searchTerm) throws IOException {
        return resultService.searchResult(searchTerm);
    }

    @GetMapping("/download")
    public void getCSV(HttpServletResponse response) throws IOException {

        resultService.getCSV(response);

//       return "redirect:/";
    }
}
