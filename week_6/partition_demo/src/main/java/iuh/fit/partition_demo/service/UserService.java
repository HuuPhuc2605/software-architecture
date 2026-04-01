package iuh.fit.partition_demo.service;
import iuh.fit.partition_demo.model.UserFemale;
import iuh.fit.partition_demo.model.UserMale;
import iuh.fit.partition_demo.repository.UserFemaleRepository;
import iuh.fit.partition_demo.repository.UserMaleRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMaleRepository maleRepo;
    @Autowired
    private UserFemaleRepository femaleRepo;

    public void saveUser(String name, String gender) {
        if ("nam".equalsIgnoreCase(gender)) {
            UserMale u = new UserMale();
            u.setName(name);
            u.setGender(gender);
            maleRepo.save(u);
        } else {
            UserFemale u = new UserFemale();
            u.setName(name);
            u.setGender(gender);
            femaleRepo.save(u);
        }
    }
}
