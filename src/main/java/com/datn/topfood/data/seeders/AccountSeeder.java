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

import java.sql.Timestamp;

public class AccountSeeder implements Seeder {
    @Override
    public void seed() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String passwordDefault = passwordEncoder.encode("123");
        AccountRepository accountRepository = BeanUtils.getBean(AccountRepository.class);
        ProfileRepository profileRepository = BeanUtils.getBean(ProfileRepository.class);
        Timestamp currentTime = DateUtils.currentTimestamp();
        Account[] accounts = new Account[]{
                new Account("thien", passwordDefault, "0912345678", "admin.topfood1@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
                new Account("khai", passwordDefault, "0912345677", "admin.topfood2@gmail.com", AccountStatus.WAIT_ACTIVE, AccountRole.ROLE_USER),
                new Account("vuong", passwordDefault, "0912345676", "admin.topfood3@gmail.com", AccountStatus.WAIT_ACTIVE, AccountRole.ROLE_USER),
                new Account("thang", passwordDefault, "0912345675", "admin.topfood4@gmail.com", AccountStatus.WAIT_ACTIVE, AccountRole.ROLE_USER),
                new Account("nga", passwordDefault, "0912345674", "admin.topfood5@gmail.com", AccountStatus.WAIT_ACTIVE, AccountRole.ROLE_USER),
                new Account("minh", passwordDefault, "0912345673", "admin.topfood6@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
                new Account("store", passwordDefault, "0912348888", "admin.topfood7@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_STORE),
                new Account("admin", passwordDefault, "0912349999", "admin.topfood8@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_ADMIN),
                new Account("store1", passwordDefault, "0912348817", "admin.topfood71@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_STORE),
                new Account("store2", passwordDefault, "0912348816", "admin.topfood72@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_STORE),
                new Account("store3", passwordDefault, "0912348812", "admin.topfood73@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_STORE),
                new Account("store4", passwordDefault, "0912348813", "admin.topfood74@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_STORE),
                new Account("store5", passwordDefault, "0912348814", "admin.topfood75@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_STORE),
                new Account("store6", passwordDefault, "0912348815", "admin.topfood76@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_STORE),
        };
        Profile[] profiles = new Profile[]{
                new Profile(1L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://i.9mobi.vn/cf/Images/tt/2021/8/20/anh-avatar-dep-39.jpg", "WHO AM I?", "VN", "Phạm Huy Thiên", currentTime, currentTime,null, null, null),
                new Profile(2L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://upanh123.com/wp-content/uploads/2020/12/tai-anh-anime-ve-lam-avatar10.jpg", "WHO AM I?", "VN", "Đào Đình khải", currentTime, currentTime, null, null, null),
                new Profile(3L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://cdn.tgdd.vn//GameApp/1340221//Anhavatardoi51-800x800.jpg", "WHO AM I?", "VN", "Quốc Vương", currentTime, currentTime, null, null, null),
                new Profile(4L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://i.pinimg.com/564x/92/26/5c/92265c40c8e428122e0b32adc1994594.jpg", "WHO AM I?", "VN", "Nguyễn Thắng", currentTime, currentTime, null, null, null),
                new Profile(5L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://upanh123.com/wp-content/uploads/2020/12/tai-anh-anime-ve-lam-avatar10.jpg", "WHO AM I?", "VN", "Nguyễn Bình Ngà", currentTime, currentTime, null, null, null),
                new Profile(6L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://upanh123.com/wp-content/uploads/2020/12/tai-anh-anime-ve-lam-avatar10.jpg", "WHO AM I?", "VN", "Trần Đại Minh", currentTime, currentTime,null, null, null),
                new Profile(1,7L, "https://cf.shopee.vn/file/0ad20ae511669aef2aad16191c1902f1", "https://cf.shopee.vn/file/57171334ad194140c5c1c088b456e3ca", "WHO AM I?", "VN", "TopFood Quán", currentTime, currentTime, null, null, null),
                new Profile(8L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://www.clipartmax.com/png/middle/319-3191274_male-avatar-admin-profile.png", "WHO AM I?", "VN", "Administrator", currentTime, currentTime, null, null, null),
                new Profile(11,9L, "https://cf.shopee.vn/file/0ad20ae511669aef2aad16191c1902f1", "https://cf.shopee.vn/file/57171334ad194140c5c1c088b456e3ca", "WHO AM I?", "VN", "TopFood Quán 1", currentTime, currentTime, null, null, null),
                new Profile(10,10L, "https://cf.shopee.vn/file/0ad20ae511669aef2aad16191c1902f1", "https://cf.shopee.vn/file/57171334ad194140c5c1c088b456e3ca", "WHO AM I?", "VN", "TopFood Quán 2", currentTime, currentTime, null, null, null),
                new Profile(8,11L, "https://cf.shopee.vn/file/0ad20ae511669aef2aad16191c1902f1", "https://cf.shopee.vn/file/57171334ad194140c5c1c088b456e3ca", "WHO AM I?", "VN", "TopFood Quán 3", currentTime, currentTime, null, null, null),
                new Profile(6,12L, "https://cf.shopee.vn/file/0ad20ae511669aef2aad16191c1902f1", "https://cf.shopee.vn/file/57171334ad194140c5c1c088b456e3ca", "WHO AM I?", "VN", "TopFood Quán 4", currentTime, currentTime, null, null, null),
                new Profile(4,13L, "https://cf.shopee.vn/file/0ad20ae511669aef2aad16191c1902f1", "https://cf.shopee.vn/file/57171334ad194140c5c1c088b456e3ca", "WHO AM I?", "VN", "TopFood Quán 5", currentTime, currentTime, null, null, null),
                new Profile(2,14L, "https://cf.shopee.vn/file/0ad20ae511669aef2aad16191c1902f1", "https://cf.shopee.vn/file/57171334ad194140c5c1c088b456e3ca", "WHO AM I?", "VN", "TopFood Quán 6", currentTime, currentTime, null, null, null),
        };
        for (int i = 0; i < accounts.length; i++) {
            accounts[i].setId(i + 1L);
            accounts[i] = accountRepository.save(accounts[i]);
            profiles[i].setAccount(accounts[i]);
            profileRepository.save(profiles[i]);
        }
    }
}
