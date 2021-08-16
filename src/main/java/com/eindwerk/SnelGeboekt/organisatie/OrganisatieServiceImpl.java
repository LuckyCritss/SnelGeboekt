package com.eindwerk.SnelGeboekt.organisatie;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganisatieServiceImpl implements OrganisatieService{

    private OrganisatieRepository organisatieRepository;
    private OrganisatieService organisatieService;

    public void setOrganisatieRepository(OrganisatieRepository organisatieRepository) {
        this.organisatieRepository = organisatieRepository;
    }

    public void setOrganisatieService(OrganisatieService organisatieService) {
        this.organisatieService = organisatieService;
    }

    @Override
    public Organisatie getOrganisatieByEmail(String email) {
        return null;
    }

    @Override
    public Organisatie getOrganisatieBygebruikersNaam(String gebruikersNaam) {
        return null;
    }

    @Override
    public Organisatie findOrganisatie(String needle) {
        return null;
    }

    @Override
    public List<Organisatie> getAll() {
        return null;
    }

    @Override
    public void save(Organisatie organisatie) throws PasswordException, PasswordMisMatchException{

    }
}
