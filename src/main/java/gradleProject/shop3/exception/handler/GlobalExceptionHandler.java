package gradleProject.shop3.exception.handler;

import gradleProject.shop3.exception.ShopException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ShopException.class)
    public String handleShopException(ShopException e, Model model, RedirectAttributes redirectAttributes) {
        log.warn("ShopException 발생: {}", e.getMessage());
        log.warn("ShopException 발생: {}", e.getRedirectUrl());
        e.printStackTrace();

        model.addAttribute("message", e.getMessage());
        model.addAttribute("url", e.getRedirectUrl());
        return "error";
    }

}