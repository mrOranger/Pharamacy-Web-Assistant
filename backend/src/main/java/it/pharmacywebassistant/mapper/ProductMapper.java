package it.pharmacywebassistant.mapper;

import it.pharmacywebassistant.model.*;
import it.pharmacywebassistant.model.dto.*;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductMapper implements Function<Product, ProductDTO> {
    @Override
    public ProductDTO apply(Product product) {
        if(product instanceof Drug) {
            return convertDrugInDrugDTO((Drug)product);
        } else {
            return convertCosmeticInCosmeticDTO((Cosmetic)product);
        }
    }

    private static DrugDTO convertDrugInDrugDTO(Drug drug) {
        final CompanyDTO companyDTO = convertCompanyInCompanyDTO(drug.getCompany());
        return new DrugDTO(drug.getId(), drug.getName(), drug.getDescription(), drug.getCost(), companyDTO, drug.getHasPrescription());
    }

    private static CosmeticDTO convertCosmeticInCosmeticDTO(Cosmetic cosmetic) {
        final CompanyDTO companyDTO = convertCompanyInCompanyDTO(cosmetic.getCompany());
        return new CosmeticDTO(cosmetic.getId(), cosmetic.getName(), cosmetic.getDescription(), cosmetic.getCost(), companyDTO, cosmetic.getType());
    }

    private static AddressDTO convertAddressInAddressDTO(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getStreetName(),
                address.getStreetCode(),
                address.getCity(),
                address.getNation());
    }

    private static CompanyDTO convertCompanyInCompanyDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                convertAddressInAddressDTO(company.getAddress())
        );
    }
}
