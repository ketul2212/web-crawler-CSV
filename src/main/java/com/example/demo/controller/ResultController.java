package com.example.demo.controller;

import com.example.demo.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ResultController {

    @Autowired
    private ResultService resultService;

    @GetMapping("/")
    public String welcome() {
        return "index.html";
    }

    @PostMapping("/search")
    @ResponseStatus(value=HttpStatus.OK)
    public String searchResult(String searchTerm, HttpServletResponse response) throws IOException {
    	resultService.searchResult(searchTerm, response);
    	return "redirect:/index.html"; 
    }
}
