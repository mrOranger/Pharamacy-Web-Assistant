package it.pharmacywebassistant.mapper;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.pharmacywebassistant.model.Drug;
import it.pharmacywebassistant.model.dto.DrugDTO;

@Service
public class DrugMapper implements Function<Drug, DrugDTO> {

	@Autowired
	private CompanyMapper mapper;
	
	@Override
	public DrugDTO apply(Drug drug) {
		return new DrugDTO(drug.getId(), 
				drug.getName(), 
				drug.getDescription(), 
				drug.getCost(), 
				mapper.apply(drug.getCompany()),
				drug.getHasPrescription());
	}

}
