package com.pisarevAS.familybudget.transaction.controller;

import com.pisarevAS.familybudget.transaction.model.TransactionDto;
import com.pisarevAS.familybudget.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/transaction")
    public String transactionPage(Model model) {
        model.addAttribute("transaction", new TransactionDto());
        return "transaction";
    }

    @PostMapping("/transaction/save")
    public String saveTransaction(@RequestParam String date,
                                  @RequestParam String type,
                                  @RequestParam String category,
                                  @RequestParam String count,
                                  Model model) {

        transactionService.create(date, type, category, count);
        model.addAttribute("transaction", new TransactionDto());
        return "transaction";
    }

    @PostMapping("transaction/patch")
    public String update(@RequestParam String transactionId,
                         @RequestParam(required = false) String date,
                         @RequestParam(required = false) String category,
                         @RequestParam(required = false) String type,
                         @RequestParam(required = false) String count,
                         Model model) {

        if (date.isBlank() && type.isBlank() && category.isBlank() && count.isBlank()) {
            model.addAttribute("transaction", new TransactionDto());
            return "transaction";
        }

        transactionService.update(transactionId, date, type, category, count);
        model.addAttribute("transaction", new TransactionDto());
        return "transaction";
    }

    @PostMapping("/transaction/delete")
    public String delete(@RequestParam long transactionId, Model model) {
        transactionService.delete(transactionId);
        model.addAttribute("transaction", new TransactionDto());
        return "transaction";
    }

    @GetMapping("/transactionget")
    public String findBySearch(@RequestParam(required = false) String transactionId,
                               @RequestParam(required = false) String startDate,
                               @RequestParam(required = false) String endDate,
                               Model model) {
        if (transactionId == null && startDate == null && endDate == null) {
            return "transactionget";
        } else if (transactionId == null && startDate != null && endDate != null) {
            List<String> headers = Arrays.asList("id", "date", "type", "category", "count");
            List<Map<String, Object>> rows = new ArrayList<>();
            rows.add(Map.of("id", "1", "date", "2020-12-20'T'12:12", "type", "COMING", "category", "SALARY",
                    "count", "100"));
            rows.add(Map.of("id", "2", "date", "2021-12-20'T'12:12", "type", "COMING", "category", "SALARY",
                    "count", "200"));
            model.addAttribute("headers", headers);
            model.addAttribute("rows", rows);
            return "transactionget";

            return transactionService.findByDateRange(startDate, endDate);
        } else if (transactionId != null && startDate == null && endDate == null) {
            return transactionService.findById(transactionId);
        } else {
            return "transactionget";
        }
    }
}