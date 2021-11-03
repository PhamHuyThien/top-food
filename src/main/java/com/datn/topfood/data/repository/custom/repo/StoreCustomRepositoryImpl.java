package com.datn.topfood.data.repository.custom.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datn.topfood.data.model.Food;
import com.datn.topfood.data.repository.custom.impl.StoreCustomRepository;
import com.datn.topfood.dto.request.PageRequest;

@Repository
public class StoreCustomRepositoryImpl implements StoreCustomRepository {

	@Autowired
	EntityManager entityManager;

	@Override
	public List<Food> searchFoods(String foodName, String tagName, Double minPrice, Double maxPrice,
			PageRequest pageRequest) {
		String sql = "select f from Food as f join f.tag as t";
		if(foodName!=null||tagName!=null||(minPrice!=null&&maxPrice!=null)) {
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
		if(minPrice!=null && maxPrice!=null) {
			if(foodName!=null||tagName!=null) {
				sql+=" and ";
			}
			sql+="f.price >= :min and f.price <= :max";
		}
		TypedQuery<Food> foodQuery = entityManager.createQuery(sql, Food.class);
		if(foodName!=null) {
			foodQuery.setParameter("name", "%"+foodName+"%");
		}
		if(tagName!=null) {
			foodQuery.setParameter("tag", "%"+tagName+"%");
		}
		if(minPrice!=null && maxPrice!=null) {
			foodQuery.setParameter("min", minPrice);
			foodQuery.setParameter("max", maxPrice);
		}
		foodQuery.setFirstResult(pageRequest.getPage() * pageRequest.getPageSize());
		foodQuery.setMaxResults(pageRequest.getPageSize());
        List<Food> foods = foodQuery.getResultList();
		return foods;
	}
	@Override
	public Long countFoodsSearch(String foodName, String tagName, Double minPrice, Double maxPrice) {
		String sql = "select f from Food as f join f.tag as t ";
		if(foodName!=null||tagName!=null||(minPrice!=null&&maxPrice!=null)) {
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
		if(minPrice!=null && maxPrice!=null) {
			if(foodName!=null||tagName!=null) {
				sql+=" and ";
			}
			sql+=" f.price >= :min and f.price <= :max";
		}
		TypedQuery<Food> foodQuery = entityManager.createQuery(sql, Food.class);
		if(foodName!=null) {
			foodQuery.setParameter("name", "%"+foodName+"%");
		}
		if(tagName!=null) {
			foodQuery.setParameter("tag", "%"+tagName+"%");
		}
		if(minPrice!=null && maxPrice!=null) {
			foodQuery.setParameter("min", minPrice);
			foodQuery.setParameter("max", maxPrice);
		}
		return Long.valueOf(foodQuery.getResultList().size());
	}
}
