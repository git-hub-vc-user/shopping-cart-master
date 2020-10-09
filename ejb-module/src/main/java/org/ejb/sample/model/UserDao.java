package org.ejb.sample.model;

import org.ejb.sample.model.core.BaseDao;
import org.springframework.stereotype.Component;

/**
 * This remote session bean is a DAO class describing User entity access
 * methods.
 * <p>
 */
@Component()
public interface UserDao extends BaseDao<User> {
}
