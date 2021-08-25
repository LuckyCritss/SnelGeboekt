package com.eindwerk.SnelGeboekt.instellingen;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OptieServiceImpl implements OptieService {

    OrganisatieRepository organisatieRepository;
    OptieRepository optieRepository;

    @Autowired
    public void setOrganisatieRepository(OrganisatieRepository organisatieRepository){
        this.organisatieRepository = organisatieRepository;
    }

    @Autowired
    public void setOptieRepository(OptieRepository optieRepository){
        this.optieRepository = optieRepository;
    }


    @Override
    public List<Optie> getAll() {
        return null;
    }

    @Override
    public void saveOrUpdate(Optie optie) {
        optieRepository.save(optie);
    }


//    @Override
//    public void saveOrUpdate(Optie optie, String bedrijfsNaam) {
 //       Organisatie organisatie = organisatieRepository.findByBedrijfsnaam(bedrijfsnaam);
//        optie.setOrganisatie(organisatie);
//        optieRepository.save(optie);
//    }

    @Override
    public Optie getById(int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
