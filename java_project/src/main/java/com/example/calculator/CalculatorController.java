package com.example.calculator;

import com.example.calculator.grpc.CalculatorGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculatorController {

    @Autowired
    private CalculatorGrpcClient calculatorGrpcClient;

    @GetMapping("/")
    public String showCalculator() {
        return "calculator";
    }

    @PostMapping("/add")
    public String add(@RequestParam double a, @RequestParam double b, Model model) {
        double result = calculatorGrpcClient.add(a, b);
        model.addAttribute("result", result);
        return "calculator";
    }
} 