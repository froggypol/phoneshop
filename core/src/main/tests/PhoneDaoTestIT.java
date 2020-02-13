import com.es.core.model.phone.Color;
import com.es.core.model.phone.JdbcPhoneDao;
import com.es.core.model.phone.Phone;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/context/test_App_Context.xml")
@ComponentScan("com.es.core")
public class PhoneDaoTestIT {

    @Resource
    private JdbcPhoneDao jdbcPhoneDao;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/context/test_App_Context.xml");
        dataSource = (DataSource)applicationContext.getBean("dataSource");
        jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
    }

    @Test
    public void notEmptyDataBaseWhenPhoneDaoTestFindAll() {
        Assert.assertTrue(jdbcPhoneDao.findAll(0).size() != 0);
    }

    @After
    public void tearDown() {
        JdbcTestUtils.dropTables(jdbcTemplate, "colors", "phones");
    }

    @Test
    public void whenTestPhoneDaoGet() {
        Phone expectedPhone;
        Phone actualParameterPhone = new Phone();

        actualParameterPhone.setId(1000L);
        actualParameterPhone.setModel("ARCHOS 101 G9");
        actualParameterPhone.setPrice(null);
        actualParameterPhone.setBrand("ARCHOS");
        actualParameterPhone.setImageUrl(null);
        actualParameterPhone.setColors(new HashSet<>());

        expectedPhone = new Phone();
        expectedPhone = jdbcPhoneDao.get(1000l).get();

        Assert.assertEquals(expectedPhone, actualParameterPhone);
    }

    @Test
    public void whenTestPhoneDaoSave() {
        Phone savingPhone = new Phone();

        savingPhone.setId(1005L);
        savingPhone.setModel("ZTE Z6");
        savingPhone.setPrice(BigDecimal.TEN);
        savingPhone.setBrand("ZTE");
        savingPhone.setImageUrl("lalala.jpg");
        savingPhone.getColors().add(new Color(1000L, "Black"));

        jdbcPhoneDao.save(savingPhone);

        Phone foundPhoneAfterSaving = jdbcPhoneDao.get(1005L).get();

        Assert.assertEquals(savingPhone, foundPhoneAfterSaving);

    }
}