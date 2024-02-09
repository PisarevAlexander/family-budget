package com.pisarevAS.familybudget.transaction.controller;

import com.pisarevAS.familybudget.transaction.model.Transaction;
import com.pisarevAS.familybudget.transaction.model.TransactionDto;
import com.pisarevAS.familybudget.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

        List<Transaction> transactions = transactionService.findById(transactionId);
        List<TransactionDto> transactionDtos = transactionService.createTransactionsDtos(transactions);
        model.addAttribute("transactions", transactionDtos);
        return "transactionget";
    }
}