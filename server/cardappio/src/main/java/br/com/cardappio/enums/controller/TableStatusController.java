package br.com.cardappio.enums.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cardappio.enums.TableStatus;
import br.com.cardappio.utils.EnumDTO;

@RestController
@RequestMapping("/api/table-status")
public class TableStatusController {

    @GetMapping
    public List<EnumDTO> find() {
        return Arrays.stream(TableStatus.values())
                .map(TableStatus::toDTO)
                .toList();
    }

}
