package br.com.cardappio.service;

import br.com.cardappio.DTO.ProductDTO;
import br.com.cardappio.adapter.ProductAdapter;
import br.com.cardappio.entity.Product;
import com.cardappio.core.adapter.Adapter;
import com.cardappio.core.service.CrudService;

import java.util.UUID;

public class ProductService extends CrudService<Product, ProductDTO, UUID> {


    @Override
    protected Adapter<ProductDTO, Product> getAdapter() {
        return new ProductAdapter();
    }
}
