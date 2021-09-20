package com.eindwerk.SnelGeboekt.reservatie;

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
    public List<Reservatie> getReservatiesByUser(User user) {
        return reservatieRepository.getReservatiesByUser(user);
    }

    @Override
    public void save(Reservatie reservatie) {
        reservatieRepository.save(reservatie);
    }
}
