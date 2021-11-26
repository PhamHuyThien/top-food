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

    private static Account[] accounts = new Account[]{
            new Account("long", null, "0584671973", "hoang.ahihi@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("minh1", null, "0713760174", "sinh.fuckgirl@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("khang", null, "0720205498", "hai.xinh_gai@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("vinh", null, "0835031528", "hoang.kute@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("nam", null, "0560141842", "cuong.ahihi@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("hai", null, "0784928932", "mai.bad_boy@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("cuong", null, "0991268667", "mai.dep_trai@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("hoang", null, "0364869498", "sinh.xinh_gai@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("mai", null, "0757587340", "giang.ahihi.89040@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("trang", null, "0867764386", "giang.fuckgirl.36332@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("linh", null, "0867764386", "giang.hot_girl.34087@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("giang", null, "0704782314", "manh.ahihi.55433@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("huyen", null, "0704782314", "thien.xinh_gai.20280@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("mon", null, "0753388304", "thien.xau_gai.90425@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("sinh", null, "0741903933", "giang.dep_trai.97836@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("anh", null, "0784688058", "thien.cute.58505@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("tuan", null, "0373880548", "long.fuckgirl.89984@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("huy", null, "0799666386", "tuan.ahihi.35516@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("loc", null, "0381180106", "mai.xau_gai.62609@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("ta", null, "0743311396", "long.fuckgirl.20950@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("gam", null, "0929997476", "tuan.meow_meow.13805@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("vu", null, "0331353830", "hoang.xau_gai.92123@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("cong", null, "0849053719", "long.xau_gai.91019@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("thu", null, "0742838978", "manh.dep_trai.34322@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("duoc", null, "0375637460", "tuan.dep_trai.94525@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("quyen", null, "0741519208", "tuan.dep_trai.34078@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("lam", null, "0708964142", "long.kute.98973@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("hien", null, "0308886906", "sinh.ahihi.13193@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("nguyen", null, "0911337175", "hoang.fuckgirl.83437@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("trung", null, "0361236316", "manh.xau_gai.89968@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
            new Account("nhu", null, "0593314906", "tuan.ahihi.95562@gmail.com", AccountStatus.ACTIVE, AccountRole.ROLE_USER),
    };

    private static Profile[] profiles = new Profile[]{

    };

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
        };
        Profile[] profiles = new Profile[]{
                new Profile(1, 1L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://i.9mobi.vn/cf/Images/tt/2021/8/20/anh-avatar-dep-39.jpg", "WHO AM I?", "VN", "Phạm Huy Thiên", currentTime, currentTime, null, null, null),
                new Profile(1, 2L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://upanh123.com/wp-content/uploads/2020/12/tai-anh-anime-ve-lam-avatar10.jpg", "WHO AM I?", "VN", "Đào Đình khải", currentTime, currentTime, null, null, null),
                new Profile(1, 3L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://cdn.tgdd.vn//GameApp/1340221//Anhavatardoi51-800x800.jpg", "WHO AM I?", "VN", "Quốc Vương", currentTime, currentTime, null, null, null),
                new Profile(1, 4L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://i.pinimg.com/564x/92/26/5c/92265c40c8e428122e0b32adc1994594.jpg", "WHO AM I?", "VN", "Nguyễn Thắng", currentTime, currentTime, null, null, null),
                new Profile(1, 5L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://upanh123.com/wp-content/uploads/2020/12/tai-anh-anime-ve-lam-avatar10.jpg", "WHO AM I?", "VN", "Nguyễn Bình Ngà", currentTime, currentTime, null, null, null),
                new Profile(1, 6L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://upanh123.com/wp-content/uploads/2020/12/tai-anh-anime-ve-lam-avatar10.jpg", "WHO AM I?", "VN", "Trần Đại Minh", currentTime, currentTime, null, null, null),
                new Profile(1, 7L, "https://cf.shopee.vn/file/0ad20ae511669aef2aad16191c1902f1", "https://cf.shopee.vn/file/57171334ad194140c5c1c088b456e3ca", "WHO AM I?", "VN", "TopFood Quán", currentTime, currentTime, null, null, null),
                new Profile(1, 8L, "https://s3.cloud.cmctelecom.vn/tinhte2/2019/05/4645607_cover_home_windows_terminal_app_moi.jpg", "https://www.clipartmax.com/png/middle/319-3191274_male-avatar-admin-profile.png", "WHO AM I?", "VN", "Administrator", currentTime, currentTime, null, null, null),
        };
        for (int i = 0; i < accounts.length; i++) {
            accounts[i].setId(i + 1L);
            accounts[i] = accountRepository.save(accounts[i]);
            profiles[i].setAccount(accounts[i]);
            profileRepository.save(profiles[i]);
        }
    }
}
