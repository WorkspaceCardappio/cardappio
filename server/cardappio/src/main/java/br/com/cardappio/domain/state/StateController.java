package br.com.cardappio.domain.state;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.perplexhub.rsql.RSQLJPASupport;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/states")
@RequiredArgsConstructor
public class StateController {

    private final StateRepositoy repositoy;

    @GetMapping
    public ResponseEntity<List<State>> findAll(@RequestParam(value = "search", defaultValue = "") String search) {
        return ResponseEntity.ok(repositoy.findAll(RSQLJPASupport.toSpecification(search)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> findById(@PathVariable final Long id) {
        final State state = repositoy.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State not found"));

        return ResponseEntity.ok(state);
    }

}
