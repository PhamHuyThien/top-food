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
}
