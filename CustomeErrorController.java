package com.NotesService.Controller;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomeErrorController implements ErrorController {

    @GetMapping("/error")
    public ResponseEntity<String> handleError() {
        return new ResponseEntity<>("404 Not Found", HttpStatus.NOT_FOUND);
    }
}
