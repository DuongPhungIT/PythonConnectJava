package com.example.calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class IndexController {
    
    private final CalculatorController calculatorController;
    private final ImageComparisonController imageComparisonController;
    
    @Autowired
    public IndexController(CalculatorController calculatorController, 
                          ImageComparisonController imageComparisonController) {
        this.calculatorController = calculatorController;
        this.imageComparisonController = imageComparisonController;
    }
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @PostMapping("/calculate")
    public String calculate(@RequestParam double a, @RequestParam double b, Model model) {
        try {
            double result = calculatorController.add(a, b).getBody();
            model.addAttribute("result", result);
            model.addAttribute("a", a);
            model.addAttribute("b", b);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }
    
    @PostMapping("/compare-images")
    public String compareImages(@RequestParam String image1Path, 
                              @RequestParam String image2Path, 
                              Model model) {
        try {
            var response = imageComparisonController.compareImages(image1Path, image2Path);
            
            // Nếu server không khả dụng (503) hoặc response body là null
            if (response.getStatusCode().value() == 503 || response.getBody() == null) {
                model.addAttribute("error", "Không thể kết nối đến server xử lý ảnh. Vui lòng thử lại sau.");
                return "index";
            }
            
            var body = response.getBody();
            model.addAttribute("isSamePerson", body.getIsSamePerson());
            model.addAttribute("image1IsFake", body.getImage1IsFake());
            model.addAttribute("image2IsFake", body.getImage2IsFake());
            model.addAttribute("image1Path", image1Path);
            model.addAttribute("image2Path", image2Path);
            
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi so sánh ảnh: " + e.getMessage());
        }
        return "index";
    }
} 