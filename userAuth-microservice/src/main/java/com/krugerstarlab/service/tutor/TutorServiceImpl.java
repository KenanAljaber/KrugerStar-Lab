package com.krugerstarlab.service.tutor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.krugerstarlab.dto.LoginResponse;
import com.krugerstarlab.dto.UserProfile;
import com.krugerstarlab.entity.member.Member;
import com.krugerstarlab.entity.tutor.Tutor;
import com.krugerstarlab.repository.TutorRepository;

@Service
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepo;
    
    private PasswordEncoder passwordEncoder;
    
    

    public TutorServiceImpl(TutorRepository tutorRepo,PasswordEncoder passwordEncoder) {
        this.tutorRepo = tutorRepo;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public List<Tutor> getAllTutors() {
        return tutorRepo.findAll();
    }

    @Override
    public Tutor getTutorById(Long tutorId) {
        return tutorRepo.findById(tutorId)
                .orElse(null);
    }

    @Override
    public Tutor createTutor(Tutor tutor) {
    	tutor.setPassword(passwordEncoder.encode(tutor.getPassword()));
    	tutor.setEmail(tutor.getEmail().toLowerCase());
        return tutorRepo.save(tutor);
    }

    @Override
    public Tutor updateTutor(Long tutorId, Tutor tutor) {
        Tutor existingTutor = getTutorById(tutorId);
        if(existingTutor==null) {
        	return null;
        }
        existingTutor.setFirstName(tutor.getFirstName());
        existingTutor.setLastName(tutor.getLastName());
        existingTutor.setEmail(tutor.getEmail());
        existingTutor.setPhoneNumber(tutor.getPhoneNumber());


        return tutorRepo.save(existingTutor);
    }

    @Override
    public void deleteTutorById(Long tutorId) {
        tutorRepo.deleteById(tutorId);
    }

	@Override
	public Tutor getTutorByEmail(String email) {
		 return tutorRepo.findByEmail(email)
	                .orElse(null);
	}

	@Override
	public LoginResponse getTutorProfile(String email) {
		Tutor tutor = getTutorByEmail(email);
		UserProfile up = UserProfile.builder()
				.id(tutor.getId())
				.firstName(tutor.getFirstName())
				.lastName(tutor.getLastName())
				.email(tutor.getEmail())
				.githubLink(tutor.getGithubLink())
				.linkedinLink(tutor.getGithubLink())
				.phoneNumber(tutor.getPhoneNumber())
				.photo(tutor.getPhoto())
				.type(tutor.getType()).build();
		return LoginResponse.builder().status(HttpStatus.OK)
				.userProfile(up).message("Success")
				.build();
	}

	


}
