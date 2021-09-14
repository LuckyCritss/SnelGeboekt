package com.eindwerk.SnelGeboekt.instellingen.medewerker;

import com.eindwerk.SnelGeboekt.instellingen.optie.OptieService;
import com.eindwerk.SnelGeboekt.instellingen.tijdsloten.TijdslotService;
import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedewerkerServiceImpl implements MedewerkerService {

    private MedewerkerRepository medewerkerRepository;

    @Autowired
    public void setMedewerkerRepository(MedewerkerRepository medewerkerRepository){
        this.medewerkerRepository = medewerkerRepository;
    }


    @Override
    public List<Medewerker> getAll() {
        return medewerkerRepository.findAll();
    }

    @Override
    public void saveOrUpdate(Medewerker medewerker) {
        medewerkerRepository.save(medewerker);
    }

    @Override
    public Medewerker getById(int id) {
        return medewerkerRepository.getById(id);
    }


    @Override
    public List<Medewerker> getMedewerkersByOrganisation(Organisatie organisatie) {
        return medewerkerRepository.getMedewerkersByOrganisatie(organisatie);
    }

    @Override
    public Medewerker getMedewerkerById(int id) {
        return medewerkerRepository.getMedewerkerById(id);
    }

    @Override
        public void delete(int id) {
            try {
                medewerkerRepository.deleteById(id);
            } catch (Exception e) {
                medewerkerRepository.deleteById(id);
            }
        }
    }
