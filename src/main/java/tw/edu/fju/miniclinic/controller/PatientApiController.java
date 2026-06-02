package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.edu.fju.miniclinic.model.Patient;
import tw.edu.fju.miniclinic.model.PatientRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class PatientApiController {
    @Autowired
    private PatientRepository patientRepo;

    @GetMapping("/api/patients")
    public List<Patient> getPatients(
            @RequestParam(required = false) String gender) {
        if (gender == null || gender.isBlank()) {
            return patientRepo.findAll();
        }
        return patientRepo.findByGender(gender);
    }

    @GetMapping("/api/patients/{chartNo}")
    public ResponseEntity<Patient> getPatient(@PathVariable String chartNo) {
        Optional<Patient> patient = patientRepo.findById(chartNo);
        return patient
            .map(p -> ResponseEntity.ok(p))       // 有 → 200 OK + 資料
            .orElse(ResponseEntity.notFound().build());  // 沒有 → 404
    }

    @GetMapping("/api/genders")
    public List<String> getGenders() {
        return patientRepo.findAllGenders();
    }


}
