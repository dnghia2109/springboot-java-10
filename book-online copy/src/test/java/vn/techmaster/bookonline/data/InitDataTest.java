package vn.techmaster.bookonline.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.techmaster.bookonline.MockDataInitializer;

@SpringBootTest
public class InitDataTest {
    @Autowired
    private MockDataInitializer mockDataInitializer;

    @Test
    void initData() {
        mockDataInitializer.initData();
    }
}
