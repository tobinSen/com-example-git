package com.example.spring.mybatis.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.spring.mybatis.domain.City;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//@MappedTypes(City.class)
//@MappedJdbcTypes(value = {JdbcType.VARCHAR}, includeNullJdbcType = true)
public class CityTypeHandler extends BaseTypeHandler<List<City>> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<City> parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }

    @Override
    public List<City> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String city = rs.getString(columnName);
        return JSONArray.parseArray(city, City.class);
    }

    @Override
    public List<City> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String city = rs.getString(columnIndex);
        return JSONArray.parseArray(city, City.class);
    }

    @Override
    public List<City> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
