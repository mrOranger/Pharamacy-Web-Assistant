package it.pharmacywebassistant.services;

import it.pharmacywebassistant.model.Doctor;
import it.pharmacywebassistant.model.Patient;
import it.pharmacywebassistant.model.Prescription;
import it.pharmacywebassistant.model.dto.PrescriptionDTO;
import it.pharmacywebassistant.service.PrescriptionService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public final class TestPrescriptionService {

    @Autowired
    private PrescriptionService service;
    private static Prescription prescription;

    @BeforeAll
    public static void beforeAll() {

        Patient patient = new Patient("AB123", "Mario", "Rossi", Date.valueOf(LocalDate.now()));
        Doctor doctor = new Doctor("AK345", "Federico", "Verdi", Date.valueOf(LocalDate.now()));
        prescription = new Prescription();
        prescription.setDoctor(doctor);
        prescription.setPatient(patient);

    }

    @Test @Order(1)
    public void testFindAllPrescriptionsReturnsArrayOfSizeZero() {
        assertThat(service.findAll().size()).isEqualTo(0);
    }

    @Test @Order(2)
    public void testFindPrescriptionByIdReturnsEmptyOptional() {
        assertThat(service.findById(1L).isEmpty()).isEqualTo(true);
    }

    @Test @Order(3)
    public void testAddNewPrescription() {
        final PrescriptionDTO savedPrescription = service.save(prescription);
        assertThat(savedPrescription.getId()).isEqualTo(prescription.getId());
        assertThat(savedPrescription.getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());
        assertThat(savedPrescription.getPatient().getTaxCode()).isEqualTo(prescription.getPatient().getTaxCode());

        assertThat(service.findAll().size()).isEqualTo(1);
        assertThat(service.findAll().get(0).getPatient().getTaxCode()).isEqualTo(prescription.getPatient().getTaxCode());
        assertThat(service.findAll().get(0).getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());

        assertThat(service.findById(savedPrescription.getId()).isPresent()).isEqualTo(true);
        assertThat(service.findById(savedPrescription.getId()).get().getPatient().getTaxCode()).isEqualTo(prescription.getPatient().getTaxCode());
        assertThat(service.findById(savedPrescription.getId()).get().getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());

    }

    @Test @Order(4)
    public void testUpdatePrescriptiongetDoctor() {

        prescription.setDoctor(new Doctor("OL091", "Federico", "Verdi", Date.valueOf(LocalDate.now())));

        PrescriptionDTO savedPrescription = service.save(prescription);
        assertThat(savedPrescription.getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());
        assertThat(savedPrescription.getPatient().getTaxCode()).isEqualTo(prescription.getPatient().getTaxCode());

        assertThat(service.findAll().size()).isEqualTo(1);
        assertThat(service.findAll().get(0).getPatient().getTaxCode()).isEqualTo(prescription.getPatient().getTaxCode());
        assertThat(service.findAll().get(0).getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());

        assertThat(service.findById(savedPrescription.getId()).isPresent()).isEqualTo(true);
        assertThat(service.findById(savedPrescription.getId()).get().getPatient().getTaxCode()).isEqualTo(prescription.getPatient().getTaxCode());
        assertThat(service.findById(savedPrescription.getId()).get().getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());

    }

    @Test @Order(5)
    public void testUpdatePrescriptiongetPatient() {
        prescription.setPatient(new Patient("PP091", "Mario", "Rossi", Date.valueOf(LocalDate.now())));

        PrescriptionDTO savedPrescription = service.save(prescription);
        assertThat(savedPrescription.getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());
        assertThat(savedPrescription.getPatient().getTaxCode()).isEqualTo(prescription.getPatient().getTaxCode());

        assertThat(service.findAll().size()).isEqualTo(1);
        assertThat(service.findAll().get(0).getPatient().getTaxCode()).isEqualTo(prescription.getPatient().getTaxCode());
        assertThat(service.findAll().get(0).getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());

        assertThat(service.findById(savedPrescription.getId()).isPresent()).isEqualTo(true);
        assertThat(service.findById(savedPrescription.getId()).get().getPatient().getTaxCode()).isEqualTo(prescription.getPatient().getTaxCode());
        assertThat(service.findById(savedPrescription.getId()).get().getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());
    }

    @Test @Order(6)
    public void testDeletePrescription() {

        service.deleteById(1L);
        assertThat(service.findAll().size()).isEqualTo(0);
        assertThat(service.findById(prescription.getId()).isEmpty()).isEqualTo(true);

    }

    @Test @Order(7)
    public void testDeleteAllPrescriptions() {

        final PrescriptionDTO savedPrescription = service.save(prescription);
        assertThat(savedPrescription.getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());
        assertThat(savedPrescription.getPatient().getTaxCode()).isEqualTo(prescription.getPatient().getTaxCode());

        assertThat(service.findAll().size()).isEqualTo(1);
        assertThat(service.findAll().get(0).getPatient().getTaxCode()).isEqualTo(prescription.getPatient().getTaxCode());
        assertThat(service.findAll().get(0).getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());

        assertThat(service.findById(savedPrescription.getId()).isPresent()).isEqualTo(true);
        assertThat(service.findById(savedPrescription.getId()).get().getPatient().getTaxCode()).isEqualTo(prescription.getPatient().getTaxCode());
        assertThat(service.findById(savedPrescription.getId()).get().getDoctor().getTaxCode()).isEqualTo(prescription.getDoctor().getTaxCode());

        service.deleteAll();

        assertThat(service.findAll().size()).isEqualTo(0);
        assertThat(service.findById(savedPrescription.getId()).isEmpty()).isEqualTo(true);

    }
}
