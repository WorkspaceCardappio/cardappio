package br.com.cardappio.domain.state;

import io.github.perplexhub.rsql.RSQLJPASupport;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {

    private final StateRepository repositoy;

    @GetMapping
    public ResponseEntity<List<State>> findAll(@RequestParam(value = "search", required = false, defaultValue = "") final String search) {
        return ResponseEntity.ok(repositoy.findAll(RSQLJPASupport.toSpecification(search)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> findById(@PathVariable final Long id) {
        final State state = repositoy.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State not found"));

        return ResponseEntity.ok(state);
    }

}
