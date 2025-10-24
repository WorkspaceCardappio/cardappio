package br.com.cardappio.domain.additional;

import br.com.cardappio.domain.additional.dto.FlutterAdditionalDTO;
import br.com.cardappio.domain.order.dto.ProductAdditionalDTO;
import br.com.cardappio.domain.order.dto.ProductAdditionalItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdditionalService {

    private final AdditionalRepository repository;

    public List<ProductAdditionalDTO> findByProductIdToOrder(final UUID productId) {

        final List<ProductAdditionalDTO> products = repository.findByProductToOrder(productId);

        final Map<UUID, ProductAdditionalDTO> grouped = products.stream()
                .collect(Collectors.toMap(
                        ProductAdditionalDTO::id,
                        dto -> {
                            dto.items().add(new ProductAdditionalItemDTO(dto.itemId(), dto.size(), dto.price()));
                            return dto;
                        },
                        (existing, incoming) -> {
                            existing.items().add(new ProductAdditionalItemDTO(incoming.itemId(), incoming.size(), incoming.price()));
                            return existing;
                        }
                ));

        return new ArrayList<>(grouped.values());

    }

    public List<FlutterAdditionalDTO> findFlutterAdditionals(UUID idRestaurant) {

        return repository.findFlutterAdditionals(idRestaurant);
    }

}
