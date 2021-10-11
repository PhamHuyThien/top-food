package com.datn.topfood.data.seeders;

public class BatchSeeder {
    public static Seeder[] seeders = new Seeder[]{
            new AccountSeeder(),
            new FriendsSeeder(),
            new MessagesSeeder()
    };

    public static void seed() {
        for (Seeder seeder : seeders) {
            seeder.seed();
        }
    }
}
