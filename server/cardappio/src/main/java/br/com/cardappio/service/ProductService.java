package br.com.cardappio.service;

import br.com.cardappio.DTO.ProductDTO;
import br.com.cardappio.adapter.ProductAdapter;
import br.com.cardappio.entity.Product;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService extends CrudService<Product, UUID, ProductDTO, ProductDTO> {


    @Override
    protected Adapter<Product, ProductDTO, ProductDTO> getAdapter() {
        return new ProductAdapter();
    }
}
