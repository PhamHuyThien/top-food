package com.datn.topfood.data.repository.custom.repo;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Favorite;
import com.datn.topfood.data.model.Food;
import com.datn.topfood.data.model.Post;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.repository.custom.impl.StoreCustomRepository;
import com.datn.topfood.dto.request.PageRequest;

@Repository
public class StoreCustomRepositoryImpl implements StoreCustomRepository {

	@Autowired
	EntityManager entityManager;

	@Override
	public List<Food> searchFoods(String foodName, String tagName,String storeName,
			PageRequest pageRequest) {
		String sql = "select f from Food as f join f.tag as t";
		if(foodName!=null||tagName!=null||storeName!=null) {
			sql+=" where ";
		}
		if(foodName!=null) {
			sql+=" f.name like :name";
		}
		if(tagName!=null) {
			if(foodName!=null) {
				sql+=" and ";
			}
			sql+=" t.tagName like :tag";
		}
		if(storeName!=null) {
			if(foodName!=null||tagName!=null) {
				sql+=" and ";
			}
			sql+=" f.profile.name like :store";
		}
		if(pageRequest.getOrder() != null) {
			sql+= " ORDER BY f.price "+pageRequest.getOrder().toUpperCase();
		}
		TypedQuery<Food> foodQuery = entityManager.createQuery(sql, Food.class);
		if(foodName!=null) {
			foodQuery.setParameter("name", "%"+foodName+"%");
		}
		if(tagName!=null) {
			foodQuery.setParameter("tag", "%"+tagName+"%");
		}
		if(storeName!=null) {
			foodQuery.setParameter("store", "%"+storeName+"%");
		}
		foodQuery.setFirstResult(pageRequest.getPage() * pageRequest.getPageSize());
		foodQuery.setMaxResults(pageRequest.getPageSize());
        List<Food> foods = foodQuery.getResultList();
		return foods;
	}
	@Override
	public Long countFoodsSearch(String foodName, String tagName,String storeName) {
		String sql = "select f from Food as f join f.tag as t";
		if(foodName!=null||tagName!=null||storeName!=null) {
			sql+=" where ";
		}
		if(foodName!=null) {
			sql+=" f.name like :name";
		}
		if(tagName!=null) {
			if(foodName!=null) {
				sql+=" and ";
			}
			sql+=" t.tagName like :tag";
		}
		if(storeName!=null) {
			if(foodName!=null||tagName!=null) {
				sql+=" and ";
			}
			sql+=" f.profile.name like :store";
		}
		TypedQuery<Food> foodQuery = entityManager.createQuery(sql, Food.class);
		if(foodName!=null) {
			foodQuery.setParameter("name", "%"+foodName+"%");
		}
		if(tagName!=null) {
			foodQuery.setParameter("tag", "%"+tagName+"%");
		}
		if(storeName!=null) {
			foodQuery.setParameter("store", "%"+storeName+"%");
		}
		return Long.valueOf(foodQuery.getResultList().size());
	}
	
	@Override
	public List<Post> newFeed(Integer address,List<Favorite> favorite,PageRequest pageRequest) {
		if(favorite!=null && !favorite.isEmpty()) {
			List<Long> tagIds = favorite.stream().map((f)->{
			return f.getTag().getId();
			}).collect(Collectors.toList());
			String city = address == null?
					"and p.disableAt is null":"and p.profile.city = :city and p.disableAt is null";
			String sql = "select p from Post as p join p.tags as t where t.id in :favorite "+ city +" group by p order by p.id desc";
			TypedQuery<Post> postQuery = entityManager.createQuery(sql, Post.class);
			postQuery.setParameter("favorite", tagIds);
			if(address != null) {
				postQuery.setParameter("city", address);
			}
			postQuery.setFirstResult(pageRequest.getPage() * pageRequest.getPageSize());
			postQuery.setMaxResults(pageRequest.getPageSize());
	        List<Post> posts = postQuery.getResultList();
			return posts;
		}
		String city = address == null?
				"":" where p.profile.city = :city ";
		String sql = "select p from Post as p"+ city +" order by p.id desc";
		TypedQuery<Post> postQuery = entityManager.createQuery(sql, Post.class);
		if(address != null) {
			postQuery.setParameter("city", address);
		}
		postQuery.setFirstResult(pageRequest.getPage() * pageRequest.getPageSize());
		postQuery.setMaxResults(pageRequest.getPageSize());
        List<Post> posts = postQuery.getResultList();
		return posts;
	}
	
	@Override
	public Long newFeedSize(Integer address,List<Favorite> favorite,PageRequest pageRequest) {
		if(favorite !=null && !favorite.isEmpty()) {
			List<Long> tagIds = favorite.stream().map((f)->{
			return f.getTag().getId();
			}).collect(Collectors.toList());
			String city = address == null?
			        "and p.disableAt is null":"and p.profile.city = :city and p.disableAt is null";
			String sql = "select p from Post as p join p.tags as t where t.id in :favorite "+ city +" group by p";
			TypedQuery<Post> postQuery = entityManager.createQuery(sql, Post.class);
			postQuery.setParameter("favorite", tagIds);
			if(address != null) {
				postQuery.setParameter("city", address);
			}
	        return Long.valueOf(postQuery.getResultList().size());
		}
		String city = address == null?
				"":" where p.profile.city = :city ";
		String sql = "select p from Post as p"+ city +" order by p.id desc";
		TypedQuery<Post> postQuery = entityManager.createQuery(sql, Post.class);
		if(address != null) {
			postQuery.setParameter("city", address);
		}
        return Long.valueOf(postQuery.getResultList().size());
	}
}
