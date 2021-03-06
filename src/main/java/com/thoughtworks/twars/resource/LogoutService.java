package com.thoughtworks.twars.resource;

import com.thoughtworks.twars.bean.LoginDetail;
import com.thoughtworks.twars.mapper.LoginDetailMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/logout")
@Api
public class LogoutService {

    @Inject
    private LoginDetailMapper loginDetailMapper;

    @POST
    @ApiResponses(value = {@ApiResponse(code = 204, message = "logout successfully"),
            @ApiResponse(code = 401, message = "logout failed")})
    public Response logoutUser(Map data) {

        List<LoginDetail> loginDetailList =
                loginDetailMapper.getLoginDetailByUserId((Integer) data.get("userId"));
        LoginDetail loginDetail = loginDetailList.get(loginDetailList.size() - 1);

        if (loginDetail == null || loginDetail.getFlag() == 0) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        loginDetailMapper.updateLoginDetailById(loginDetail.getId());

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
