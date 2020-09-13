package com.example.spring.mybatis.config;

/**
 * TODO 芋艿
 * <p>
 * 参考 https://www.cnblogs.com/waterystone/p/5547254.html
 * <p>
 * 后续，补充下注释和测试类，以及文章。
 *
 * @param <T>
 */
//public class JSONTypeHandler<T extends Object> extends BaseTypeHandler<T> {
//
//    private Class<T> clazz;
//
//    public JSONTypeHandler(Class<T> clazz) {
//        if (clazz == null) throw new IllegalArgumentException("Type argument cannot be null");
//        this.clazz = clazz;
//    }
//
//    //入参
//    @Override
//    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
//        ps.setString(i, this.toJson(parameter));
//    }
//
//    //回参
//    @Override
//    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
//        return this.toObject(rs.getString(columnName), clazz);
//    }
//
//    @Override
//    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
//        return this.toObject(rs.getString(columnIndex), clazz);
//    }
//
//    //返回存储过程
//    @Override
//    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
//        return this.toObject(cs.getString(columnIndex), clazz);
//    }
//
//    private String toJson(T object) {
//        try {
//            return JSON.toJSONString(object);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private T toObject(String content, Class<?> clazz) {
//        if (content != null && !content.isEmpty()) {
//            try {
//                return (T) JSON.parseObject(content, clazz);
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            return null;
//        }
//    }
//
//}
