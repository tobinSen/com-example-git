package faker;

import com.github.javafaker.Faker;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Locale;

public class FakerDemo {

    public static void main(String[] args) {
        Faker faker = new Faker(Locale.US);
        List<UserInfo> list  = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            UserInfo info  = new UserInfo();
            info.setRealName(faker.name().fullName());
            info.setCellPhone(faker.phoneNumber().cellPhone());
            info.setCity(faker.address().city());
            info.setUniversityName(faker.university().name());
            info.setStreet(faker.address().streetAddress());
            list.add(info);
        }

        list.forEach(System.out::println);
    }
}
