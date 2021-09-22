package com.eindwerk.SnelGeboekt.reservatie;

import com.eindwerk.SnelGeboekt.organisatie.Organisatie;
import com.eindwerk.SnelGeboekt.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservatieServiceImpl implements ReservatieService {

  private ReservatieRepository reservatieRepository;

  @Autowired
  public void setReservatieRepository(ReservatieRepository reservatieRepository) {
      this.reservatieRepository = reservatieRepository;
  }

    @Override
    public List<ReservatieDTO> getReservatiesByEmail(String email) {
        return reservatieRepository.getReservatiesByEmail(email);
    }

    @Override
    public List<ReservatieDTO> getReservatiesByOrganisatie(Organisatie organisatie) {
        return reservatieRepository.getReservatiesByOrganisatie(organisatie);
    }

    @Override
    public ReservatieDTO getReservatieById(int id) {
        return reservatieRepository.getReservatieById(id);
    }

    @Override
    public void save(ReservatieDTO reservatieDTO) {
        reservatieRepository.save(reservatieDTO);
    }

    @Override
    public void delete(int id) {
        try {
            reservatieRepository.deleteById(id);
        } catch (Exception e) {
            reservatieRepository.deleteById(id);
        }
    }
}
