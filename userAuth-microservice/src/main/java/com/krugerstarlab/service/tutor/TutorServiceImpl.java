package com.krugerstarlab.service.tutor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.krugerstarlab.entity.Member;
import com.krugerstarlab.entity.Tutor;
import com.krugerstarlab.repository.TutorRepository;

@Service
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepo;
    
    

    public TutorServiceImpl(TutorRepository tutorRepo) {
        this.tutorRepo = tutorRepo;
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

	


}
