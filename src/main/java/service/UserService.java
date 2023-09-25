package service;

import domain.User;

public interface UserService {

    public User login(User user);

    public User findUserByEmail(User user);

    public boolean modifyPassword(User user,String password);

    public User findUserByName(User user);

    public User findUserByPhone(User user);

    public boolean registerAccount(User user);

    public boolean deleteAccount(User user);

    public boolean modifyInfo(User user, String email);
}
