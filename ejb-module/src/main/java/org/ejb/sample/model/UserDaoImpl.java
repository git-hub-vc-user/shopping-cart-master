package org.ejb.sample.model;

import org.ejb.sample.model.core.BaseDaoImp;
import org.springframework.stereotype.Component;

/**
 * This is the UserDao implementation.
 * <p>
 */
@Component()
public class UserDaoImpl extends BaseDaoImp<User> implements UserDao {

    /**
     *  This method hooks the correct entity type, simplifying parent BaseDaoImp
     *  type discovery.
     */
    @Override
    public Class<User> getType() {
        return User.class;
    }
}
