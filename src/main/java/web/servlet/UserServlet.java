package web.servlet;

import domain.User;
import service.UserService;
import service.impl.UserServiceImpl;
import util.MailUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

@WebServlet("/user/*") // /user/add /user/find
public class UserServlet extends BaseServlet {

    //账号密码登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        User loginUser = new User();
        loginUser.setName(username);
        loginUser.setPassword(password);

        UserService service = new UserServiceImpl();
        User user = service.login(loginUser);

        boolean flag = false;

        if (user == null) {
            flag = false;
        } else {
            flag = true;
        }

        if (flag) {
            session.setAttribute("username", user.getName());
            session.setAttribute("tip", "pass");
        } else {
            session.setAttribute("username", "");
            session.setAttribute("tip", "error");
        }

        findLogInfo(request, response);

    }

    //登出
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //重新设置session，清除缓存
        HttpSession session = request.getSession();
        session.setAttribute("username", "");
        session.setAttribute("tip", "error");

        findLogInfo(request, response);
    }

    //返回登录信息
    public void findLogInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //直接从session中获取登录用户
        String s[] = {"", ""};
        HttpSession session = request.getSession();
        s[0] = (String) session.getAttribute("username");
        s[1] = (String) session.getAttribute("tip");

        //将登陆状态写回客户端
        writeValue(s, response);
    }

//    校验图形验证码
    public void checkCaptcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //验证校验
        String captcha = request.getParameter("captcha");
        //从sesion中获取验证码
        HttpSession session = request.getSession();
        String captcha_server = (String) session.getAttribute("captcha_session");
        session.removeAttribute("captcha_session");//为了保证验证码只能使用一次

        if(captcha == null ||captcha_server == null || !captcha_server.equalsIgnoreCase(captcha)){
//            验证码错误
            session.setAttribute("username", "");
            session.setAttribute("tip", "error");
        }else {
            session.setAttribute("username", "");
            session.setAttribute("tip", "pass");
        }

        findLogInfo(request, response);
    }

    //返回图形验证码
    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int width = 86;
        int height = 30;
        //创建验证码对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        //美化图片
        //设置背景色
        Graphics g = image.getGraphics();       //画板对象
        g.setColor(new Color(230,230,230));             //设置画板颜色
        g.fillRect(0, 0, width, height);

        //画边框
//        g.setColor(Color.BLUE);
//        g.drawRect(0, 0, width - 1, height - 1);

        //生成随机角标
        String str = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789";

        StringBuilder sb = new StringBuilder();

        //设置字体
        g.setColor(new Color(57,64,77));
        g.setFont(new Font("黑体", Font.BOLD, 24));

        Random ran = new Random();
        for (int i = 1; i <= 4; i++) {
            int index = ran.nextInt(str.length());
            //获取字符
            char ch = str.charAt(index);
            sb.append(ch);

            g.drawString(ch + "", width / 5 * i, height * 2 / 3);
        }

        String captcha_session = sb.toString();
        request.getSession().setAttribute("captcha_session", captcha_session);

        //画干扰线
        g.setColor(new Color(93,79,94));
        //随机生成坐标点
        for (int i = 0; i < 10; i++) {
            int x1 = ran.nextInt(width);
            int x2 = ran.nextInt(width);
            int y1 = ran.nextInt(height);
            int y2 = ran.nextInt(height);
            g.drawLine(x1, x2, y1, y2);
        }

        //将图片输出到页面展示
        ImageIO.write(image, "jpg", response.getOutputStream());

    }

    //用邮箱查询用户信息
    public void findByEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //JS已经处理过格式，这里查找用户
        String email = request.getParameter("email");
        HttpSession session = request.getSession();


        User loginUser = new User();
        loginUser.setEmail(email);

        UserService service = new UserServiceImpl();
        User user = service.findUserByEmail(loginUser);

        boolean flag = false;

        if (user == null) {
            flag = false;
        } else {
            flag = true;
        }

        if (flag) {
            session.setAttribute("username", user.getName());
            session.setAttribute("tip", "pass");
        } else {
            session.setAttribute("username", "");
            session.setAttribute("tip", "error");
        }

        findLogInfo(request, response);

    }

    //发送邮件
    public void sendEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

//        生成动态验证码6位
        //生成随机角标
        String str = "0123456789";

        StringBuilder sb = new StringBuilder();

        Random ran = new Random();
        for (int i = 1; i <= 6; i++) {
            int index = ran.nextInt(str.length());
            //获取字符
            char ch = str.charAt(index);
            sb.append(ch);
        }

        String email_code = sb.toString();
        request.getSession().setAttribute("email_code", email_code);
        System.out.println(email_code);
        MailUtils.sendMail(email,"动态验证码为:"+email_code,"验证登录");
    }

    //返回User对象给前端
    public void findUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //直接从session中获取用户
        String []s =new String[8];
        HttpSession session = request.getSession();
        s[0] = (String) session.getAttribute("id");
        s[1] = (String) session.getAttribute("name");
        s[2] = (String) session.getAttribute("password");
        s[3] = (String) session.getAttribute("gender");
        s[4] = (String) session.getAttribute("age");
        s[5] = (String) session.getAttribute("phone");
        s[6] = (String) session.getAttribute("email");
        s[7] = (String) session.getAttribute("tip");

        //将用户信息写回客户端
        writeValue(s, response);
    }

    //校验邮箱验证码
    public void checkDynamicCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String dynamicCode = request.getParameter("dynamicCode");
        String username = request.getParameter("username");

        HttpSession session = request.getSession();
        boolean flag = false;

        String email_code = (String) session.getAttribute("email_code");

        if (dynamicCode ==null || email_code == null || !email_code.equalsIgnoreCase(dynamicCode)){
            flag =false;
        }else {
            flag=true;
        }

        if (flag) {
            session.setAttribute("username", username);
            session.setAttribute("tip", "pass");
        } else {
            session.setAttribute("username", "");
            session.setAttribute("tip", "error");
        }
        findLogInfo(request, response);

    }

    //修改密码
    public void modifyPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        HttpSession session = request.getSession();

        User loginUser = new User();
        loginUser.setEmail(email);

        UserService service = new UserServiceImpl();
        User user = service.findUserByEmail(loginUser);

        boolean flag = service.modifyPassword(user,password);

        if (flag) {
            session.setAttribute("username", user.getName());
            session.setAttribute("tip", "pass");
        } else {
            session.setAttribute("username", "");
            session.setAttribute("tip", "error");
        }

        findLogInfo(request, response);
    }

    //用用户名查找用户
    public void findUserByUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");

        HttpSession session = request.getSession();

        User loginUser = new User();
        loginUser.setName(username);

        UserService service = new UserServiceImpl();
        User user = service.findUserByName(loginUser);

        boolean flag = false;

        if (user == null) {
            flag = false;
        } else {
            flag = true;
        }

        if (flag) {
            session.setAttribute("username", user.getName());
            session.setAttribute("tip", "pass");
        } else {
            session.setAttribute("username", "");
            session.setAttribute("tip", "error");
        }

        findLogInfo(request, response);
    }

    //用手机号查找用户
    public void findUserByPhone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String phone = request.getParameter("phone");

        HttpSession session = request.getSession();

        User loginUser = new User();
        loginUser.setPhone(phone);

        UserService service = new UserServiceImpl();
        User user = service.findUserByPhone(loginUser);

        boolean flag = false;

        if (user == null) {
            flag = false;
        } else {
            flag = true;
        }

        if (flag) {
            session.setAttribute("username", user.getName());
            session.setAttribute("tip", "pass");
        } else {
            session.setAttribute("username", "");
            session.setAttribute("tip", "error");
        }

        findLogInfo(request, response);
    }

    //保存注册表单信息
    public void saveRegisterForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String gender = request.getParameter("gender");
        String age = request.getParameter("age");
        String phone = request.getParameter("phone");

        HttpSession session = request.getSession();

        String []s = new String[5];
        s[0]=username;s[1]=password;s[2]=gender;s[3]=age;s[4]=phone;

        session.setAttribute("register_form",s);

    }

    //注册用户
    public void registerAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        String []s = (String[]) session.getAttribute("register_form");

        User loginUser = new User(s[0],s[1],s[2],Integer.parseInt(s[3]),s[4],email);

        UserService service = new UserServiceImpl();

        boolean flag = service.registerAccount(loginUser);

        if (flag) {
            session.setAttribute("username", s[0]);
            session.setAttribute("tip", "pass");
        } else {
            session.setAttribute("username", "");
            session.setAttribute("tip", "error");
        }

        findLogInfo(request, response);

    }

    //删除用户
    public void deleteAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        HttpSession session = request.getSession();

        User loginUser = new User();
        loginUser.setEmail(email);

        UserService service = new UserServiceImpl();

        User loginUser2 = service.findUserByEmail(loginUser);

        boolean flag = service.deleteAccount(loginUser);

        if (flag) {
            session.setAttribute("username", loginUser2.getName());
            session.setAttribute("tip", "pass");
        } else {
            session.setAttribute("username", "");
            session.setAttribute("tip", "error");
        }

        findLogInfo(request, response);

    }

    //用用户名查找用户并返回用户，修改信息用，显示在前端
    public void findUserByUsername2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");

        HttpSession session = request.getSession();

        User loginUser = new User();
        loginUser.setName(username);

        UserService service = new UserServiceImpl();
        User user = service.findUserByName(loginUser);

        String []s = new String[5];
        s[0]=username;s[1]=user.getGender();s[2]= String.valueOf(user.getAge());s[3]=user.getPhone();s[4]=user.getEmail();

//        为没有修改前的信息设置session
        session.setAttribute("username_NoModify",username);
        session.setAttribute("phone_NoModify",user.getPhone());
        session.setAttribute("email_NoModify",user.getEmail());

        writeValue(s,response);
    }

    //用用户名查找用户是否重名,修改信息用
    public void findUserByUsername3(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        HttpSession session = request.getSession();
        UserService service = new UserServiceImpl();
        boolean flag = false;   //不重名

        String username_NoModify = (String) session.getAttribute("username_NoModify");

        if (!(username_NoModify.equals(username))){
            //修改了，找是否重名
            User loginUser = new User();
            loginUser.setName(username);
            User user = service.findUserByName(loginUser);

            if (user == null) {
                //不重名
                flag = false;
            } else {
                //重名
                flag = true;
            }
        }

        if (flag) {
            session.setAttribute("username", "");
            session.setAttribute("tip", "error");
        } else {
            session.setAttribute("username", "");
            session.setAttribute("tip", "pass");
        }

        findLogInfo(request, response);

    }

    //用手机号查找手机是否重复,修改信息用
    public void findUserByPhone2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String phone = request.getParameter("phone");
        HttpSession session = request.getSession();
        UserService service = new UserServiceImpl();
        boolean flag = false;   //不重复

        String phone_NoModify = (String) session.getAttribute("phone_NoModify");

        if (!(phone_NoModify.equals(phone))){
            //修改了，找是否重复
            User loginUser = new User();
            loginUser.setPhone(phone);
            User user = service.findUserByPhone(loginUser);

            if (user == null) {
                //不重复
                flag = false;
            } else {
                //重复
                flag = true;
            }
        }

        if (flag) {
            session.setAttribute("username", "");
            session.setAttribute("tip", "error");
        } else {
            session.setAttribute("username", "");
            session.setAttribute("tip", "pass");
        }

        findLogInfo(request, response);

    }

    //修改用户信息
    public void modifyInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String gender = request.getParameter("gender");
        String age = request.getParameter("age");
        String phone = request.getParameter("phone");

        HttpSession session = request.getSession();
        UserService service = new UserServiceImpl();

        //我们认为邮箱不可更改，前后一致
        String email = (String) session.getAttribute("email_NoModify");
        //准备修改的参数
        User user2 = new User();
        user2.setName(username);
        user2.setGender(gender);
        user2.setAge(Integer.parseInt(age));
        user2.setPhone(phone);

        System.out.println(user2);

        boolean flag = service.modifyInfo(user2,email);

        if (flag) {
            session.setAttribute("username", "");
            session.setAttribute("tip", "pass");
        } else {
            session.setAttribute("username", "");
            session.setAttribute("tip", "error");
        }

        findLogInfo(request, response);
    }


}
