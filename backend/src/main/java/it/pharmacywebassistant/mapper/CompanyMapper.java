package it.pharmacywebassistant.mapper;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import it.pharmacywebassistant.model.Company;
import it.pharmacywebassistant.model.dto.CompanyDTO;

public class CompanyMapper implements Function<Company, CompanyDTO>{
	
	@Autowired
	private AddressMapper mapper;

	@Override
	public CompanyDTO apply(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                mapper.apply(company.getAddress()));
	}

}
