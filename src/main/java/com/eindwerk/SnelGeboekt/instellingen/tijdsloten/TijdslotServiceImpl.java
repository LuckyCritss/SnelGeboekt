package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TijdslotServiceImpl implements TijdslotService {

    private TijdslotRepository tijdslotRepository;

    @Autowired
    public void setTijdslotenRepository(TijdslotRepository tijdslotRepository) {
        this.tijdslotRepository = tijdslotRepository;
    }

    @Override
    public List<Tijdslot> getAll() {
        return tijdslotRepository.findAll();
    }

    @Override
    public void saveOrUpdate(Tijdslot tijdsloten) {
        tijdslotRepository.save(tijdsloten);
    }

    @Override
    public Tijdslot getById(int id) {
        return tijdslotRepository.getById(id);
    }

    @Override
    public List<Tijdslot> getTijdslotenByOrganisatie(Organisatie organisatie) {

        return tijdslotRepository.getTijdslotenByOrganisatie(organisatie);
    }

    @Override
    public void delete(int id) {

    }
}
