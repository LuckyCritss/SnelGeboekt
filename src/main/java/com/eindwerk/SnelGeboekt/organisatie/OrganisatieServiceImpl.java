package com.eindwerk.SnelGeboekt.organisatie;

import com.eindwerk.SnelGeboekt.user.UserService;

import java.util.List;

public class OrganisatieServiceImpl implements OrganisatieService{

    private OrganisatieRepository organisatieRepository;

    @Override
    public Organisatie getOrganisatieByHandle(String firstName) {
        return null;
    }

    @Override
    public Organisatie getOrganisatieComplete(String firstName) {
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
    public void save(Organisatie organisatie) throws UserService.PasswordException, UserService.PasswordMisMatchException {

    }
}
