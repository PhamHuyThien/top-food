package com.datn.topfood.data.seeders;

import com.datn.topfood.data.model.Account;
import com.datn.topfood.data.model.Profile;
import com.datn.topfood.data.repository.jpa.AccountRepository;
import com.datn.topfood.data.repository.jpa.ProfileRepository;
import com.datn.topfood.util.BeanUtils;
import com.datn.topfood.util.DateUtils;
import com.datn.topfood.util.enums.AccountRole;
import com.datn.topfood.util.enums.AccountStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountSeeder implements Seeder {
    @Override
    public void seed() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordDefault = passwordEncoder.encode("123");
        AccountRepository accountRepository = BeanUtils.getBean(AccountRepository.class);
        ProfileRepository profileRepository = BeanUtils.getBean(ProfileRepository.class);
        Account[] accounts = new Account[]{
                new Account("thien", passwordDefault, "0912345678", "admin.topfood1@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
                new Account("khai", passwordDefault, "0912345677", "admin.topfood2@gmail.com", AccountStatus.WAIT_ACTIVE, AccountRole.ROLE_USER),
                new Account("vuong", passwordDefault, "0912345676", "admin.topfood3@gmail.com", AccountStatus.WAIT_ACTIVE, AccountRole.ROLE_USER),
                new Account("thang", passwordDefault, "0912345675", "admin.topfood4@gmail.com", AccountStatus.WAIT_ACTIVE, AccountRole.ROLE_USER),
                new Account("nga", passwordDefault, "0912345674", "admin.topfood5@gmail.com", AccountStatus.WAIT_ACTIVE, AccountRole.ROLE_USER),
                new Account("minh", passwordDefault, "0912345673", "admin.topfood6@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
                new Account("store", passwordDefault, "0912348888", "admin.topfood7@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_STORE),
                new Account("admin", passwordDefault, "0912349999", "admin.topfood8@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_ADMIN)
        };
        Profile[] profiles = new Profile[]{
                new Profile(1L, "https://i.bloganchoi.com/bloganchoi.com/wp-content/uploads/2020/09/nh-nen-nhung-dam-may-cute-696x385.jpg?fit=696%2C20000&quality=95&ssl=1", "https://i.9mobi.vn/cf/Images/tt/2021/8/20/anh-avatar-dep-39.jpg", "WHO AM I?", "VN", "Phạm Huy Thiên", DateUtils.currentTimestamp(), DateUtils.currentTimestamp(),null, null, null),
                new Profile(2L, "https://i.bloganchoi.com/bloganchoi.com/wp-content/uploads/2020/09/nh-nen-nhung-dam-may-cute-696x385.jpg?fit=696%2C20000&quality=95&ssl=1", "https://scr.vn/wp-content/uploads/2020/07/T%E1%BA%A3i-h%C3%ACnh-n%E1%BB%81n-%C4%91%E1%BA%B9p-nh%E1%BA%A5t-1.jpg", "WHO AM I?", "VN", "Đào Đình khải", DateUtils.currentTimestamp(), DateUtils.currentTimestamp(), null, null, null),
                new Profile(3L, "https://i.bloganchoi.com/bloganchoi.com/wp-content/uploads/2020/09/nh-nen-nhung-dam-may-cute-696x385.jpg?fit=696%2C20000&quality=95&ssl=1", "https://cdn.tgdd.vn//GameApp/1340221//Anhavatardoi51-800x800.jpg", "WHO AM I?", "VN", "Quốc Vương", DateUtils.currentTimestamp(), DateUtils.currentTimestamp(), null, null, null),
                new Profile(4L, "https://i.bloganchoi.com/bloganchoi.com/wp-content/uploads/2020/09/nh-nen-nhung-dam-may-cute-696x385.jpg?fit=696%2C20000&quality=95&ssl=1", "https://i.pinimg.com/564x/92/26/5c/92265c40c8e428122e0b32adc1994594.jpg", "WHO AM I?", "VN", "Nguyễn Thắng", DateUtils.currentTimestamp(), DateUtils.currentTimestamp(), null, null, null),
                new Profile(5L, "https://i.bloganchoi.com/bloganchoi.com/wp-content/uploads/2020/09/nh-nen-nhung-dam-may-cute-696x385.jpg?fit=696%2C20000&quality=95&ssl=1", "https://mondaycareer.com/wp-content/uploads/2020/11/%E1%BA%A3nh-avatar-%C4%91%E1%BA%B9p-c%C3%B4-g%C3%A1i-%C4%91eo-k%C3%ADnh.jpg", "WHO AM I?", "VN", "Nguyễn Bình Ngà", DateUtils.currentTimestamp(), DateUtils.currentTimestamp(), null, null, null),
                new Profile(6L, "https://i.bloganchoi.com/bloganchoi.com/wp-content/uploads/2020/09/nh-nen-nhung-dam-may-cute-696x385.jpg?fit=696%2C20000&quality=95&ssl=1", "https://upanh123.com/wp-content/uploads/2020/12/tai-anh-anime-ve-lam-avatar10.jpg", "WHO AM I?", "VN", "Trần Đại Minh", DateUtils.currentTimestamp(), DateUtils.currentTimestamp(),null, null, null),
                new Profile(7L, "https://cf.shopee.vn/file/0ad20ae511669aef2aad16191c1902f1", "https://cf.shopee.vn/file/57171334ad194140c5c1c088b456e3ca", "WHO AM I?", "VN", "TopFood Quán", DateUtils.currentTimestamp(), DateUtils.currentTimestamp(), null, null, null),
                new Profile(8L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://www.clipartmax.com/png/middle/319-3191274_male-avatar-admin-profile.png", "WHO AM I?", "VN", "Administrator", DateUtils.currentTimestamp(), DateUtils.currentTimestamp(), null, null, null),
        };
        for (int i = 0; i < accounts.length; i++) {
            accounts[i].setId(i + 1L);
            accounts[i] = accountRepository.save(accounts[i]);
            profiles[i].setAccount(accounts[i]);
            profileRepository.save(profiles[i]);
        }
    }
}
