package com.eindwerk.SnelGeboekt.organisatie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganisatieServiceImpl implements OrganisatieService{

    private OrganisatieRepository organisatieRepository;

    @Autowired
    public void setOrganisatieRepository(OrganisatieRepository organisatieRepository) {
        this.organisatieRepository = organisatieRepository;
    }

    @Override
    public Organisatie getOrganisatieByEmail(String email) {
        return organisatieRepository.getOrganisatieByEmail(email);
    }

    @Override
    public Organisatie getOrganisatieById(Integer id) {
        return organisatieRepository.getOrganisatieById(id);
    }

    @Override
    public Organisatie getOrganisatieByName(String name) {
        return organisatieRepository.getOrganisatieByName(name);
    }

    @Override
    public List<Organisatie> getAll() {
        return organisatieRepository.findAll();
    }

    @Override
    public void save(Organisatie organisatie) throws PasswordException, PasswordMisMatchException {
        if (organisatie.getWachtWoord() != null && organisatie.getWachtWoord().length() < 5) {
            throw new PasswordException("password malformed");
        }
        if (organisatie.getWachtWoord() == null && organisatie.getId() == 0) {
            throw new PasswordException("password malformed");
        }
        if (organisatie.getWachtWoord().length() > 4 && !organisatie.getWachtWoord().equals(organisatie.getCheckWachtWoord()) ) {
            throw new PasswordMisMatchException("password mismatch");
        }

        if (organisatie.getWachtWoord() == null && organisatie.getId() > 0) {
            organisatie.setWachtWoord(organisatieRepository.findById(organisatie.getId()).get().getWachtWoord());
        } else {
            organisatie.setWachtWoord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(organisatie.getWachtWoord()));
        }
        organisatieRepository.save(organisatie);
    }

    public Organisatie getById(int id){
        return organisatieRepository.getById(id);
    }
}
