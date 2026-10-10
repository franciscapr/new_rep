package com.duoc.backend.medication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    public List<Medication> getAllMedications() {
        return (List<Medication>) medicationRepository.findAll();
    }

    public Medication getMedicationById(Long id) {
        return medicationRepository.findById(id).orElse(null);
    }

    public Medication saveMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }

    public Medication updateMedication(Long id, Medication medicationDetails) {
    Medication medication = medicationRepository.findById(id).orElse(null);

    if (medication == null) {
        return null;
    }

    medication.setName(medicationDetails.getName());
    medication.setCost(medicationDetails.getCost());

    return medicationRepository.save(medication);
}
}