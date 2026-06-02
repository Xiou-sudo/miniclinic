package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import tw.edu.fju.miniclinic.model.Patient;
import tw.edu.fju.miniclinic.model.PatientRepository;


import java.util.List;
import java.util.Optional;


@Controller
public class PatientPageController {

    @Autowired
    private PatientRepository patientRepo;

    @GetMapping("/patients")
    public String listPatients(
            @RequestParam(required = false) String gender,
            Model model) {

        List<Patient> patients;
        if (gender == null || gender.isBlank()) {
            patients = patientRepo.findAll();
        } else {
            patients = patientRepo.findByGender(gender);
        }

        model.addAttribute("patients", patients);
        model.addAttribute("genders", patientRepo.findAllGenders());
        model.addAttribute("selectedGender", gender);

        return "patients";
    }

    @GetMapping("/patients/{chartNo}")
    public String patientDetail(@PathVariable String chartNo, Model model) {
        // 增加 null 檢查，消除 IDE 的 NonNull 警告
        if (chartNo == null) {
            return "redirect:/patients";
        }

        Optional<Patient> patient = patientRepo.findById(chartNo);

        if (patient.isEmpty()) {
            return "redirect:/patients";  // 找不到就回清單頁
        }

        model.addAttribute("patient", patient.get());
        return "patient-detail";
    }
}
