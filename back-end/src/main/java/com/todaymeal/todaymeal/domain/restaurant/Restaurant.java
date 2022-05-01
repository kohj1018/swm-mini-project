package com.todaymeal.todaymeal.domain.restaurant;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.todaymeal.todaymeal.domain.BaseEntity;
import com.todaymeal.todaymeal.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Restaurant extends BaseEntity {
	protected Restaurant() {}

	@Builder(builderMethodName = "restaurantBuilder")
	public Restaurant(String location, RestaurantCategory restaurantCategory, String restaurantName) {
		this.location = location;
		this.restaurantCategory = restaurantCategory;
		this.restaurantName = restaurantName;
	}

	@Builder(builderMethodName = "restaurantNameBuilder")
	public Restaurant(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	@Id
	@GeneratedValue
	@Column(name = "restaurant_id")
	private Long id;

	private String location;

	private String restaurantName;

	@Enumerated(value = EnumType.STRING)
	private RestaurantCategory restaurantCategory;

	@OneToMany(mappedBy = "restaurant")
	private List<Post> posts = new ArrayList<>();

	public void addPost(Post post) {
		post.setRestaurant(this);
	}
}
