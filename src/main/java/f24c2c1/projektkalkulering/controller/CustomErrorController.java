package f24c2c1.projektkalkulering.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Custom Error Controller for handling HTTP error codes.
 */
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 403) {
                return "error/403"; // Forbidden Error Page
            } else if (statusCode == 404) {
                return "error/404"; // Not Found Error Page
            } else if (statusCode == 500) {
                return "error/500"; // Internal Server Error Page
            }
        }
        return "error/error"; // Fallback Error Page
    }

    public String getErrorPath() {
        return "/error";
    }
}
