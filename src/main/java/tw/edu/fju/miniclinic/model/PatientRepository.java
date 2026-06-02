package tw.edu.fju.miniclinic.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, String> {

    List<Patient> findByName(String name);

    List<Patient> findByGender(String gender);

    @Query("SELECT DISTINCT p.gender FROM Patient p ORDER BY p.gender")
    List<String> findAllGenders();
}