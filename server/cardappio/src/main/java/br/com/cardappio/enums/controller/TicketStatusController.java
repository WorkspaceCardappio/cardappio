package br.com.cardappio.enums.controller;

import br.com.cardappio.enums.TicketStatus;
import br.com.cardappio.utils.EnumDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/ticket-status")
public class TicketStatusController {

    @GetMapping
    public List<EnumDTO> find() {
        return Arrays.stream(TicketStatus.values())
                .map(TicketStatus::toDTO)
                .toList();
    }

}
