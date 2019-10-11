package com.example.spring.ttl.beancopier;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BeanCopierTest {


    //这里是不适用转换器，数据类型和字段名必须完全一致
    public static BeanCopier copier = BeanCopier.create(Account.class, AccountDto.class, false);
    //这里使用转换器，需保证字段名一致，数据类型可不一致
    public static BeanCopier typeCopier = BeanCopier.create(Account.class, AccountDto.class, true);

    public static void main(String[] args) {
        Account po = new Account(1, new Date(), BigDecimal.valueOf(40000L));
        AccountDto dto = new AccountDto();
        typeCopier.copy(po, dto, new AccountConverter());
        System.out.println(dto);


    }

    static class AccountConverter implements Converter {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //每一个属性都会进入此方法中
        @SuppressWarnings("rawtypes")
        @Override
        public Object convert(Object source, Class target, Object context) {
            if (source instanceof Integer) {
                return (Integer) source;
            } else if (source instanceof Date) {
                Date date = (Date) source;
                return sdf.format(date);
            } else if (source instanceof BigDecimal) {
                BigDecimal bd = (BigDecimal) source;
                return bd.toPlainString();
            }
            return null;
        }
    }

    public static class Account {
        private int id;
        private Date createTime;
        private BigDecimal balance;

        public Account(int id, Date createTime, BigDecimal balance) {
            this.id = id;
            this.createTime = createTime;
            this.balance = balance;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }
    }

    public static class AccountDto {
        private int id;
        private String createTime;
        private String balance;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        @Override
        public String toString() {
            return "AccountDto{" +
                    "id=" + id +
                    ", createTime='" + createTime + '\'' +
                    ", balance='" + balance + '\'' +
                    '}';
        }
    }


}
