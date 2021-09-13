package com.eindwerk.SnelGeboekt.instellingen.medewerker;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedewerkerServiceImpl implements MedewerkerService {


        private OrganisatieRepository organisatieRepository;
        private MedewerkerRepository medewerkerRepository;

        @Autowired
        public void setOrganisatieRepository(OrganisatieRepository organisatieRepository){
            this.organisatieRepository = organisatieRepository;
        }

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
                // delete tourInfo when available, this will also delete tour (cascade = All)
                medewerkerRepository.deleteById(id);
            } catch (Exception e) {
                // Delete tour only when no tourInfo exists
                medewerkerRepository.deleteById(id);
            }
        }
    }
