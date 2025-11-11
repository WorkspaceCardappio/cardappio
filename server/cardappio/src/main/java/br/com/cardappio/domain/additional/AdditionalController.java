package br.com.cardappio.domain.additional;

import br.com.cardappio.domain.additional.dto.FlutterAdditionalDTO;
import br.com.cardappio.domain.order.dto.ProductAdditionalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/additionals")
public class AdditionalController {

    private final AdditionalService service;

    @GetMapping("/product/{id}")
    public List<ProductAdditionalDTO> findByProductToOrder(@PathVariable final UUID id) {
        return service.findByProductIdToOrder(id);
    }

    @GetMapping("/{idProduct}/flutter-additionals")
    public ResponseEntity<List<FlutterAdditionalDTO>> findFlutterAdditionals(@PathVariable UUID idProduct) {

        return ResponseEntity.ok(service.findFlutterAdditionals(idProduct));
    }
}
