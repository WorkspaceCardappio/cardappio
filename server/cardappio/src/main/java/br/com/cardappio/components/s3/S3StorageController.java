package br.com.cardappio.components.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/s3-storage")
@RequiredArgsConstructor
public class S3StorageController {

    private final S3StorageComponent component;

    @GetMapping("image/{name}")
    public ResponseEntity<String> getImage(@PathVariable String name) {

        return ResponseEntity.ok(component.getPresignedUrl(name));
    }
}
