package com.todaymeal.todaymeal.service.restaurant;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaymeal.todaymeal.domain.restaurant.Restaurant;
import com.todaymeal.todaymeal.domain.restaurant.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantService {

	private final RestaurantRepository restaurantRepository;
	private final EntityManager em;

	public Restaurant saveIfNotExist(String restaurantName) {
		Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurantName)
				.orElse(new Restaurant(restaurantName));
		em.persist(restaurant);
		return restaurant;
	}
}
