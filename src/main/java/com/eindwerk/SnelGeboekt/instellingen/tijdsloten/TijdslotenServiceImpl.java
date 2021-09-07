package com.eindwerk.SnelGeboekt.instellingen.tijdsloten;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TijdslotenServiceImpl implements TijdslotenService {

    private TijdslotenRepository tijdslotenRepository;

    @Autowired
    public void setTijdslotenRepository(TijdslotenRepository tijdslotenRepository) {
        this.tijdslotenRepository = tijdslotenRepository;
    }

    @Override
    public List<Tijdsloten> getAll() {
        return tijdslotenRepository.findAll();
    }

    @Override
    public void saveOrUpdate(Tijdsloten tijdsloten) {
        tijdslotenRepository.save(tijdsloten);
    }

    @Override
    public Tijdsloten getById(int id) {
        return tijdslotenRepository.getById(id);
    }

    @Override
    public List<Tijdsloten> getTijdslotenByOrganisatie(Organisatie organisatie) {

        return tijdslotenRepository.getTijdslotenByOrganisatie(organisatie);
    }

    @Override
    public void delete(int id) {

    }
}
