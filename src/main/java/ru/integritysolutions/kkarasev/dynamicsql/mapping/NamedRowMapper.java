package ru.integritysolutions.kkarasev.dynamicsql.mapping;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;

@RequiredArgsConstructor
public class NamedRowMapper<E> implements RowMapper<E> {
  private final Iterable<? extends Field> fields;
  private final Supplier<E> factory;
  private final Class<E> entityClass;

  public NamedRowMapper(Iterable<? extends Field> fields, Class<E> entityClass) {
    this.fields = fields;
    this.entityClass = entityClass;
    this.factory = getSupplier(entityClass);
  }

  public NamedRowMapper(Class<E> entityClass) {
    this.fields = Arrays.stream(BeanUtils.getPropertyDescriptors(entityClass))
        .filter(pd -> pd.getWriteMethod() != null)
        .map(pd -> new FieldImpl(pd.getName(), pd.getName()))
        .collect(Collectors.toList());
    this.entityClass = entityClass;
    this.factory = getSupplier(entityClass);
  }

  private Supplier<E> getSupplier(Class<E> entityClass) {
    return () -> {
      try {
        Constructor<E> constructor = entityClass.getDeclaredConstructor();
        return constructor.newInstance();
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    };
  }

  @Override
  @SneakyThrows
  public E mapRow(ResultSet resultSet, int i) throws SQLException {
    E object = factory.get();
    for (Field field : fields) {
      Object value = resultSet.getObject(field.name());
      PropertyDescriptor pd = BeanUtils
          .getPropertyDescriptor(entityClass, field.getBeanField());
      pd.getWriteMethod().invoke(object, value);
    }
    return object;
  }

  @RequiredArgsConstructor
  class FieldImpl implements Field {
    private final String name;
    @Getter
    private final String beanField;

    public String name() {
      return name;
    }

  }
}
