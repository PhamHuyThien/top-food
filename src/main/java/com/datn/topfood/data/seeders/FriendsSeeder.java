package com.datn.topfood.data.seeders;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.FriendShip;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.FriendShipRepository;
import com.datn.topfood.util.BeanUtils;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.enums.FriendShipStatus;

import javax.xml.crypto.Data;

public class FriendsSeeder implements Seeder {

    @Override
    public void seed() {
        AccountRepository accountRepository = BeanUtils.getBean(AccountRepository.class);
        FriendShipRepository friendShipRepository = BeanUtils.getBean(FriendShipRepository.class);
        Account thien = accountRepository.findByUsername("thien");
        Account minh = accountRepository.findByUsername("minh");
        Account nga = accountRepository.findByUsername("nga");
        Account thang = accountRepository.findByUsername("thang");
        Account vuong = accountRepository.findByUsername("vuong");
        Account khai = accountRepository.findByUsername("khai");

        FriendShip friendShip = new FriendShip();
        friendShip.setId(1L);
        friendShip.setAccountAddressee(thien);
        friendShip.setAccountRequest(minh);
        friendShip.setStatus(FriendShipStatus.SENDING);
        friendShip.setCreateAt(DateUtils.currentTimestamp());
        friendShipRepository.save(friendShip);

        friendShip.setId(2L);
        friendShip.setAccountRequest(nga);
        friendShip.setStatus(FriendShipStatus.FRIEND);
        friendShipRepository.save(friendShip);

        friendShip.setId(3L);
        friendShip.setAccountRequest(thang);
        friendShip.setStatus(FriendShipStatus.BLOCK);
        friendShip.setBlockBy(thien);
        friendShipRepository.save(friendShip);

        friendShip.setId(4L);
        friendShip.setAccountAddressee(vuong);
        friendShip.setAccountRequest(thien);
        friendShip.setStatus(FriendShipStatus.SENDING);
        friendShip.setBlockBy(null);
        friendShipRepository.save(friendShip);

        friendShip.setId(5L);
        friendShip.setAccountAddressee(khai);
        friendShip.setAccountRequest(thien);
        friendShip.setStatus(FriendShipStatus.BLOCK);
        friendShip.setBlockBy(thien);
        friendShipRepository.save(friendShip);
    }
}
