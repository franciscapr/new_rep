package com.duoc.backend.care;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/care")
public class CareController {

    @Autowired
    private CareRepository careRepository;

    @GetMapping
    public List<Care> getAllCares() {
        return (List<Care>) careRepository.findAll();
    }

    @GetMapping("/{id}")
    public Care getCareById(@PathVariable Long id) {
        return careRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Care saveCare(@RequestBody Care service) {
        return careRepository.save(service);
    }

    @PutMapping("/{id}")
    public Care updateCare(
            @PathVariable Long id,
            @RequestBody Care careDetails) {

        Care care = careRepository.findById(id).orElse(null);

        if (care == null) {
            return null;
        }

        care.setName(careDetails.getName());
        care.setCost(careDetails.getCost());

        return careRepository.save(care);
    }

    @DeleteMapping("/{id}")
    public void deleteCare(@PathVariable Long id) {
        careRepository.deleteById(id);
    }
}