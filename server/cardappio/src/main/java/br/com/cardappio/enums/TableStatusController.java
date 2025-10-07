package br.com.cardappio.enums;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/table-status")
public class TableStatusController {

    @GetMapping
    public ResponseEntity<List<EnumCodigoDescricaoDTO>> findAll() {
        return ResponseEntity.ok(TableStatus.valuesToDTO());
    }

}
