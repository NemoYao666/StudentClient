package service.impl;

import dao.Impl.UserDaoImpl;
import dao.UserDao;
import domain.User;
import service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user);
    }

    @Override
    public User findUserByEmail(User user) {
        return dao.findUserByEmail(user);
    }

    @Override
    public boolean modifyPassword(User user, String password) {
        return dao.modifyPassword(user,password);
    }

    @Override
    public User findUserByName(User user) {
        return dao.findUserByName(user);
    }

    @Override
    public User findUserByPhone(User user) {
        return dao.findUserByPhone(user);
    }

    @Override
    public boolean registerAccount(User user) {
        return dao.registerAccount(user);
    }

    @Override
    public boolean deleteAccount(User user) {
        return dao.deleteAccount(user);
    }

    @Override
    public boolean modifyInfo(User user, String email) {
        return dao.modifyInfo(user,email);
    }
}
