package dao.Impl;

import dao.UserDao;
import domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findUserByUsernameAndPassword(User loginUser) {
        //1.编写sql
        String sql = "select * from user where name = ? and password = ?";

        //调用query方法,查询结果，将结果封装为对象
        //可能查找不到时要这么写！
        User user=null;
        try {
            user = template.queryForObject(sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    loginUser.getName(), loginUser.getPassword());
        }catch (Exception e){
            user=null;
        }

        return user;

    }

    @Override
    public User findUserByEmail(User loginUser) {
        //1.编写sql
        String sql = "select * from user where email = ?";

        //调用query方法,查询结果，将结果封装为对象
        //可能查找不到时要这么写！
        User user=null;
        try {
            user = template.queryForObject(sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    loginUser.getEmail());
        }catch (Exception e){
            user=null;
        }

        return user;
    }

    @Override
    public boolean modifyPassword(User user, String password) {
        boolean flag =true;
        String sql="update user set password=? where id=?";

        try {
            template.update(sql,password,user.getId());
        }catch (Exception e){
            flag=false;
        }

        return flag;
    }

    @Override
    public User findUserByName(User loginUser) {
        //1.编写sql
        String sql = "select * from user where name = ?";

        //调用query方法,查询结果，将结果封装为对象
        //可能查找不到时要这么写！
        User user=null;
        try {
            user = template.queryForObject(sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    loginUser.getName());
        }catch (Exception e){
            user=null;
        }

        return user;
    }

    @Override
    public User findUserByPhone(User loginUser) {
        //1.编写sql
        String sql = "select * from user where phone = ?";

        //调用query方法,查询结果，将结果封装为对象
        //可能查找不到时要这么写！
        User user=null;
        try {
            user = template.queryForObject(sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    loginUser.getPhone());
        }catch (Exception e){
            user=null;
        }

        return user;
    }

    @Override
    public boolean registerAccount(User loginUser) {
        boolean flag =true;
        String sql="insert into user values(null,?,?,?,?,?,?)";

        try {
            template.update(sql,loginUser.getName(),loginUser.getPassword(),loginUser.getGender(),
                    loginUser.getAge(), loginUser.getPhone(),loginUser.getEmail());
        }catch (Exception e){
            flag=false;
        }

        return flag;
    }

    @Override
    public boolean deleteAccount(User loginUser) {
        boolean flag =true;
        String sql="delete from user where email=?";

        try {
            template.update(sql,loginUser.getEmail());
        }catch (Exception e){
            flag=false;
        }

        return flag;

    }

    @Override
    public boolean modifyInfo(User user, String email) {
        boolean flag =true;
        String sql="update user set name=?,gender=?,age=?,phone=? where email=?";

        try {
            template.update(sql,user.getName(),user.getGender(),
                    user.getAge(),user.getPhone(),email);
        }catch (Exception e){
            flag=false;
        }

        return flag;    }
}
