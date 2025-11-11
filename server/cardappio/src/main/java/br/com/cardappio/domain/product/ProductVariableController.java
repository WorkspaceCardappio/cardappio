//package br.com.cardappio.domain.product;
//
//import br.com.cardappio.domain.product.dto.FlutterProductVariableDTO;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/product-variables")
//@RequiredArgsConstructor
//public class ProductVariableController {
//
//    private final ProductVariableService service;
//
//    @GetMapping("/{idProduct}/flutter-product-variables")
//    public ResponseEntity<List<FlutterProductVariableDTO>> findFlutterProductVariables(@PathVariable UUID idProduct) {
//
//        return ResponseEntity.ok(service.findFlutterProductVariables(idProduct));
//    }
//}
