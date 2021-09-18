package com.eindwerk.SnelGeboekt.instellingen.optie;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptieServiceImpl implements OptieService {

    private OptieRepository optieRepository;


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

    @Override
    public Optie getById(int id) {
        return optieRepository.getById(id);
    }


    @Override
    public List<Optie> getOptiesByMedewerker(Medewerker medewerker) {
        return optieRepository.getOptiesByMedewerker(medewerker);
    }

    @Override
    public String getOptieTitelsByMedewerkersId(int medewerkersId) {
        return optieRepository.getOptieTitelsByMedewerkersId(medewerkersId);
    }

    @Override
    public Optie getOptieByMedewerkerAndString(Medewerker medewerker, String titel) {
        return optieRepository.getOptieByMedewerkerAndString(medewerker, titel);
    }

    @Override
    public int getDuurOptie(String titel, String medewerkerNaam) {
        return optieRepository.getDuurOptie(titel, medewerkerNaam);
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

    @Override
    public void deleteOptiesByMedewerkerId(int medewerkerId) {
        try {
            // delete tourInfo when available, this will also delete tour (cascade = All)
            optieRepository.deleteOptiesByMedewerkerId(medewerkerId);
        } catch (Exception e) {
            // Delete tour only when no tourInfo exists
            optieRepository.deleteOptiesByMedewerkerId(medewerkerId);
        }
    }


}
