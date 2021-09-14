package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;

import com.eindwerk.SnelGeboekt.instellingen.medewerker.Medewerker;
import com.eindwerk.SnelGeboekt.organisatie.OrganisatieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TijdslotServiceImpl implements TijdslotService {

    private OrganisatieRepository organisatieRepository;
    private TijdslotRepository tijdslotRepository;

    @Autowired
    public void setOrganisatieRepository(OrganisatieRepository organisatieRepository){
        this.organisatieRepository = organisatieRepository;
    }

    @Autowired
    public void setTijdslotenRepository(TijdslotRepository tijdslotRepository) {
        this.tijdslotRepository = tijdslotRepository;
    }

    @Override
    public List<Tijdslot> getAll() {
        return tijdslotRepository.findAll();
    }

    @Override
    public void saveOrUpdate(Tijdslot tijdslot) {
        tijdslotRepository.save(tijdslot);
    }

    @Override
    public Tijdslot getById(int id) {
        return tijdslotRepository.getById(id);
    }

    @Override
    public List<Tijdslot> getTijdslotenByMedewerker(Medewerker medewerker) {
        return tijdslotRepository.getTijdslotenByMedewerker(medewerker);
    }

    @Override
    public List<Tijdslot> getTijdslotenByMedewerkers(List<Medewerker> medewerkers) {
        return tijdslotRepository.getTijdslotenByMedewerkers(medewerkers);
    }

    @Override
    public void delete(int id) {
        try {
            // delete tourInfo when available, this will also delete tour (cascade = All)
            tijdslotRepository.deleteById(id);
        } catch (Exception e) {
            // Delete tour only when no tourInfo exists
            tijdslotRepository.deleteById(id);
        }
    }

    @Override
    public void deleteTijdslotenByMedewerkerId(int medewerkerId) {
        try {
            // delete tourInfo when available, this will also delete tour (cascade = All)
            tijdslotRepository.deleteTijdslotenByMedewerkerId(medewerkerId);
        } catch (Exception e) {
            // Delete tour only when no tourInfo exists
            tijdslotRepository.deleteTijdslotenByMedewerkerId(medewerkerId);
        }
    }
}
