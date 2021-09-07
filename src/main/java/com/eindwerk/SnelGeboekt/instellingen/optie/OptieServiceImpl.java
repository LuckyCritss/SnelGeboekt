package com.eindwerk.SnelGeboekt.instellingen.optie;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptieServiceImpl implements OptieService {

    private OrganisatieRepository organisatieRepository;
    private OptieRepository optieRepository;

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
        return optieRepository.findAll();
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
        return optieRepository.getById(id);
    }

    @Override
    public List<Optie> getOptiesByOrganisation(Organisatie organisatie) {
        return optieRepository.getOptieByOrganisatie(organisatie);
    }

    @Override
    public int GetDuurOptie(int DuurOptie){
        return DuurOptie;
    }


    @Override
    public void delete(int id) {
        try {
            // delete tourInfo when available, this will also delete tour (cascade = All)
            optieRepository.deleteById(id);
        } catch (Exception e) {
            // Delete tour only when no tourInfo exists
            optieRepository.deleteById(id);
        }
    }


}
