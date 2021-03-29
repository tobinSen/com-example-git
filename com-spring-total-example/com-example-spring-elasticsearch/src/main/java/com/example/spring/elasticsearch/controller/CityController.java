package com.example.spring.elasticsearch.controller;

import com.example.spring.elasticsearch.dao.CityRepository;
import com.example.spring.elasticsearch.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 注释中出现以@开头东东被称之为Javadoc文档标记，是JDK定义好的，
 * 如@author、@version、@since、@see、@link、@code、@param、@return、@exception、@throws等。
 *
 * 1. @link：{@link 包名.类名#方法名(参数类型)} 用于快速链接到相关代码
 *    {@link java.lang.Character}
 *    {@link String}
 *    {@link #length()}
 *    {@link java.lang.String#charAt(int)}
 *
 * 2. @code： {@code text} 将文本标记为code 一般在Javadoc中只要涉及到类名或者方法名，都需要使用@code进行标记。
 *    {@code String}
 *
 * 3. @param 一般类中支持泛型时会通过@param来解释泛型的类型
 *    @param <E> the type of elements in this list
 *
 * 4. @author 详细描述后面一般使用@author来标记作者，
 *    @author Rod Johnson
 *    @author Igor Hersht, igorh@ca.ibm.com
 *    @author <a href="mailto:ovidiu@cup.hp.com">Ovidiu Predescu</a>
 *    @author shane_curcuru@us.ibm.com
 *    @author <a href="https://jakarta.apache.org/turbine"> Apache Jakarta Turbine</a>
 *
 * 5. @see 另请参阅  一般用于标记该类相关联的类,@see即可以用在类上，也可以用在方法上
 *    @see IntStream
 *    @see <a href="https://blog.csdn.net/vbirdbest/article/details/80296136">java.util.stream</a>
 *
 * 6. @since 从以下版本开始 一般用于标记文件创建时项目当时对应的版本
 *    @since 1.8
 *    @since 16 April 2001
 *
 * 7. @version 版本 用于标记当前版本，默认为1.0
 *    @version 1.0
 *
 * 8. @param 后面跟参数名，再跟参数描述
 *    @param str the {@code CharSequence} to check (may be {@code null})
 *
 * 9. @return 跟返回值的描述
 *    @return {@code true} if the {@code String} is not {@code null}, its
 *
 * 10. @throws 跟异常类型 异常描述 , 用于描述方法内部可能抛出的异常
 *    @throws IllegalArgumentException when the given source contains invalid encoded sequences
 *
 * 11. @exception 用于描述方法签名throws对应的异常
 *     @exception IllegalArgumentException if <code>key</code> is null.
 *
 */
@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     *
     * @param city 城市
     * @throws Exception 异常
     */
    @PostMapping(value = "api/city.do")
    public void save(@RequestBody City city) throws Exception {

        List<City> cityList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            City c = new City();
            c.setId(Long.valueOf(i));
            c.setName("tongjian" + i);
            c.setScore("29" + new Random().nextInt(1999));
            c.setDescription("kibana " + i + "opertation");
            c.setTimestamp(new Date());
            cityList.add(c);
        }
        cityRepository.saveAll(cityList);
    }

    @RequestMapping("jmeter.do")
    public String jmeter(String name) throws Exception {
        System.out.println("jmeter");
        return new Random().nextInt(19) + name;
    }

}
