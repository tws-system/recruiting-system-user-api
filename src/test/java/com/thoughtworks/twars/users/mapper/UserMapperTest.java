package com.thoughtworks.twars.users.mapper;

import com.thoughtworks.twars.users.bean.Group;
import com.thoughtworks.twars.users.bean.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserMapperTest extends TestBase {

    private UserMapper userMapper;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        userMapper = session.getMapper(UserMapper.class);
    }

    @Test
    public void should_return_user_by_id() throws Exception {
        User user = userMapper.getUserById(1);
        assertThat(user.getMobilePhone(), is("18745454545"));
    }


    @Test
    public void should_return_user_by_email() throws Exception {
        User user = userMapper.getUserByEmail("test@163.com");
        assertThat(user.getMobilePhone(), is("18745454545"));
    }

    @Test
    public void should_return_user_by_mobile_phone() throws Exception {
        User user = userMapper.getUserByMobilePhone("18745454545");
        assertThat(user.getEmail(), is("test@163.com"));
    }

    @Test
    public void should_return_user_by_email_and_password() throws Exception {
        User user = new User();
        user.setEmail("test@163.com");
        user.setPassword("25d55ad283aa400af464c76d713c07ad");

        User resultUser = userMapper.getUserByEmailAndPassWord(user);
        assertThat(resultUser.getMobilePhone(), is("18745454545"));
    }

    @Test
    public void should_return_user_by_mobile_phone_and_password() throws Exception {
        User user = new User();
        user.setMobilePhone("12345678901");
        user.setPassword("25d55ad283aa400af464c76d713c07ad");
    }

    @Test
    public void should_add_user() throws Exception {
        User user = new User();

        user.setEmail("test3@163.com");
        user.setMobilePhone("123456789012");
        user.setPassword("18928392811");

        userMapper.insertUser(user);

        assertThat(user.getId(), is(7));
    }


    @Test
    public void should_encrypt_password_when_create_new_user () throws Exception {
        User newUser = new User();
        newUser.setEmail("jingjing@qq.com");
        newUser.setMobilePhone("13576826262");
        newUser.setPassword("123");
        int result = userMapper.insertUser(newUser);
        int userId = newUser.getId();
        User addedUser = userMapper.getUserById(userId);

        assertThat(result, is(1));

        assertThat(addedUser.getPassword(), is("202cb962ac59075b964b07152d234b70"));
    }


    @Test
    public void should_update_password() throws Exception {

        int id = 1;
        String oldPassword = "25d55ad283aa400af464c76d713c07ad";
        String password = "123";

        int result = userMapper.updatePassword(id, oldPassword, password);

        User resultUser = userMapper.getUserById(1);

        assertThat(result, is(1));
        assertThat(resultUser.getPassword(), is("202cb962ac59075b964b07152d234b70"));
    }

    @Test
    public void should_reset_password() throws Exception {

        User user = new User();
        user.setPassword("1bbd886460827015e5d605ed44252251");
        user.setEmail("test@163.com");

        int result = userMapper.resetPassword(user);

        User resultUser = userMapper.getUserById(1);

        assertThat(result, is(1));
        assertThat(resultUser.getPassword(), is("d0521106f6ba7f9ac0a7370fb28d0ec6"));
    }


    @Test
    public void should_return_users() {
        List<Integer> userIds = new ArrayList<>();
        userIds.add(1);
        userIds.add(2);
        userIds.add(3);

        List<User> users = userMapper.findUsersByUserIds(userIds);

        assertThat(users.size(), is(3));
    }

    @Test
    public void should_return_user_groups() {
        List<Integer> groupIds = userMapper.findUserGroupsByUserId(1);
        assertThat(groupIds.size(), is(1));
    }

    @Test
    public void should_return_groups() {
        List<Integer> groupIds = new ArrayList<>();
        groupIds.add(1);
        groupIds.add(2);
        groupIds.add(3);

        List<Group> groups = userMapper.findGroupsByGroupId(groupIds);
        assertThat(groups.size(), is(3));
    }
}