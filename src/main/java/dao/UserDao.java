package dao;

import domain.User;

public interface UserDao {
    public User findUserByUsernameAndPassword(User loginUser);

    public User findUserByEmail(User loginUser);

    public boolean modifyPassword(User user, String password);

    public User findUserByName(User loginUser);

    public User findUserByPhone(User loginUser);

    public boolean registerAccount(User loginUser);

    public boolean deleteAccount(User loginUser);

    public boolean modifyInfo(User user, String email);
}
