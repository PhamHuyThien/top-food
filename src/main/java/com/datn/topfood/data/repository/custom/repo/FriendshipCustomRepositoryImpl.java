package com.datn.topfood.data.repository.custom.repo;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.FriendShip;
import com.datn.topfood.data.repository.custom.impl.FriendshipCustomRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.dto.request.PageRequest;
import com.datn.topfood.dto.response.FriendProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FriendshipCustomRepositoryImpl implements FriendshipCustomRepository {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    ProfileRepository profileRepository;

    @Override
    public List<FriendProfileResponse> findByListFriends(long profileId, PageRequest pageRequest) {
        String sql = "SELECT fs FROM FriendShip fs " +
                "WHERE (fs.accountRequest.id =?1 OR  fs.accountAddressee.id = ?2) AND fs.status = 'FRIEND' ";
        TypedQuery<FriendShip> friendShipTypedQuery = entityManager.createQuery(sql, FriendShip.class);
        friendShipTypedQuery.setParameter(1, profileId);
        friendShipTypedQuery.setParameter(2, profileId);
        friendShipTypedQuery.setFirstResult((pageRequest.getPage() - 1) * pageRequest.getPageSize());
        friendShipTypedQuery.setMaxResults(pageRequest.getPageSize());
        List<FriendShip> friendShipList = friendShipTypedQuery.getResultList();
        List<FriendProfileResponse> friendResponseList = new ArrayList<>();
        friendShipList.stream().forEach((friendShip) -> {
            Account account = null;
            if (friendShip.getAccountAddressee().getId() == profileId) {
                account = friendShip.getAccountRequest();
            } else {
                account = friendShip.getAccountAddressee();
            }
            FriendProfileResponse friendProfileResponse = profileRepository.findFiendProfileByAccountId(account.getId());
            friendResponseList.add(friendProfileResponse);
        });
        return friendResponseList;
    }

    @Override
    public Long getTotalProfile(long profileId) {
        String sql = "select count (fs) " +
                "from FriendShip fs " +
                "where (fs.accountAddressee.id = ?1 or fs.accountRequest.id = ?2) and fs.status = 'FRIEND' ";
        Query query = entityManager.createQuery(sql);
        query.setParameter(1, profileId);
        query.setParameter(2, profileId);
        return Long.parseLong(query.getSingleResult().toString());
    }
}
