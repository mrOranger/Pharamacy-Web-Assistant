package it.pharmacywebassistant.mapper;

import it.pharmacywebassistant.model.*;
import it.pharmacywebassistant.model.dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductMapper implements Function<Product, ProductDTO> {
	
	@Autowired
	private CompanyMapper mapper;
	
    @Override
    public ProductDTO apply(Product product) {
        if(product instanceof Drug) {
            return convertDrugInDrugDTO((Drug)product);
        } else {
            return convertCosmeticInCosmeticDTO((Cosmetic)product);
        }
    }

    private DrugDTO convertDrugInDrugDTO(Drug drug) {
        final CompanyDTO companyDTO = mapper.apply(drug.getCompany());
        return new DrugDTO(drug.getId(), drug.getName(), drug.getDescription(), drug.getCost(), companyDTO, drug.getHasPrescription());
    }

    private CosmeticDTO convertCosmeticInCosmeticDTO(Cosmetic cosmetic) {
        final CompanyDTO companyDTO = mapper.apply(cosmetic.getCompany());
        return new CosmeticDTO(cosmetic.getId(), cosmetic.getName(), cosmetic.getDescription(), cosmetic.getCost(), companyDTO, cosmetic.getType());
    }
}
